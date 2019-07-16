package com.hqumath.androidmvvm.base;

import androidx.databinding.ViewDataBinding;

/**
 * ****************************************************************
 * 文件名称: BaseViewModelFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/7/2 17:09
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public abstract class BaseViewModelFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseFragment<V> {
    protected VM viewModel;

    /**
     * 初始化ViewModel
     */
    public void initViewModel() {
        viewModel = getViewModel();
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(viewModel);
        //注入RxLifecycle生命周期
        viewModel.injectLifecycleProvider(this);
    }

    public abstract VM getViewModel();
}
