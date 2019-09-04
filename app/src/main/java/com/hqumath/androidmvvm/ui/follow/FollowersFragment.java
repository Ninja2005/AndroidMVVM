package com.hqumath.androidmvvm.ui.follow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.adapters.UserPagedListAdapter;
import com.hqumath.androidmvvm.base.BaseViewModelFragment;
import com.hqumath.androidmvvm.databinding.FragmentSwipeListBinding;
import com.hqumath.androidmvvm.entity.NetworkState;
import com.hqumath.androidmvvm.entity.UserInfoEntity;
import com.hqumath.androidmvvm.ui.profile.ProfileActivity;
import com.hqumath.androidmvvm.utils.LogUtil;

/**
 * ****************************************************************
 * 文件名称: FollowingFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/7/24 15:41
 * 文件描述: 我的追随者 paging分页 db+net
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class FollowersFragment extends BaseViewModelFragment<FragmentSwipeListBinding, FollowersViewModel> {

    private UserPagedListAdapter adapter;

    @Override
    public FollowersViewModel getViewModel() {
        return ViewModelProviders.of(this).get(FollowersViewModel.class);
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
        adapter = new UserPagedListAdapter(new UserPagedListAdapter.ClickCallback() {
            @Override
            public void onClick(@NonNull UserInfoEntity data) {
                Intent intent = new Intent(mContext, ProfileActivity.class);
                intent.putExtra("UserName", data.getLogin());
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
