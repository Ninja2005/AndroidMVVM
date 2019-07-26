package com.hqumath.androidmvvm.ui.myrepos;

import android.os.Bundle;
import androidx.lifecycle.ViewModelProviders;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.adapters.MyReposListAdapter;
import com.hqumath.androidmvvm.base.BaseViewModelFragment;
import com.hqumath.androidmvvm.databinding.FragmentMyreposBinding;

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
public class MyReposFragment extends BaseViewModelFragment<FragmentMyreposBinding, MyReposViewModel> {

    private MyReposListAdapter adapter;

    @Override
    public MyReposViewModel getViewModel() {
        return ViewModelProviders.of(this).get(MyReposViewModel.class);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.fragment_myrepos;
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
