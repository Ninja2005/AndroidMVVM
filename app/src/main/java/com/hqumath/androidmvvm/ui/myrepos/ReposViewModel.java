package com.hqumath.androidmvvm.ui.myrepos;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.data.MyRepository;
import com.hqumath.androidmvvm.entity.ReposEntity;
import com.hqumath.androidmvvm.entity.UserInfoEntity;
import com.hqumath.androidmvvm.http.BaseApi;
import com.hqumath.androidmvvm.http.HandlerException;
import com.hqumath.androidmvvm.http.HttpOnNextListener;
import com.hqumath.androidmvvm.http.RetrofitClient;
import com.hqumath.androidmvvm.http.service.MyApiService;
import com.hqumath.androidmvvm.utils.ToastUtil;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class ReposViewModel extends BaseViewModel<MyRepository> {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public ReposEntity data;
//    public MutableLiveData<String> avatarUrl = new MutableLiveData<>();
//    public MutableLiveData<String> name = new MutableLiveData<>();
//    public MutableLiveData<String> description = new MutableLiveData<>();
//    public MutableLiveData<String> full_name = new MutableLiveData<>();
//    public MutableLiveData<String> created_at = new MutableLiveData<>();
//    public MutableLiveData<String> language = new MutableLiveData<>();
//    public MutableLiveData<String> size = new MutableLiveData<>();



    public ReposViewModel(@NonNull Application application) {
        super(application);
        model = MyRepository.getInstance();
    }

    public void getData() {
        isLoading.postValue(false);
        /*RetrofitClient.getInstance().sendHttpRequestIO(new BaseApi(new HttpOnNextListener() {
            @Override
            public void onSubscribe() {
                isLoading.postValue(true);
            }

            @Override
            public void onNext(Object o) {
//                UserInfoEntity user = (UserInfoEntity)o;
//                avatarUrl.postValue(user.getAvatar_url());
//                name.postValue(user.getName());
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
                return retrofit.create(MyApiService.class).getUserInfo("");
            }
        });*/
    }
}
