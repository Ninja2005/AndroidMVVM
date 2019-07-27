package com.hqumath.androidmvvm.ui.myrepos;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.adapters.CommitListAdapter;
import com.hqumath.androidmvvm.base.BaseViewModelActivity;
import com.hqumath.androidmvvm.databinding.ActivityReposBinding;
import com.hqumath.androidmvvm.entity.ReposEntity;
import com.hqumath.androidmvvm.ui.profile.ProfileActivity;
import com.hqumath.androidmvvm.utils.StringUtils;

import java.util.Locale;

public class ReposActivity extends BaseViewModelActivity<ActivityReposBinding, ReposViewModel> {

    private CommitListAdapter adapter;
    private String userName, reposName;
    
    @Override
    public ReposViewModel getViewModel() {
        return ViewModelProviders.of(this).get(ReposViewModel.class);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_repos;
    }

    @Override
    public void initView() {
        setSupportActionBar(binding.toolbar);

        binding.toolbar.setNavigationOnClickListener(v -> finish());
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        binding.swipeRefreshLayout.setOnRefreshListener(() -> viewModel.getData(userName, reposName));

        //状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void initData() {
        //data
        reposName = getIntent().getStringExtra("name");
        userName = getIntent().getStringExtra("login");
        //ui
        setTitle(reposName);
        binding.setViewModel(viewModel);
        adapter = new CommitListAdapter(data -> {});
        binding.list.setAdapter(adapter);
        viewModel.getData(userName, reposName);
        binding.swipeRefreshLayout.setRefreshing(true);
    }

    public void initViewObservable() {
        viewModel.isLoading.observe(this, b -> {
            if (!b) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
        viewModel.avatar_url.observe(this, str -> {
            Glide.with(mContext).load(str).into(binding.ivAvatarBg);
        });
        viewModel.list.observe(this, list -> {
            adapter.setData(list);
        });
    }
}
