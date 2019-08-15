package com.hqumath.androidmvvm.ui.profile;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.ViewModelProviders;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.base.BaseViewModelActivity;
import com.hqumath.androidmvvm.databinding.ActivityProfileBinding;
import com.hqumath.androidmvvm.utils.Util;

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
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        binding.swipeRefreshLayout.setOnRefreshListener(() -> viewModel.getData(userName));

        //状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void initData() {
        userName = getIntent().getStringExtra("UserName");
        //binding.toolbar.setTitle(userName);
        setTitle(userName);
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
            Glide.with(mContext).load(url).into(binding.ivAvatarBg);
            Glide.with(Util.getContext())
                    .load(url)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))//圆形
                    .into(binding.ivAvatar);
        });
        viewModel.company.observe(this, value -> binding.company.setVisibility(TextUtils.isEmpty(value) ? View.GONE :
                View.VISIBLE));
        viewModel.email.observe(this, value -> binding.email.setVisibility(TextUtils.isEmpty(value) ? View.GONE :
                View.VISIBLE));
        viewModel.blog.observe(this, value -> binding.link.setVisibility(TextUtils.isEmpty(value) ? View.GONE :
                View.VISIBLE));
    }
}
