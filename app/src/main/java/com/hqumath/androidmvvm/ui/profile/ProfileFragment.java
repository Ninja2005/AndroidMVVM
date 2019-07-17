package com.hqumath.androidmvvm.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProviders;
import com.bumptech.glide.Glide;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.base.BaseViewModelFragment;
import com.hqumath.androidmvvm.databinding.FragmentProfileBinding;

/**
 * ****************************************************************
 * 文件名称: ProfileFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/7/17 11:44
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class ProfileFragment extends BaseViewModelFragment<FragmentProfileBinding, ProfileViewModel> {
    @Override
    public ProfileViewModel getViewModel() {
        return ViewModelProviders.of(this).get(ProfileViewModel.class);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.fragment_profile;
    }

    @Override
    public void initView() {
        binding.swipeRefreshLayout.setOnRefreshListener(() -> viewModel.getData());
    }

    @Override
    public void initData() {
        binding.setViewModel(viewModel);
        viewModel.getData();
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
