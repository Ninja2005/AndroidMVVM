package com.hqumath.androidmvvm.ui.list;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.data.MyRepository;
import com.hqumath.androidmvvm.datasource.CommitFactory;
import com.hqumath.androidmvvm.datasource.CommitSource;
import com.hqumath.androidmvvm.entity.CommitEntity;
import com.hqumath.androidmvvm.entity.NetworkState;

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
    private LiveData<NetworkState> networkState;//请求状态
    private LiveData<PagedList<CommitEntity>> list;

    public ListViewModel(@NonNull Application application) {
        super(application);
        //model = MyRepository.getInstance();
        init();
    }

    private void init() {

        CommitFactory commitFactory = new CommitFactory();
        //转换操作
        networkState = Transformations.switchMap(commitFactory.getSourceLiveData(), CommitSource::getNetworkState);

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(10).build();
//
        list = new LivePagedListBuilder<>(commitFactory, pagedListConfig).build();
    }
    /*public void refresh() {
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
    }*/

    public LiveData<PagedList<CommitEntity>> getData() {
        return list;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }
}
