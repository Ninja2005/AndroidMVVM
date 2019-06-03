package com.hqumath.androidmvvm.ui.login;

import android.app.Application;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.data.DemoRepository;
import com.hqumath.androidmvvm.utils.ToastUtil;

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
public class LoginViewModel extends BaseViewModel<DemoRepository> {

    public MutableLiveData<String> userName = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application, DemoRepository repository) {
        super(application, repository);
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
        //
//        model
    }
}
