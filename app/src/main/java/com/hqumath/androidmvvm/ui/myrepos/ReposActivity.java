package com.hqumath.androidmvvm.ui.myrepos;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import com.bumptech.glide.Glide;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.adapters.CommitPagedListAdapter;
import com.hqumath.androidmvvm.base.BaseViewModelActivity;
import com.hqumath.androidmvvm.databinding.ActivityReposBinding;
import com.hqumath.androidmvvm.entity.CommitEntity;
import com.hqumath.androidmvvm.entity.NetworkState;

public class ReposActivity extends BaseViewModelActivity<ActivityReposBinding, ReposViewModel> {

    private CommitPagedListAdapter adapter;
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
        binding.swipeRefreshLayout.setOnRefreshListener(viewModel::refresh);

        //状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void initData() {
        //data
        userName = getIntent().getStringExtra("login");
        reposName = getIntent().getStringExtra("name");
        //ui
        setTitle(reposName);
        binding.setViewModel(viewModel);
        viewModel.init(userName, reposName);
        adapter = new CommitPagedListAdapter(new CommitPagedListAdapter.ClickCallback() {
            @Override
            public void onClick(@NonNull CommitEntity data) {
            }

            @Override
            public void onRetry() {
                viewModel.retry();
            }
        });
        binding.list.setAdapter(adapter);
    }

    public void initViewObservable() {
        //viewModel.isLoading.observe(this, binding.swipeRefreshLayout::setRefreshing);
        viewModel.avatar_url.observe(this, str -> {
            Glide.with(mContext).load(str).into(binding.ivAvatarBg);
        });
        viewModel.list.observe(this, adapter::submitList);
        viewModel.refreshState.observe(this, state ->
                binding.swipeRefreshLayout.setRefreshing(state == NetworkState.LOADING));
        viewModel.networkState.observe(this, adapter::setNetworkState);
    }
}
