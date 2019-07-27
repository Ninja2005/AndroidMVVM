package com.hqumath.androidmvvm.ui.starred;

import android.content.Intent;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProviders;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.adapters.MyReposListAdapter;
import com.hqumath.androidmvvm.base.BaseViewModelFragment;
import com.hqumath.androidmvvm.databinding.FragmentStarredBinding;
import com.hqumath.androidmvvm.ui.myrepos.ReposActivity;

/**
 * ****************************************************************
 * 文件名称: StarredFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/7/24 15:41
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class StarredFragment extends BaseViewModelFragment<FragmentStarredBinding, StarredViewModel> {

    private MyReposListAdapter adapter;

    @Override
    public StarredViewModel getViewModel() {
        return ViewModelProviders.of(this).get(StarredViewModel.class);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.fragment_starred;
    }

    @Override
    public void initView() {
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        binding.swipeRefreshLayout.setOnRefreshListener(() -> viewModel.getData());

    }

    @Override
    public void initData() {
        binding.setViewModel(viewModel);
        adapter = new MyReposListAdapter(data -> {
            Intent intent = new Intent(mContext, ReposActivity.class);
            intent.putExtra("name", data.getName());
            intent.putExtra("login", data.getOwner().getLogin());
            startActivity(intent);
        });
        binding.list.setAdapter(adapter);
        viewModel.getData();
        binding.swipeRefreshLayout.setRefreshing(true);
    }

    public void initViewObservable() {
        viewModel.isLoading.observe(this, b -> {
            if (!b) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
        viewModel.list.observe(this, list -> {
            adapter.setData(list);
        });
    }
}
