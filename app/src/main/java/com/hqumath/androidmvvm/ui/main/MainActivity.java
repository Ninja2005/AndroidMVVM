package com.hqumath.androidmvvm.ui.main;

import android.os.Bundle;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.base.BaseActivity;
import com.hqumath.androidmvvm.databinding.ActivityMainBinding;
import com.hqumath.androidmvvm.utils.ToastUtil;

/**
 * ****************************************************************
 * 文件名称: MainActivity
 * 作    者: Created by gyd
 * 创建时间: 2019/7/17 11:16
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class MainActivity extends BaseActivity<ActivityMainBinding> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        binding.navigationView.setNavigationItemSelectedListener(menuItem -> {
            ToastUtil.toast("点击" + menuItem.getItemId());
            return true;
        });
    }

    @Override
    public void initData() {

    }
}
