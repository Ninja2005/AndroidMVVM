package com.hqumath.androidmvvm.data.paging;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;
import com.hqumath.androidmvvm.app.AppExecutors;
import com.hqumath.androidmvvm.entity.NetworkState;
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
 * 文件名称: UserInfoBoundaryCallback
 * 作    者: Created by gyd
 * 创建时间: 2019/8/5 15:11
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class UserInfoBoundaryCallback extends PagedList.BoundaryCallback<UserInfoEntity> {

    public PagingRequestHelper helper = new PagingRequestHelper(AppExecutors.getInstance().diskIO());
    public LiveData<NetworkState> networkState = createStatusLiveData(helper);

    private HandleResponseCallback handleResponseCallback;//处理结果
    private int pageSize;//分页大小
    private int initialLoadPage;//预加载页数
    private WeakReference<LifecycleProvider> lifecycle;

    public UserInfoBoundaryCallback(HandleResponseCallback callback, int pageSize, int initialLoadPage,
                                    WeakReference<LifecycleProvider> lifecycle) {
        this.handleResponseCallback = callback;
        this.pageSize = pageSize;
        this.initialLoadPage = initialLoadPage;
        this.lifecycle = lifecycle;
    }

    private LiveData<NetworkState> createStatusLiveData(PagingRequestHelper helper) {
        MutableLiveData<NetworkState> liveData = new MutableLiveData<>();
        helper.addListener(report -> {
            if (report.hasRunning()) {
                liveData.postValue(NetworkState.LOADING);
            } else if (report.hasError()) {
                Throwable throwable;
                if (report.initial == PagingRequestHelper.Status.FAILED) {
                    throwable = report.getErrorFor(PagingRequestHelper.RequestType.INITIAL);
                } else if (report.before == PagingRequestHelper.Status.FAILED) {
                    throwable = report.getErrorFor(PagingRequestHelper.RequestType.BEFORE);
                } else {
                    throwable = report.getErrorFor(PagingRequestHelper.RequestType.AFTER);
                }
                liveData.postValue(new NetworkState(NetworkState.Status.FAILED,
                        (throwable != null && !TextUtils.isEmpty(throwable.getMessage())) ? throwable.getMessage() :
                                "网络错误"));
            } else {
                liveData.postValue(NetworkState.LOADED);
            }
        });
        return liveData;
    }

    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    public void onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL, helperCallback -> {
            RetrofitClient.getInstance().sendHttpRequestIO(new BaseApi(new HttpOnNextListener<List<UserInfoEntity>>() {
                @Override
                public void onSubscribe() {
                }

                @Override
                public void onNext(List<UserInfoEntity> o) {
                    handleResponseCallback.insertResultIntoDb(o);
                    helperCallback.recordSuccess();
                }

                @Override
                public void onError(HandlerException.ResponseThrowable e) {
                    helperCallback.recordFailure(new Throwable(e.getMessage()));
                }

                @Override
                public void onComplete() {
                }
            }, lifecycle) {
                @Override
                public Observable getObservable(Retrofit retrofit) {
                    return retrofit.create(MyApiService.class).getFollowers1("JakeWharton",
                            pageSize * initialLoadPage, 1);
                }
            });
        });
    }

    public void onItemAtFrontLoaded(@NonNull UserInfoEntity itemAtFront) {
    }

    /**
     * User reached to the end of the list.
     */
    public void onItemAtEndLoaded(@NonNull UserInfoEntity itemAtEnd) {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER, helperCallback -> {
            RetrofitClient.getInstance().sendHttpRequestIO(new BaseApi(new HttpOnNextListener<List<UserInfoEntity>>() {
                @Override
                public void onSubscribe() {
                }

                @Override
                public void onNext(List<UserInfoEntity> o) {
                    handleResponseCallback.insertResultIntoDb(o);
                    helperCallback.recordSuccess();
                }

                @Override
                public void onError(HandlerException.ResponseThrowable e) {
                    helperCallback.recordFailure(new Throwable(e.getMessage()));
                }

                @Override
                public void onComplete() {
                }
            }, lifecycle) {
                @Override
                public Observable getObservable(Retrofit retrofit) {
                    //请求下页位置 需要+2
                    return retrofit.create(MyApiService.class).getFollowers1("JakeWharton", pageSize,
                            itemAtEnd.getIndexInResponse() / pageSize + 2);
                }
            });
        });
    }

    public interface HandleResponseCallback {
        void insertResultIntoDb(List<UserInfoEntity> response);
    }
}
