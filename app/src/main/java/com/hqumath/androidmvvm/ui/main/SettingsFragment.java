package com.hqumath.androidmvvm.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hqumath.androidmvvm.base.BaseFragment;
import com.hqumath.androidmvvm.databinding.FragmentSettingsBinding;

public class SettingsFragment extends BaseFragment {

    private FragmentSettingsBinding binding;

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initListener() {
        /*binding.fileUpDown.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, FileUpDownActivity.class);
            startActivity(intent);
        });
        binding.vCheckUpdate.setOnClickListener(v -> {
            //检查升级
            *//*boolean needUpdate = new Random().nextBoolean();
            if (!needUpdate) {
                toast("已是最新版本");
                return;
            }*//*
            SPUtil.getInstance().put(Constant.APK_URL, FileUpDownActivity.url);
            SPUtil.getInstance().put(Constant.APK_NAME, "AndroidMVP V2.0");
            DialogUtil alterDialogUtils = new DialogUtil(mContext);
            alterDialogUtils.setTitle("新版本V2.0");
            alterDialogUtils.setMessage("适配 Android 11");
            alterDialogUtils.setTwoConfirmBtn("立即更新", v1 -> {
                toast("已在后台下载");
                mContext.startService(new Intent(mContext, UpdateService.class));
            });
            alterDialogUtils.setTwoCancelBtn("下次提醒", v2 -> {
            });
            alterDialogUtils.setCancelable(false);
            alterDialogUtils.show();
        });
        binding.vLogout.setOnClickListener(v -> {
            SPUtil.getInstance().clear();
            startActivity(new Intent(mContext, LoginActivity.class));
            mContext.finish();
        });*/
    }

    @Override
    protected void initData() {
    }

}
