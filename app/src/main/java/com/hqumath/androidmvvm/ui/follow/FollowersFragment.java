package com.hqumath.androidmvvm.ui.follow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;

import com.hqumath.androidmvvm.adapters.MyRecyclerAdapters;
import com.hqumath.androidmvvm.base.BaseFragment;
import com.hqumath.androidmvvm.bean.UserInfoEntity;
import com.hqumath.androidmvvm.databinding.FragmentFollowersBinding;
import com.hqumath.androidmvvm.utils.CommonUtil;

/**
 * ****************************************************************
 * 文件名称: FollowersFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/11/5 10:06
 * 文件描述:
 * 注意事项: 使用DiffUtil比对更新，减少刷新量
 * 版权声明:
 * ****************************************************************
 */
public class FollowersFragment extends BaseFragment {

    private FragmentFollowersBinding binding;
    private FollowersViewModel viewModel;
    private MyRecyclerAdapters.FollowRecyclerAdapter recyclerAdapter;
    protected boolean hasRequested;//在onResume中判断是否已经请求过数据。用于懒加载

    @Override
    public View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFollowersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initListener() {
        binding.refreshLayout.setOnRefreshListener(v -> viewModel.getFollowers(true));
        binding.refreshLayout.setOnLoadMoreListener(v -> viewModel.getFollowers(false));
    }

    @Override
    protected void initData() {
        viewModel = new ViewModelProvider(requireActivity()).get(FollowersViewModel.class);

        recyclerAdapter = new MyRecyclerAdapters.FollowRecyclerAdapter(mContext, viewModel.mData);
        recyclerAdapter.setOnItemClickListener((v, position) -> {
            UserInfoEntity data = viewModel.mData.get(position);
            // startActivity(ProfileDetailActivity.getStartIntent(mContext, data.getLogin())); TODO
        });
        binding.recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    protected void initViewObservable() {
        viewModel.followersResultCode.observe(this, code -> {
            if (code.equals("0")) {
                recyclerAdapter.notifyDataSetChanged();
                if (viewModel.followersRefresh) {
                    if (viewModel.followersNewEmpty) {
                        binding.refreshLayout.finishRefreshWithNoMoreData();//上拉加载功能将显示没有更多数据
                    } else {
                        binding.refreshLayout.finishRefresh();
                    }
                } else {
                    if (viewModel.followersNewEmpty) {
                        binding.refreshLayout.finishLoadMoreWithNoMoreData();//上拉加载功能将显示没有更多数据
                    } else {
                        binding.refreshLayout.finishLoadMore();
                    }
                }
            } else {
                CommonUtil.toast(viewModel.followersResultMsg);
                if (viewModel.followersRefresh) {
                    binding.refreshLayout.finishRefresh(false);//刷新失败，会影响到上次的更新时间
                } else {
                    binding.refreshLayout.finishLoadMore(false);
                }
            }
            binding.emptyLayout.llEmpty.setVisibility(viewModel.mData.isEmpty() ? View.VISIBLE : View.GONE);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hasRequested) {
            hasRequested = true;
            binding.refreshLayout.autoRefresh();//触发自动刷新
        }
    }

    @Override
    public void onDestroy() {
        if (viewModel != null) {
            viewModel.dispose();
            viewModel = null;
        }
        super.onDestroy();
    }
}