package com.hqumath.androidmvvm.ui.repos;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.adapters.MyFragmentPagerAdapter;
import com.hqumath.androidmvvm.base.BaseFragment;
import com.hqumath.androidmvvm.databinding.FragmentReposBinding;
import com.hqumath.androidmvvm.ui.follow.FollowersFragment;
import com.hqumath.androidmvvm.ui.follow.FollowingFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * ****************************************************************
 * 文件名称: ReposFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/9/4 14:42
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class ReposFragment extends BaseFragment<FragmentReposBinding> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.fragment_repos;
    }

    @Override
    public void initView() {


    }

    @Override
    public void initData() {
        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(new MyReposFragment());
        fragmentList.add(new StarredFragment());

        List<String> titles = new ArrayList<>();
        titles.add("MyRepos");
        titles.add("Starred");

        MyFragmentPagerAdapter pagerAdapter =
                new MyFragmentPagerAdapter(getChildFragmentManager());
        pagerAdapter.setData(fragmentList, titles);
        binding.viewpager.setAdapter(pagerAdapter);
        binding.viewpager.setOffscreenPageLimit(fragmentList.size());
        binding.tablayout.setupWithViewPager(binding.viewpager);
    }
}