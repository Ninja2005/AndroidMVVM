package com.hqumath.androidmvvm.data.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;
import com.hqumath.androidmvvm.app.AppExecutors;
import com.hqumath.androidmvvm.entity.NetworkState;
import com.hqumath.androidmvvm.entity.UserInfoEntity;
import com.hqumath.androidmvvm.http.RetrofitClient;
import com.hqumath.androidmvvm.http.service.MyApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;
import java.util.concurrent.Executor;

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

    private Executor ioExecutor = AppExecutors.getInstance().diskIO();
    public PagingRequestHelper helper = new PagingRequestHelper(ioExecutor);
    public LiveData<NetworkState> networkState = createStatusLiveData(helper);

    private HandleResponseCallback handleResponseCallback;//处理结果
    private int pageSize;//分页大小

    public UserInfoBoundaryCallback(HandleResponseCallback callback, int pageSize) {
        this.handleResponseCallback = callback;
        this.pageSize = pageSize;
    }

    public LiveData<NetworkState> createStatusLiveData(PagingRequestHelper helper) {
        MutableLiveData<NetworkState> liveData = new MutableLiveData<>();
        helper.addListener(report -> {
            if (report.hasRunning()) {
                liveData.postValue(NetworkState.LOADING);
            } else if (report.hasError()) {
                //错误信息
                liveData.postValue(new NetworkState(NetworkState.Status.FAILED,
                        report.getErrorFor(PagingRequestHelper.RequestType.INITIAL).getMessage()));
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
            RetrofitClient.getInstance().getRetrofit().create(MyApiService.class)
                    .getFollowers("JakeWharton", pageSize, 1)
                    .enqueue(new Callback<List<UserInfoEntity>>() {
                        @Override
                        public void onResponse(@NonNull Call<List<UserInfoEntity>> call,
                                               @NonNull Response<List<UserInfoEntity>> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                ioExecutor.execute(() -> {
                                    handleResponseCallback.insertResultIntoDb(response.body());
                                    helperCallback.recordSuccess();
                                });
                            } else {
                                helperCallback.recordFailure(new Throwable("error code" + response.code()));
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<UserInfoEntity>> call, @NonNull Throwable t) {
                            helperCallback.recordFailure(t);
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
            //请求下页数据
            RetrofitClient.getInstance().getRetrofit().create(MyApiService.class)
                    .getFollowers("JakeWharton", pageSize, itemAtEnd.getIndexInResponse() / pageSize + 2)
                    .enqueue(new Callback<List<UserInfoEntity>>() {
                        @Override
                        public void onResponse(@NonNull Call<List<UserInfoEntity>> call,
                                               @NonNull Response<List<UserInfoEntity>> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                ioExecutor.execute(() -> {
                                    handleResponseCallback.insertResultIntoDb(response.body());
                                    helperCallback.recordSuccess();
                                });
                            } else {
                                helperCallback.recordFailure(new Throwable("error code" + response.code()));
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<UserInfoEntity>> call, @NonNull Throwable t) {
                            helperCallback.recordFailure(t);
                        }
                    });
        });
    }

    public interface HandleResponseCallback {
        void insertResultIntoDb(List<UserInfoEntity> response);
    }
}
