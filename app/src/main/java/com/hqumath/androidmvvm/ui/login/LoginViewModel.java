package com.hqumath.androidmvvm.ui.login;

import android.app.Application;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.data.MyRepository;

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
    private MutableLiveData<Boolean> isLogin = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        model = MyRepository.getInstance();
        //从本地取得数据绑定到View层
        userName.setValue(model.getUserName());
        password.setValue(model.getPassword());
        if (!TextUtils.isEmpty(model.getToken())) {
            isLogin.setValue(true);
        }
    }

    public void login() {
        appExecutors.diskIO().execute(() -> {
            isLoading.postValue(true);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            model.saveUserName(userName.getValue());
            model.savePassword(password.getValue());
            model.saveToken("1234");
            isLoading.postValue(false);
            isLogin.postValue(true);
        });
    }

    public LiveData<Boolean> isLogin() {
        return isLogin;
    }
}
