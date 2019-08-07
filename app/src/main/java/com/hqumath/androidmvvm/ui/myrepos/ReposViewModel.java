package com.hqumath.androidmvvm.ui.myrepos;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.data.MyRepository;
import com.hqumath.androidmvvm.data.paging.CommitFactory;
import com.hqumath.androidmvvm.data.paging.CommitSource;
import com.hqumath.androidmvvm.entity.CommitEntity;
import com.hqumath.androidmvvm.entity.NetworkState;
import com.hqumath.androidmvvm.entity.ReposEntity;
import com.hqumath.androidmvvm.http.BaseApi;
import com.hqumath.androidmvvm.http.HandlerException;
import com.hqumath.androidmvvm.http.HttpOnNextListener;
import com.hqumath.androidmvvm.http.RetrofitClient;
import com.hqumath.androidmvvm.http.service.MyApiService;
import com.hqumath.androidmvvm.utils.StringUtils;
import com.hqumath.androidmvvm.utils.ToastUtil;
import io.reactivex.Observable;
import retrofit2.Retrofit;

import java.util.Locale;

public class ReposViewModel extends BaseViewModel<MyRepository> {

    private CommitFactory factory;

    //public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private String userName, reposName;
    public MutableLiveData<String> avatar_url = new MutableLiveData<>();//仓库信息
    public MutableLiveData<String> description = new MutableLiveData<>();
    public MutableLiveData<String> full_name = new MutableLiveData<>();
    public MutableLiveData<String> created_at = new MutableLiveData<>();
    public MutableLiveData<String> languageAndSize = new MutableLiveData<>();

    public LiveData<PagedList<CommitEntity>> list;
    public LiveData<NetworkState> networkState;//网络状态
    public LiveData<NetworkState> refreshState;//初始化加载状态
    private int pageSize = 10;//每页大小
    private int initialLoadPage = 3;//预加载页数

    public ReposViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(String userName, String reposName) {
        this.userName = userName;
        this.reposName = reposName;
        getReposInfo(userName, reposName);

        factory = new CommitFactory(pageSize, userName, reposName);
        networkState = Transformations.switchMap(factory.getSourceLiveData(), source -> source.networkState);
        refreshState = Transformations.switchMap(factory.getSourceLiveData(), source -> source.initialLoad);

        list = new LivePagedListBuilder<>(
                factory,
                new PagedList.Config.Builder()
                        .setPageSize(pageSize)
                        .setInitialLoadSizeHint(pageSize * initialLoadPage)
                        .setEnablePlaceholders(false)//不明确item数目
                        .build())
                .setFetchExecutor(appExecutors.networkIO())
                .build();
    }

    /**
     * 仓库信息
     */
    public void getReposInfo(String userName, String reposName) {
        RetrofitClient.getInstance().sendHttpRequestIO(new BaseApi(new HttpOnNextListener() {
            @Override
            public void onSubscribe() {
                //isLoading.postValue(true);
            }

            @Override
            public void onNext(Object o) {
                ReposEntity data = (ReposEntity) o;
                avatar_url.postValue(data.getOwner().getAvatar_url());
                description.postValue(data.getDescription());
                full_name.postValue(data.getFull_name());
                created_at.postValue(data.getCreated_at().replace("T", " ")
                        .replace("Z", ""));
                String info = String.format(Locale.getDefault(), "Language %s, size %s",
                        data.getLanguage(), StringUtils.getSizeString(data.getSize() * 1024));
                languageAndSize.postValue(info);
                //isLoading.postValue(false);
            }

            @Override
            public void onError(HandlerException.ResponseThrowable e) {
                //isLoading.postValue(false);
                appExecutors.mainThread().execute(() -> ToastUtil.toast(e.getMessage()));
            }

            @Override
            public void onComplete() {
            }
        }, getLifecycleProvider()) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                return retrofit.create(MyApiService.class).getReposInfo(userName, reposName);
            }
        });
    }

    public void refresh() {
        getReposInfo(userName, reposName);

        CommitSource source = factory.getSourceLiveData().getValue();
        if (source != null)
            source.invalidate();
    }

    public void retry() {
        CommitSource source = factory.getSourceLiveData().getValue();
        if (source != null)
            source.retryAllFailed();
    }
}
