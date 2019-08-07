package com.hqumath.androidmvvm.ui.paging;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.data.MyRepository;
import com.hqumath.androidmvvm.data.paging.UserInfoBoundaryCallback;
import com.hqumath.androidmvvm.entity.NetworkState;
import com.hqumath.androidmvvm.entity.UserInfoEntity;
import com.hqumath.androidmvvm.http.RetrofitClient;
import com.hqumath.androidmvvm.http.service.MyApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

/**
 * ****************************************************************
 * 文件名称: PagingNetAndDbViewModel
 * 作    者: Created by gyd
 * 创建时间: 2019/8/5 14:30
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class PagingNetAndDbViewModel extends BaseViewModel<MyRepository> {

    public LiveData<PagedList<UserInfoEntity>> list;
    public LiveData<NetworkState> networkState;//网络状态
    public MutableLiveData<NetworkState> refreshState = new MutableLiveData<>();//初始化加载状态
    private int pageSize = 10;
    private UserInfoBoundaryCallback boundaryCallback;


    public PagingNetAndDbViewModel(@NonNull Application application) {
        super(application);
        model = MyRepository.getInstance();
    }

    public void init() {
        boundaryCallback = new UserInfoBoundaryCallback(this::insertResultIntoDb, pageSize);

        list = new LivePagedListBuilder<>(
                model.loadAllUsers2(),
                new PagedList.Config.Builder()
                        .setPageSize(pageSize)
                        .setInitialLoadSizeHint(pageSize)
                        .build())
                .setFetchExecutor(appExecutors.diskIO())
                .setBoundaryCallback(boundaryCallback)
                .build();

        networkState = boundaryCallback.networkState;
    }

    public void refresh() {
        refreshState.postValue(NetworkState.LOADING);
        RetrofitClient.getInstance().getRetrofit().create(MyApiService.class)
                .getFollowers("JakeWharton", pageSize, 1)
                .enqueue(new Callback<List<UserInfoEntity>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<UserInfoEntity>> call,
                                           @NonNull Response<List<UserInfoEntity>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            appExecutors.diskIO().execute(() -> {
                                model.runInTransaction(() -> {
                                    model.deleteAllUsers();
                                    insertResultIntoDb(response.body());
                                });
                                refreshState.postValue(NetworkState.LOADED);
                            });
                        } else {
                            refreshState.postValue(new NetworkState(NetworkState.Status.FAILED,
                                    "error: " + response.code() + " " + response.message()));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<UserInfoEntity>> call, @NonNull Throwable t) {
                        refreshState.postValue(new NetworkState(NetworkState.Status.FAILED, t.getMessage()));
                    }
                });
    }

    public void retry() {
        boundaryCallback.helper.retryAllFailed();
    }

    /**
     * Inserts the response into the database while also assigning position indices to items.
     * 插入数据库，并标注位置
     */
    private void insertResultIntoDb(List<UserInfoEntity> list) {
        model.runInTransaction(() -> {
            int start = model.getNextIndexInUsers();
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setIndexInResponse(start + i);
            }
            model.insertAllUsers(list);
        });
    }
}
