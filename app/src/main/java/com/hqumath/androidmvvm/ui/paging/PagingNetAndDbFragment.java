package com.hqumath.androidmvvm.ui.paging;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.adapters.UserPagedListAdapter;
import com.hqumath.androidmvvm.base.BaseViewModelFragment;
import com.hqumath.androidmvvm.databinding.FragmentPagingNetBinding;
import com.hqumath.androidmvvm.entity.NetworkState;
import com.hqumath.androidmvvm.entity.UserInfoEntity;
import com.hqumath.androidmvvm.utils.ToastUtil;

/**
 * ****************************************************************
 * 文件名称: PagingNetAndDbFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/8/5 14:28
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class PagingNetAndDbFragment extends BaseViewModelFragment<FragmentPagingNetBinding, PagingNetAndDbViewModel> {

    private UserPagedListAdapter adapter;

    @Override
    public PagingNetAndDbViewModel getViewModel() {
        return ViewModelProviders.of(this).get(PagingNetAndDbViewModel.class);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.fragment_paging_net;
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
                ToastUtil.toast(data.getLogin());
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
