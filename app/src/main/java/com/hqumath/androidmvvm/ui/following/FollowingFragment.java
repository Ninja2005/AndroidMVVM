package com.hqumath.androidmvvm.ui.following;

import android.os.Bundle;
import androidx.lifecycle.ViewModelProviders;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.adapters.UserListAdapter;
import com.hqumath.androidmvvm.base.BaseViewModelFragment;
import com.hqumath.androidmvvm.databinding.FragmentFollowingBinding;

/**
 * ****************************************************************
 * 文件名称: FollowingFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/7/24 15:41
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class FollowingFragment extends BaseViewModelFragment<FragmentFollowingBinding, FollowingViewModel> {

    private UserListAdapter adapter;

    @Override
    public FollowingViewModel getViewModel() {
        return ViewModelProviders.of(this).get(FollowingViewModel.class);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.fragment_following;
    }

    @Override
    public void initView() {
        binding.swipeRefreshLayout.setOnRefreshListener(() -> viewModel.getData());
        binding.swipeRefreshLayout.setOnRefreshListener(() -> viewModel.getData());

    }

    @Override
    public void initData() {
        binding.setViewModel(viewModel);
        adapter = new UserListAdapter(data -> {
            /*Intent intent = new Intent(mContext, CheckInActActivity.class);
            intent.putExtra("CheckInActID", data.getId());
            startActivity(intent);*/
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
