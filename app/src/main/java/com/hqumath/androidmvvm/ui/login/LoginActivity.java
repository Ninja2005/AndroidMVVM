package com.hqumath.androidmvvm.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.lifecycle.ViewModelProviders;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.base.BaseViewModelActivity;
import com.hqumath.androidmvvm.databinding.ActivityLoginBinding;
import com.hqumath.androidmvvm.ui.main.MainActivity;
import com.hqumath.androidmvvm.utils.ToastUtil;

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
public class LoginActivity extends BaseViewModelActivity<ActivityLoginBinding, LoginViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public LoginViewModel getViewModel() {
        return ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    @Override
    public void initView() {
        binding.btnLogin.setOnClickListener(v -> {
            boolean valid = true;
            if (TextUtils.isEmpty(viewModel.userName.getValue())) {
                valid = false;
                binding.userNameLayout.setError(getString(R.string.user_name_warning));
            } else {
                binding.userNameLayout.setErrorEnabled(false);
            }
            if (TextUtils.isEmpty(viewModel.password.getValue())) {
                valid = false;
                binding.passwordLayout.setError(getString(R.string.password_warning));
            } else {
                binding.passwordLayout.setErrorEnabled(false);
            }
            if (valid)
                viewModel.login();
        });

    }


    public void initData() {
        binding.setViewModel(viewModel);
    }

    public void initViewObservable() {
        viewModel.isLogin().observe(this, b -> {
            if (b) {
                startActivity(new Intent(mContext, MainActivity.class));
                finish();
            }
        });
    }
}
