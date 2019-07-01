package com.hqumath.androidmvvm.ui.list;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.adapters.CommitListAdapter;
import com.hqumath.androidmvvm.base.BaseActivity;
import com.hqumath.androidmvvm.databinding.ActivityListBinding;
import com.hqumath.androidmvvm.entity.CommitEntity;
import com.hqumath.androidmvvm.utils.ToastUtil;

/**
 * ****************************************************************
 * 文件名称: ListActivity
 * 作    者: Created by gyd
 * 创建时间: 2019/6/4 16:52
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class ListActivity extends BaseActivity<ActivityListBinding, ListViewModel> {

    private CommitListAdapter adapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_list;
    }

    public ListViewModel initViewModel() {
        return ViewModelProviders.of(this).get(ListViewModel.class);
    }

    public void initData() {
        setTitle("Commits");
        binding.setLifecycleOwner(this);
        adapter = new CommitListAdapter(clickCallback);
        binding.list.setAdapter(adapter);
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refresh();
                binding.swipeRefresh.setRefreshing(false);
            }
        });
//        viewModel.refresh();
    }

    public void initViewObservable() {
        viewModel.getData().observe(this, pagedList -> {
            adapter.submitList(pagedList);
        });
        viewModel.getNetworkState().observe(this, networkState -> {
            adapter.setNetworkState(networkState);
        });
    }

    private CommitListAdapter.ClickCallback clickCallback = new CommitListAdapter.ClickCallback() {
        @Override
        public void onItemClick(@NonNull CommitEntity data) {
            ToastUtil.toast(getApplication(), data.getSha());
        }
    };
}
