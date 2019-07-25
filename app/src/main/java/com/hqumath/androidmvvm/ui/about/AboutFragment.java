package com.hqumath.androidmvvm.ui.about;

import android.os.Bundle;
import androidx.lifecycle.ViewModelProviders;
import com.hqumath.androidmvvm.R;
import com.hqumath.androidmvvm.base.BaseViewModelFragment;
import com.hqumath.androidmvvm.databinding.FragmentAboutBinding;
import com.hqumath.androidmvvm.databinding.FragmentSettingsBinding;
import com.hqumath.androidmvvm.ui.settings.SettingsViewModel;

/**
 * ****************************************************************
 * 文件名称: AboutFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/7/25 11:36
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class AboutFragment extends BaseViewModelFragment<FragmentAboutBinding, AboutViewModel> {
    @Override
    public AboutViewModel getViewModel() {
        return ViewModelProviders.of(this).get(AboutViewModel.class);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.fragment_about;
    }

    @Override
    public void initView() {
        binding.setViewModel(viewModel);
    }

    @Override
    public void initData() {

    }
}
