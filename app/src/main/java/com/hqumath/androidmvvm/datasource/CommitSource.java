package com.hqumath.androidmvvm.datasource;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;
import com.hqumath.androidmvvm.entity.CommitEntity;
import com.hqumath.androidmvvm.entity.NetworkState;
import com.hqumath.androidmvvm.http.BaseApi;
import com.hqumath.androidmvvm.http.HandlerException;
import com.hqumath.androidmvvm.http.HttpOnNextListener;
import com.hqumath.androidmvvm.http.RetrofitClient;
import com.hqumath.androidmvvm.http.service.MyApiService;
import com.trello.rxlifecycle2.LifecycleProvider;
import io.reactivex.Observable;
import retrofit2.Retrofit;

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


    public CommitSource(WeakReference<LifecycleProvider> lifecycle) {
        this.lifecycle = lifecycle;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long,
            CommitEntity> callback) {
        RetrofitClient.getInstance().sendHttpRequestIO(new BaseApi(new HttpOnNextListener() {
            @Override
            public void onSubscribe() {
                networkState.postValue(NetworkState.LOADING);
                initialLoad.postValue(NetworkState.LOADING);
            }

            @Override
            public void onNext(Object o) {
                callback.onResult((List<CommitEntity>) o, null, 2L);
            }

            @Override
            public void onError(HandlerException.ResponseThrowable e) {
                NetworkState error = new NetworkState(NetworkState.Status.FAILED, e.getMessage());
                networkState.postValue(error);
                initialLoad.postValue(error);
            }

            @Override
            public void onComplete() {
                networkState.postValue(NetworkState.LOADED);
                initialLoad.postValue(NetworkState.LOADED);
            }
        }, lifecycle) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                return retrofit.create(MyApiService.class).getCommits1("ThirtyDegreesRay", "OpenHub",
                        params.requestedLoadSize, 1);
            }
        });
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
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, e.getMessage()));
            }

            @Override
            public void onComplete() {
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
