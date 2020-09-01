package com.hqumath.androidmvvm.ui.main;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.adapters.MyFragmentPagerAdapter;
import com.hqumath.androidmvvm.base.BaseActivity;
import com.hqumath.androidmvvm.base.BaseFragment;
import com.hqumath.androidmvvm.databinding.ActivityMainBinding;
import com.hqumath.androidmvvm.ui.about.AboutFragment;
import com.hqumath.androidmvvm.ui.follow.FollowFragment;
import com.hqumath.androidmvvm.ui.repos.ReposFragment;
import com.hqumath.androidmvvm.ui.settings.SettingsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * ****************************************************************
 * 文件名称: MainActivity
 * 作    者: Created by gyd
 * 创建时间: 2019/7/17 11:16
 * 文件描述: 主界面风格1
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
    }

    @Override
    public void initData() {
        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(new ReposFragment());
        fragmentList.add(new FollowFragment());
        fragmentList.add(new SettingsFragment());
        fragmentList.add(new AboutFragment());

        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pagerAdapter.setData(fragmentList, null);
        binding.viewpager.setAdapter(pagerAdapter);
        //binding.viewpager.setOffscreenPageLimit(fragmentList.size());
        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                binding.navigation.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        binding.navigation.setOnNavigationItemSelectedListener(item -> {
            binding.viewpager.setCurrentItem(item.getOrder());
            return true;
        });
    }

}
