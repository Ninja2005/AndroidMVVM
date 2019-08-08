package com.hqumath.androidmvvm.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * ****************************************************************
 * 文件名称: BaseFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/7/2 16:04
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public abstract class BaseFragment<V extends ViewDataBinding> extends RxFragment {
    protected V binding;
    protected Activity mContext;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, initContentView(savedInstanceState), container, false);
        binding.setLifecycleOwner(this);
        //viewModel
        initViewModel();
        //初始化ui
        initView();
        //页面数据初始化方法
        initData();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
        return binding.getRoot();
    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView(Bundle savedInstanceState);

    public void initViewModel() {
    }

    public abstract void initView();

    public abstract void initData();

    public void initViewObservable() {
    }

    public void showProgressDialog() {
        showProgressDialog("loading...");
    }

    public void showProgressDialog(String content) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.setMessage(content);
        mProgressDialog.show();
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
