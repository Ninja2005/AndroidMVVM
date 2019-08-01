package com.hqumath.androidmvvm.ui.paging;

import android.os.Bundle;
import androidx.lifecycle.ViewModelProviders;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.adapters.CommitPagedListAdapter;
import com.hqumath.androidmvvm.base.BaseViewModelFragment;
import com.hqumath.androidmvvm.databinding.FragmentPagingNetBinding;
import com.hqumath.androidmvvm.entity.NetworkState;
import com.hqumath.androidmvvm.utils.ToastUtil;

/**
 * ****************************************************************
 * 文件名称: PagingDBFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/7/30 15:16
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class PagingNetFragment extends BaseViewModelFragment<FragmentPagingNetBinding, PagingNetViewModel> {

    private CommitPagedListAdapter adapter;

    @Override
    public PagingNetViewModel getViewModel() {
        return ViewModelProviders.of(this).get(PagingNetViewModel.class);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.fragment_paging_net;
    }

    @Override
    public void initView() {
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.refresh();
        });
    }

    @Override
    public void initData() {
        viewModel.init();

        binding.setViewModel(viewModel);
        adapter = new CommitPagedListAdapter(data -> {
            ToastUtil.toast(data.getSha());
        });
        binding.list.setAdapter(adapter);
    }

    public void initViewObservable() {
        viewModel.list.observe(this, adapter::submitList);
        viewModel.refreshState.observe(this, state ->
                binding.swipeRefreshLayout.setRefreshing(state == NetworkState.LOADING));
    }

}
