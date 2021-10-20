package com.hqumath.androidmvvm.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hqumath.androidmvvm.base.BaseFragment;
import com.hqumath.androidmvvm.databinding.FragmentAboutBinding;
import com.hqumath.androidmvvm.utils.CommonUtil;

/**
 * ****************************************************************
 * 文件名称: AboutFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/11/5 10:06
 * 文件描述:
 * 注意事项:
 * ****************************************************************
 */
public class AboutFragment extends BaseFragment {

    private FragmentAboutBinding binding;

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAboutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initListener() {
        //TODO
        /*binding.llSourcecode.setOnClickListener(v -> {
            startActivity(ReposDetailActivity.getStartIntent(mContext, "androidmvvm", "ninja2005"));
        });
        binding.llProfile.setOnClickListener(v -> {
            startActivity(ProfileDetailActivity.getStartIntent(mContext, "ninja2005"));
        });*/
    }

    @Override
    protected void initData() {
        binding.tvVersion.setText(CommonUtil.getVersion());
    }

}