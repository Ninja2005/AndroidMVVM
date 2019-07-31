package com.hqumath.androidmvvm.ui.paging;

import android.os.Bundle;
import androidx.lifecycle.ViewModelProviders;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.adapters.MyPagedListAdapter;
import com.hqumath.androidmvvm.base.BaseViewModelFragment;
import com.hqumath.androidmvvm.databinding.FragmentPagingDbBinding;
import com.hqumath.androidmvvm.databinding.FragmentPagingNetBinding;
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

    private MyPagedListAdapter adapter;

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
        binding.swipeRefreshLayout.setOnRefreshListener(() -> viewModel.getData());
    }

    @Override
    public void initData() {
        binding.setViewModel(viewModel);
        adapter = new MyPagedListAdapter(data -> {
            ToastUtil.toast(data.getLogin());
        });
        binding.list.setAdapter(adapter);
        viewModel.getData();
        binding.swipeRefreshLayout.setRefreshing(true);
    }

    public void initViewObservable() {
        viewModel.isLoading.observe(this, binding.swipeRefreshLayout::setRefreshing);
        viewModel.list.observe(this, adapter::submitList);
    }

}
