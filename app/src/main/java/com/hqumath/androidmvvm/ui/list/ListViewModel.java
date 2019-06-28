package com.hqumath.androidmvvm.ui.list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import androidx.paging.PagedList;
import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.data.MyApiService;
import com.hqumath.androidmvvm.data.MyRepository;
import com.hqumath.androidmvvm.entity.CommitEntity;
import com.hqumath.androidmvvm.entity.NetworkState;
import com.hqumath.androidmvvm.http.BaseApi;
import com.hqumath.androidmvvm.http.HandlerException;
import com.hqumath.androidmvvm.http.HttpOnNextListener;
import com.hqumath.androidmvvm.http.RetrofitClient;
import com.hqumath.androidmvvm.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * ****************************************************************
 * 文件名称: ListViewModel
 * 作    者: Created by gyd
 * 创建时间: 2019/6/4 16:54
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class ListViewModel extends BaseViewModel<MyRepository> {
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
//    private MediatorLiveData<List<CommitEntity>> list = new MediatorLiveData<>();
    private MutableLiveData<NetworkState> networkState;//请求状态
    private MutableLiveData<PagedList<CommitEntity>> list;

    public ListViewModel(@NonNull Application application) {
        super(application);
        //model = MyRepository.getInstance();
        init();
    }
    private void init() {

//        FeedDataFactory feedDataFactory = new FeedDataFactory(appController);
//        networkState = Transformations.switchMap(feedDataFactory.getMutableLiveData(),
//                dataSource -> dataSource.getNetworkState());
//
//        PagedList.Config pagedListConfig =
//                (new PagedList.Config.Builder())
//                        .setEnablePlaceholders(false)
//                        .setInitialLoadSizeHint(10)
//                        .setPageSize(20).build();
//
//        articleLiveData = new LivePagedListBuilder<>(feedDataFactory, pagedListConfig)
//                .setFetchExecutor(executor)
//                .build();
    }
    public void refresh() {
        RetrofitClient.getInstance().sendHttpRequest(new BaseApi(new HttpOnNextListener() {
            @Override
            public void onSubscribe() {
                isLoading.setValue(true);
            }

            @Override
            public void onNext(Object o) {
                list.setValue((List<CommitEntity>)o);
            }

            @Override
            public void onError(HandlerException.ResponseThrowable e) {
                isLoading.setValue(false);
                ToastUtil.toast(getApplication(), e.getMessage());

            }

            @Override
            public void onComplete() {
                isLoading.setValue(false);
            }
        }, getLifecycleProvider()) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                Map<String, Object> map = new HashMap<>();
                map.put("per_page", 10);
                map.put("page", 1);
                map.put("sha", "master");
                return retrofit.create(MyApiService.class).getActivityList(map);
            }
        });
    }

    public LiveData<PagedList<CommitEntity>> getData() {
        return list;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }
}
