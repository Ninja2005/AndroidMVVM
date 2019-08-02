package com.hqumath.androidmvvm.datasource;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;
import com.hqumath.androidmvvm.app.AppExecutors;
import com.hqumath.androidmvvm.entity.CommitEntity;
import com.hqumath.androidmvvm.entity.NetworkState;
import com.hqumath.androidmvvm.http.BaseApi;
import com.hqumath.androidmvvm.http.HandlerException;
import com.hqumath.androidmvvm.http.HttpOnNextListener;
import com.hqumath.androidmvvm.http.RetrofitClient;
import com.hqumath.androidmvvm.http.service.MyApiService;
import com.trello.rxlifecycle2.LifecycleProvider;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * ****************************************************************
 * 文件名称: CommitSource
 * 作    者: Created by gyd
 * 创建时间: 2019/7/31 16:05
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class CommitSource extends PageKeyedDataSource<Long, CommitEntity> {

    private WeakReference<LifecycleProvider> lifecycle;
    public MutableLiveData<NetworkState> networkState = new MutableLiveData<>();//网络状态
    public MutableLiveData<NetworkState> initialLoad = new MutableLiveData<>();//初始化加载状态
    private Runnable retry = null;

    public CommitSource(WeakReference<LifecycleProvider> lifecycle) {
        this.lifecycle = lifecycle;
    }

    public void retryAllFailed() {
        if (retry != null) {
            Runnable prevRetry = retry;
            retry = null;
            AppExecutors.getInstance().networkIO().execute(prevRetry);
        }
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long,
            CommitEntity> callback) {
        networkState.postValue(NetworkState.LOADING);
        initialLoad.postValue(NetworkState.LOADING);

        // triggered by a refresh, we better execute sync
        // 初始化请求不能异步
        try {
            Response<List<CommitEntity>> response = RetrofitClient.getInstance().getRetrofit().create(MyApiService.class)
                    .getCommits2("ThirtyDegreesRay", "OpenHub", params.requestedLoadSize, 1).execute();
            if (response.isSuccessful() && response.body() != null) {
                retry = null;
                networkState.postValue(NetworkState.LOADED);
                initialLoad.postValue(NetworkState.LOADED);
                callback.onResult(response.body(), null, 2L);
            } else {
                retry = () -> loadInitial(params, callback);
                NetworkState error = new NetworkState(NetworkState.Status.FAILED, "error code" + response.code());
                networkState.postValue(error);
                initialLoad.postValue(error);
            }
        } catch (IOException e) {
            retry = () -> loadInitial(params, callback);
            NetworkState error = new NetworkState(NetworkState.Status.FAILED, e.getMessage());
            networkState.postValue(error);
            initialLoad.postValue(error);
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, CommitEntity> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, CommitEntity> callback) {
        RetrofitClient.getInstance().sendHttpRequestIO(new BaseApi(new HttpOnNextListener() {
            @Override
            public void onSubscribe() {
                networkState.postValue(NetworkState.LOADING);
            }

            @Override
            public void onNext(Object o) {
                List<CommitEntity> list = (List<CommitEntity>) o;
                callback.onResult((List<CommitEntity>) o, (list != null && list.size() > 0) ? params.key + 1 : null);
            }

            @Override
            public void onError(HandlerException.ResponseThrowable e) {
                retry = () -> loadAfter(params, callback);
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, e.getMessage()));

            }

            @Override
            public void onComplete() {
                retry = null;
                networkState.postValue(NetworkState.LOADED);
            }
        }, lifecycle) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                return retrofit.create(MyApiService.class).getCommits1("ThirtyDegreesRay", "OpenHub",
                        params.requestedLoadSize, params.key);
            }
        });
    }
}
