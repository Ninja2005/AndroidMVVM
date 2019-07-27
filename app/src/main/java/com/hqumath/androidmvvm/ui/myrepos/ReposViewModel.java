package com.hqumath.androidmvvm.ui.myrepos;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.data.MyRepository;
import com.hqumath.androidmvvm.entity.CommitEntity;
import com.hqumath.androidmvvm.entity.ReposEntity;
import com.hqumath.androidmvvm.http.BaseApi;
import com.hqumath.androidmvvm.http.HandlerException;
import com.hqumath.androidmvvm.http.HttpOnNextListener;
import com.hqumath.androidmvvm.http.RetrofitClient;
import com.hqumath.androidmvvm.http.service.MyApiService;
import com.hqumath.androidmvvm.utils.StringUtils;
import com.hqumath.androidmvvm.utils.ToastUtil;

import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class ReposViewModel extends BaseViewModel<MyRepository> {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public String reposName;//仓库名称
    public MutableLiveData<String> avatar_url = new MutableLiveData<>();//仓库信息
    public MutableLiveData<String> description = new MutableLiveData<>();
    public MutableLiveData<String> full_name = new MutableLiveData<>();
    public MutableLiveData<String> created_at = new MutableLiveData<>();
    public MutableLiveData<String> languageAndSize = new MutableLiveData<>();
    public MutableLiveData<List<CommitEntity>> list = new MutableLiveData<>();//提交记录

    public ReposViewModel(@NonNull Application application) {
        super(application);
        model = MyRepository.getInstance();
    }

    public void getData(String userName, String reposName) {
        this.reposName = reposName;
        getReposInfo(userName, reposName);
        getCommitList(userName, reposName);
    }

    /**
     * 仓库信息
     */
    public void getReposInfo(String userName, String reposName) {
        RetrofitClient.getInstance().sendHttpRequestIO(new BaseApi(new HttpOnNextListener() {
            @Override
            public void onSubscribe() {
                isLoading.postValue(true);
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
                isLoading.postValue(false);
            }

            @Override
            public void onError(HandlerException.ResponseThrowable e) {
                isLoading.postValue(false);
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

    /**
     * 提交记录
     */
    public void getCommitList(String userName, String reposName) {
        RetrofitClient.getInstance().sendHttpRequestIO(new BaseApi(new HttpOnNextListener() {
            @Override
            public void onSubscribe() {
                isLoading.postValue(true);
            }

            @Override
            public void onNext(Object o) {
                list.postValue((List<CommitEntity>) o);
                isLoading.postValue(false);
            }

            @Override
            public void onError(HandlerException.ResponseThrowable e) {
                isLoading.postValue(false);
                appExecutors.mainThread().execute(() -> ToastUtil.toast(e.getMessage()));
            }

            @Override
            public void onComplete() {
            }
        }, getLifecycleProvider()) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                return retrofit.create(MyApiService.class).getCommits(userName, reposName);
            }
        });
    }
}
