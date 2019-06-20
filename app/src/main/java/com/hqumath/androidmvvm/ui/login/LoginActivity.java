package com.hqumath.androidmvvm.ui.login;

import android.content.Intent;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProviders;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.base.BaseActivity;
import com.hqumath.androidmvvm.databinding.ActivityLoginBinding;
import com.hqumath.androidmvvm.ui.activitylist.ActivityListActivity;

/**
 * ****************************************************************
 * 文件名称: LoginActivity
 * 作    者: Created by gyd
 * 创建时间: 2019/6/3 10:26
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    public LoginViewModel initViewModel() {
        return ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    public void initData() {
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }

    public void initViewObservable() {
        viewModel.isLogin().observe(this, b -> {
            if (b) {
                startActivity(new Intent(LoginActivity.this, ActivityListActivity.class));
                finish();
            }
        });
    }
}
