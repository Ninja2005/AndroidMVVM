package com.hqumath.androidmvvm.datasource;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;
import com.hqumath.androidmvvm.data.MyApiService;
import com.hqumath.androidmvvm.entity.CommitEntity;
import com.hqumath.androidmvvm.entity.NetworkState;
import com.hqumath.androidmvvm.http.BaseApi;
import com.hqumath.androidmvvm.http.HandlerException;
import com.hqumath.androidmvvm.http.HttpOnNextListener;
import com.hqumath.androidmvvm.http.RetrofitClient;
import io.reactivex.Observable;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ****************************************************************
 * 文件名称: CommitSource
 * 作    者: Created by gyd
 * 创建时间: 2019/6/28 11:56
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class CommitSource extends PageKeyedDataSource<Long, CommitEntity> {
    private MutableLiveData<NetworkState> networkState = new MutableLiveData<>();//请求状态

    public CommitSource() {
    }

    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params,
                            @NonNull LoadInitialCallback<Long, CommitEntity> callback) {
        RetrofitClient.getInstance().sendHttpRequestIO(new BaseApi(new HttpOnNextListener() {
            @Override
            public void onSubscribe() {
                networkState.postValue(new NetworkState(NetworkState.Status.LOADING, ""));
            }

            @Override
            public void onNext(Object o) {
                callback.onResult((List<CommitEntity>) o, null, 2l);
            }

            @Override
            public void onError(HandlerException.ResponseThrowable e) {
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, e.getMessage()));
            }

            @Override
            public void onComplete() {
                networkState.postValue(new NetworkState(NetworkState.Status.SUCCESS, ""));
            }
        }, null) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                Map<String, Object> map = new HashMap<>();
                map.put("per_page", params.requestedLoadSize);
                map.put("page", 1);
                map.put("sha", "master");
                return retrofit.create(MyApiService.class).getActivityList(map);
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
                networkState.postValue(new NetworkState(NetworkState.Status.LOADING, ""));
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
                networkState.postValue(new NetworkState(NetworkState.Status.SUCCESS, ""));
            }
        }, null) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                Map<String, Object> map = new HashMap<>();
                map.put("per_page", params.requestedLoadSize);
                map.put("page", params.key);
                map.put("sha", "master");
                return retrofit.create(MyApiService.class).getActivityList(map);
            }
        });
    }
}
