package com.hqumath.androidmvvm.ui.profile;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.data.MyRepository;
import com.hqumath.androidmvvm.entity.UserInfoEntity;
import com.hqumath.androidmvvm.http.BaseApi;
import com.hqumath.androidmvvm.http.HandlerException;
import com.hqumath.androidmvvm.http.HttpOnNextListener;
import com.hqumath.androidmvvm.http.RetrofitClient;
import com.hqumath.androidmvvm.http.service.MyApiService;
import com.hqumath.androidmvvm.utils.ToastUtil;
import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * ****************************************************************
 * 文件名称: ProfileViewModel
 * 作    者: Created by gyd
 * 创建时间: 2019/7/17 11:52
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class ProfileViewModel extends BaseViewModel<MyRepository> {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<String> login = new MutableLiveData<>();
    public MutableLiveData<String> avatarUrl = new MutableLiveData<>();
    public MutableLiveData<String> location = new MutableLiveData<>();
    public MutableLiveData<String> createdTime = new MutableLiveData<>();

    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<String> company = new MutableLiveData<>();
    public MutableLiveData<String> blog = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        model = MyRepository.getInstance();
    }

    /**
     * 获取用户信息
     */
    public void getData(String userName) {
        RetrofitClient.getInstance().sendHttpRequestIO(new BaseApi(new HttpOnNextListener() {
            @Override
            public void onSubscribe() {
                isLoading.postValue(true);
            }

            @Override
            public void onNext(Object o) {
                UserInfoEntity user = (UserInfoEntity)o;
                login.postValue(user.getLogin());
                avatarUrl.postValue(user.getAvatar_url());
                location.postValue(user.getLocation());
                createdTime.postValue(user.getCreated_at().replace("T", " ").replace("Z", ""));
                name.postValue(user.getName());
                company.postValue(user.getCompany());
                blog.postValue(user.getBlog());
                email.postValue(user.getEmail());
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
                return retrofit.create(MyApiService.class).getUserInfo(userName);
            }
        });
    }

}
