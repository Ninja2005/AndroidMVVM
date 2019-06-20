package com.hqumath.androidmvvm.ui.activitylist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.base.BaseActivity;
import com.hqumath.androidmvvm.databinding.ActivityActivityListBinding;
import com.hqumath.androidmvvm.entity.ActivityEntity;
import com.hqumath.androidmvvm.entity.CommitEntity;
import com.hqumath.androidmvvm.ui.login.LoginViewModel;
import com.hqumath.androidmvvm.utils.ToastUtil;

import java.util.List;

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

    private ActivityListAdapter adapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_activity_list;
    }

    public ActivityListViewModel initViewModel() {
        return ViewModelProviders.of(this).get(ActivityListViewModel.class);
    }

    public void initData() {
        binding.setLifecycleOwner(this);
        adapter = new ActivityListAdapter(clickCallback);
        binding.rvActivity.setAdapter(adapter);
        viewModel.getActivityList();
    }

    public void initViewObservable() {
        viewModel.getData().observe(this, new Observer<List<CommitEntity>>() {
            @Override
            public void onChanged(List<CommitEntity> activityEntities) {
                adapter.setData(activityEntities);
                //当绑定的数据修改时更新视图
                binding.executePendingBindings();
            }
        });
    }

    private ActivityListAdapter.ClickCallback clickCallback = new ActivityListAdapter.ClickCallback() {
        @Override
        public void onPersonListClick(@NonNull CommitEntity data) {
            ToastUtil.toast(getApplication(), "名单" + data.getNode_id());
        }
    };
}
