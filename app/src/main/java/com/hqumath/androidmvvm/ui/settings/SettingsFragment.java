package com.hqumath.androidmvvm.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.base.BaseViewModelFragment;
import com.hqumath.androidmvvm.databinding.FragmentSettingsBinding;
import com.hqumath.androidmvvm.ui.login.LoginActivity;
import com.hqumath.androidmvvm.utils.LogUtil;

/**
 * ****************************************************************
 * 文件名称: SettingsFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/7/25 11:17
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class SettingsFragment extends BaseViewModelFragment<FragmentSettingsBinding, SettingsViewModel> {
    @Override
    public SettingsViewModel getViewModel() {
        return ViewModelProviders.of(this).get(SettingsViewModel.class);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.fragment_settings;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        binding.setViewModel(viewModel);
    }

    public void initViewObservable() {
        viewModel.isLogout.observe(this, b -> {
            if (b) {
                startActivity(new Intent(mContext, LoginActivity.class));
                mContext.finish();
            }
        });
    }
}
