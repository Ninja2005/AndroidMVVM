package com.hqumath.androidmvvm.ui.paging;

import android.os.Bundle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.adapters.UserListAdapter;
import com.hqumath.androidmvvm.app.AppExecutors;
import com.hqumath.androidmvvm.base.BaseViewModelFragment;
import com.hqumath.androidmvvm.data.MyRepository;
import com.hqumath.androidmvvm.databinding.FragmentPagingBinding;
import com.hqumath.androidmvvm.entity.UserInfoEntity;

import java.util.List;

/**
 * ****************************************************************
 * 文件名称: PagingFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/7/30 15:16
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class PagingFragment extends BaseViewModelFragment<FragmentPagingBinding, PagingViewModel> {

    private UserListAdapter adapter;


    @Override
    public PagingViewModel getViewModel() {
        return ViewModelProviders.of(this).get(PagingViewModel.class);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.fragment_paging;
    }

    @Override
    public void initView() {
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        binding.swipeRefreshLayout.setOnRefreshListener(() -> viewModel.getData());
    }

    @Override
    public void initData() {
        binding.setViewModel(viewModel);
        adapter = new UserListAdapter(data -> {
            /*Intent intent = new Intent(mContext, ProfileActivity.class);
            intent.putExtra("UserName", data.getLogin());
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
        //列表绑定数据库
        AppExecutors appExecutors = AppExecutors.getInstance();
        MyRepository model = MyRepository.getInstance();
        appExecutors.diskIO().execute(() -> {
            LiveData<List<UserInfoEntity>> list = model.loadAllUsers();
            appExecutors.mainThread().execute(() -> {
                list.observe(this, data -> {
                    adapter.setData(data);
                    binding.executePendingBindings();
                });
            });
        });
    }

}
