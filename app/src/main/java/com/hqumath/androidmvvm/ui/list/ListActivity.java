package com.hqumath.androidmvvm.ui.list;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.base.BaseActivity;
import com.hqumath.androidmvvm.databinding.ActivityListBinding;
import com.hqumath.androidmvvm.entity.CommitEntity;
import com.hqumath.androidmvvm.utils.ToastUtil;

import java.util.List;

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

    private ListAdapter adapter;

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
        adapter = new ListAdapter(clickCallback);
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

    private ListAdapter.ClickCallback clickCallback = new ListAdapter.ClickCallback() {
        @Override
        public void onPersonListClick(@NonNull CommitEntity data) {
            ToastUtil.toast(getApplication(), data.getSha());
        }
    };
}
