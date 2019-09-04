package com.hqumath.androidmvvm.ui.repos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.adapters.MyReposPagedListAdapter;
import com.hqumath.androidmvvm.base.BaseViewModelFragment;
import com.hqumath.androidmvvm.databinding.FragmentSwipeListBinding;
import com.hqumath.androidmvvm.entity.NetworkState;
import com.hqumath.androidmvvm.entity.ReposEntity;
import com.hqumath.androidmvvm.utils.LogUtil;

/**
 * ****************************************************************
 * 文件名称: MyReposFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/7/24 15:41
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class MyReposFragment extends BaseViewModelFragment<FragmentSwipeListBinding, MyReposViewModel> {

    private MyReposPagedListAdapter adapter;

    @Override
    public MyReposViewModel getViewModel() {
        return ViewModelProviders.of(this).get(MyReposViewModel.class);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.fragment_swipe_list;
    }

    @Override
    public void initView() {
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        binding.swipeRefreshLayout.setOnRefreshListener(viewModel::refresh);
    }

    @Override
    public void initData() {
        viewModel.init();
        adapter = new MyReposPagedListAdapter(new MyReposPagedListAdapter.ClickCallback() {
            @Override
            public void onClick(@NonNull ReposEntity data) {
                Intent intent = new Intent(mContext, ReposDetailActivity.class);
                intent.putExtra("name", data.getName());
                intent.putExtra("login", data.getOwner().getLogin());
                startActivity(intent);
            }

            @Override
            public void onRetry() {
                viewModel.retry();
            }
        });
        binding.list.setAdapter(adapter);
    }

    public void initViewObservable() {
        viewModel.list.observe(this, adapter::submitList);
        viewModel.refreshState.observe(this, state ->
                binding.swipeRefreshLayout.setRefreshing(state == NetworkState.LOADING));
        viewModel.networkState.observe(this, adapter::setNetworkState);
    }
}
