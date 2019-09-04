package com.hqumath.androidmvvm.ui.about;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.base.BaseViewModelFragment;
import com.hqumath.androidmvvm.databinding.FragmentAboutBinding;
import com.hqumath.androidmvvm.ui.repos.ReposDetailActivity;
import com.hqumath.androidmvvm.ui.profile.ProfileActivity;
import com.hqumath.androidmvvm.utils.LogUtil;

/**
 * ****************************************************************
 * 文件名称: AboutFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/7/25 11:36
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class AboutFragment extends BaseViewModelFragment<FragmentAboutBinding, AboutViewModel> {
    @Override
    public AboutViewModel getViewModel() {
        return ViewModelProviders.of(this).get(AboutViewModel.class);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.fragment_about;
    }

    @Override
    public void initView() {
        binding.llSourcecode.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ReposDetailActivity.class);
            intent.putExtra("name", "AndroidMVVM");
            intent.putExtra("login", "ninja2005");
            startActivity(intent);
        });
        binding.llProfile.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ProfileActivity.class);
            intent.putExtra("UserName", "ninja2005");
            startActivity(intent);
        });
    }

    @Override
    public void initData() {
        binding.setViewModel(viewModel);
    }
}
