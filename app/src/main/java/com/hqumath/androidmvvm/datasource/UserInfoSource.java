package com.hqumath.androidmvvm.datasource;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;
import com.hqumath.androidmvvm.entity.UserInfoEntity;
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
 * 文件名称: UserInfoSource
 * 作    者: Created by gyd
 * 创建时间: 2019/7/31 14:47
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class UserInfoSource extends PageKeyedDataSource<Long, UserInfoEntity> {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private WeakReference<LifecycleProvider> lifecycle;

    public UserInfoSource(WeakReference<LifecycleProvider> lifecycle) {
        this.lifecycle = lifecycle;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long,
            UserInfoEntity> callback) {
        RetrofitClient.getInstance().sendHttpRequestIO(new BaseApi(new HttpOnNextListener() {
            @Override
            public void onSubscribe() {
                isLoading.postValue(true);
            }

            @Override
            public void onNext(Object o) {
                callback.onResult((List<UserInfoEntity>) o, null, 2l);
            }

            @Override
            public void onError(HandlerException.ResponseThrowable e) {
                isLoading.postValue(false);
            }

            @Override
            public void onComplete() {
                isLoading.postValue(false);
            }
        }, lifecycle) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                return retrofit.create(MyApiService.class).getFollowing1(params.requestedLoadSize, 1);
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, UserInfoEntity> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, UserInfoEntity> callback) {
        RetrofitClient.getInstance().sendHttpRequestIO(new BaseApi(new HttpOnNextListener() {
            @Override
            public void onSubscribe() {
                isLoading.postValue(true);
            }

            @Override
            public void onNext(Object o) {
                List<UserInfoEntity> list = (List<UserInfoEntity>) o;
                callback.onResult((List<UserInfoEntity>) o, (list != null && list.size() > 0) ? params.key + 1 : null);
            }

            @Override
            public void onError(HandlerException.ResponseThrowable e) {
                isLoading.postValue(false);
            }

            @Override
            public void onComplete() {
                isLoading.postValue(false);
            }
        }, lifecycle) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                return retrofit.create(MyApiService.class).getFollowing1(params.requestedLoadSize, params.key);
            }
        });
    }
}
