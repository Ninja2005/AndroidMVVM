package com.hqumath.androidmvvm.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hqumath.androidmvvm.R;

/**
 * ****************************************************************
 * 文件名称: AlterDialogTool
 * 作    者: Created by gyd
 * 创建时间: 2018/10/28 23:01
 * 文件描述: 通用弹窗界面
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */

public class DialogUtil extends Dialog {
    private Context mContext;
    private TextView tvTitle;//标题
    private TextView tvMessage;//内容
    private Button btnOneYes;//一个按钮时，确定按钮
    private View llTwo;//两个按钮布局
    private Button btnYes;//确定按钮
    private Button btnNo;//取消按钮
    private View line;//确定取消按钮中间竖线，有些样式没有
    private View ivRightClose;//右上角叉号
    private View mView;

    //ex.
        /*DialogUtil alterDialogUtils = new DialogUtil(mContext);
        alterDialogUtils.setTitle("提示");
        alterDialogUtils.setMessage("是否确认退出驾驶？");
        alterDialogUtils.setTwoConfirmBtn("确定", v -> {});
        alterDialogUtils.setTwoCancelBtn("取消", v -> {});
        alterDialogUtils.show();*/
    //仅显示确定按钮
    //alterDialogUtils.setOneConfirmBtn("确定", v -> {});

    /*
     * 默认主要操作弹窗
     */
    public DialogUtil(Context context) {
        this(context, R.style.dialog_common, R.layout.dialog_common);
    }

    public DialogUtil(Context context, int theme, int messageLayout) {
        super(context, theme);
        this.mContext = context;
        mView = LayoutInflater.from(getContext()).inflate(messageLayout, null);
        tvTitle = (TextView) mView.findViewById(R.id.title);
        tvMessage = (TextView) mView.findViewById(R.id.message);
        btnOneYes = (Button) mView.findViewById(R.id.oneYes);
        llTwo = mView.findViewById(R.id.llTwo);
        btnYes = (Button) mView.findViewById(R.id.yes);
        btnNo = (Button) mView.findViewById(R.id.no);
        setContentView(mView);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setMessage(int resId) {
        tvMessage.setText(resId);
    }

    public void setMessage(String message) {
        tvMessage.setText(message);
    }

    public void showRightClose() {
        if (ivRightClose != null)
            ivRightClose.setVisibility(View.VISIBLE);
    }

    public void setOneConfirmBtn(String text, View.OnClickListener listener) {
        setOneOrTwoBtn(true);
        if (text != null) {
            btnOneYes.setText(text);
        }
        btnOneYes.setOnClickListener(v -> {
            dismiss();
            listener.onClick(v);
        });
    }

    public void setTwoConfirmBtn(String text, View.OnClickListener listener) {
        setOneOrTwoBtn(false);
        if (text != null) {
            btnYes.setText(text);
        }
        btnYes.setOnClickListener(v -> {
            dismiss();
            listener.onClick(v);
        });
    }

    public void setTwoCancelBtn(String text, View.OnClickListener listener) {
        setOneOrTwoBtn(false);
        if (text != null) {
            btnNo.setText(text);
        }
        btnNo.setOnClickListener(v -> {
            dismiss();
            listener.onClick(v);
        });
    }

    /**
     * 设置按键类型
     *
     * @param one true 只有一个确认按键 ； false 显示 确认 和取消 按键
     */
    private void setOneOrTwoBtn(boolean one) {
        if (one) {
            if (btnOneYes != null)
                btnOneYes.setVisibility(View.VISIBLE);
            if (llTwo != null)
                llTwo.setVisibility(View.GONE);
        } else {
            if (btnOneYes != null)
                btnOneYes.setVisibility(View.GONE);
            if (llTwo != null)
                llTwo.setVisibility(View.VISIBLE);
        }
    }
}
