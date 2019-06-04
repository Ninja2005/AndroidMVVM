package com.hqumath.androidmvvm.ui.login;

import android.app.Application;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.data.MyApiService;
import com.hqumath.androidmvvm.data.MyRepository;
import com.hqumath.androidmvvm.http.*;
import com.hqumath.androidmvvm.utils.ToastUtil;
import io.reactivex.Observable;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;

/**
 * ****************************************************************
 * 文件名称: LoginViewModel
 * 作    者: Created by gyd
 * 创建时间: 2019/6/3 10:27
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class LoginViewModel extends BaseViewModel<MyRepository> {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<String> userName = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        model = MyRepository.getInstance();
        //从本地取得数据绑定到View层
        userName.setValue(model.getUserName());
        password.setValue(model.getPassword());
    }

    public void login() {
        if (TextUtils.isEmpty(userName.getValue())) {
            ToastUtil.toast(getApplication(), "请输入账号！");
            return;
        }
        if (TextUtils.isEmpty(password.getValue())) {
            ToastUtil.toast(getApplication(), "请输入密码！");
            return;
        }

        RetrofitClient.getInstance().sendHttpRequest(new BaseApi(new HttpOnNextListener() {
            @Override
            public void onSubscribe() {
                isLoading.setValue(true);
            }

            @Override
            public void onNext(Object o) {
                model.saveUserName(userName.getValue());
                model.savePassword(password.getValue());
                ToastUtil.toast(getApplication(), "已登录");
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
                map.put("userName", userName.getValue());
                map.put("passWord", password.getValue());
                return retrofit.create(MyApiService.class).userLogin(map);
            }
        });
    }
}
