package com.hqumath.androidmvvm.ui.activitylist;

import android.os.Bundle;
import androidx.lifecycle.ViewModelProviders;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.base.BaseActivity;
import com.hqumath.androidmvvm.databinding.ActivityActivityListBinding;
import com.hqumath.androidmvvm.ui.login.LoginViewModel;

/**
 * ****************************************************************
 * 文件名称: ActivityListActivity
 * 作    者: Created by gyd
 * 创建时间: 2019/6/4 16:52
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class ActivityListActivity extends BaseActivity<ActivityActivityListBinding, ActivityListViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_activity_list;
    }

    public ActivityListViewModel initViewModel() {
        return ViewModelProviders.of(this).get(ActivityListViewModel.class);
    }

    public void initData() {
//        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }

    public void initViewObservable() {
    }
}
