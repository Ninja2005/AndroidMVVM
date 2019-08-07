package com.hqumath.androidmvvm.ui.following;

import android.content.Intent;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProviders;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.adapters.UserListAdapter;
import com.hqumath.androidmvvm.base.BaseViewModelFragment;
import com.hqumath.androidmvvm.databinding.FragmentSwipeListBinding;
import com.hqumath.androidmvvm.ui.profile.ProfileActivity;

/**
 * ****************************************************************
 * 文件名称: FollowingFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/7/24 15:41
 * 文件描述: 我追随的 不分页
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class FollowingFragment extends BaseViewModelFragment<FragmentSwipeListBinding, FollowingViewModel> {

    private UserListAdapter adapter;

    @Override
    public FollowingViewModel getViewModel() {
        return ViewModelProviders.of(this).get(FollowingViewModel.class);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.fragment_swipe_list;
    }

    @Override
    public void initView() {
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        binding.swipeRefreshLayout.setOnRefreshListener(viewModel::getData);
    }

    @Override
    public void initData() {
        adapter = new UserListAdapter(data -> {
            Intent intent = new Intent(mContext, ProfileActivity.class);
            intent.putExtra("UserName", data.getLogin());
            startActivity(intent);
        });
        binding.list.setAdapter(adapter);
        viewModel.getData();
        binding.swipeRefreshLayout.setRefreshing(true);
    }

    public void initViewObservable() {
        viewModel.isLoading.observe(this, binding.swipeRefreshLayout::setRefreshing);
        viewModel.list.observe(this, adapter::setData);
    }
}
