package com.hqumath.androidmvvm.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProviders;
import com.bumptech.glide.Glide;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.base.BaseViewModelActivity;
import com.hqumath.androidmvvm.databinding.ActivityProfileBinding;

/**
 * ****************************************************************
 * 文件名称: ProfileActivity
 * 作    者: Created by gyd
 * 创建时间: 2019/7/26 10:04
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class ProfileActivity extends BaseViewModelActivity<ActivityProfileBinding, ProfileViewModel> {

    private String userName;

    @Override
    public ProfileViewModel getViewModel() {
        return ViewModelProviders.of(this).get(ProfileViewModel.class);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_profile;
    }

    @Override
    public void initView() {
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        binding.swipeRefreshLayout.setOnRefreshListener(() -> viewModel.getData(userName));
    }

    @Override
    public void initData() {
        userName = getIntent().getStringExtra("UserName");

        binding.setViewModel(viewModel);
        viewModel.getData(userName);
        binding.swipeRefreshLayout.setRefreshing(true);
    }

    public void initViewObservable() {
        viewModel.isLoading.observe(this, b -> {
            if (!b) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
        viewModel.avatarUrl.observe(this, url -> {
            Glide.with(mContext).load(viewModel.avatarUrl.getValue()).into(binding.ivFace);
        });
    }
}
