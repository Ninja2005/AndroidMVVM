package com.hqumath.androidmvvm.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.text.TextUtils;

import com.hqumath.androidmvvm.app.Constant;
import com.hqumath.androidmvvm.utils.SPUtil;

import java.io.File;

/**
 * 版本更新 服务
 */
public class UpdateService extends Service {
    private boolean isDown = false;
    private String apkName = "";

    //安卓系统下载类
    DownloadManager manager;
    //接收下载完的广播
    DownloadCompleteReceiver receiver;

    public UpdateService() {
    }

    //初始化下载器
    private void initDownManager() {
        if (!isDown) {
            isDown = true;
            manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            receiver = new DownloadCompleteReceiver();

            String apkUrl = SPUtil.getInstance().getString(Constant.APK_URL);
            String Name = SPUtil.getInstance().getString(Constant.APK_NAME);
            if (TextUtils.isEmpty(Name))
                apkName = getPackageName() + ".apk";
            else
                apkName = Name + ".apk";

            Uri parse = Uri.parse(apkUrl);
            DownloadManager.Request down = new DownloadManager.Request(parse);
            // 设置允许使用的网络类型，这里是移动网络和wifi都可以
            down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
            // 下载时，通知栏显示途中
            down.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            // 显示下载界面
            down.setVisibleInDownloadsUi(true);
            // 设置下载后文件存放的位置
            down.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, apkName);
            // 将下载请求放入队列
            manager.enqueue(down);
            //注册下载广播
            registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 调用下载
        initDownManager();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        // 注销下载广播
        if (receiver != null)
            unregisterReceiver(receiver);
        super.onDestroy();
    }

    // 接受下载完成后的intent
    class DownloadCompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //判断是否下载完成的广播
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                //获取下载的文件id
                long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                //自动安装apk
                Uri uriForDownloadedFile = manager.getUriForDownloadedFile(downId);
                installApkNew(uriForDownloadedFile);
                //停止服务并关闭广播
                UpdateService.this.stopSelf();
            }
        }

        //安装apk
        protected void installApkNew(Uri uri) {
            if (Build.VERSION.SDK_INT >= 24) {//判读版本是否在7.0以上
                Intent install = new Intent(Intent.ACTION_VIEW);
                install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
                install.setDataAndType(uri, "application/vnd.android.package-archive");
                getApplicationContext().startActivity(install);
            } else {
                //魅族手机URI不正确getExternalFilesDir
                if (!uri.toString().contains("file://")) {
                    File file = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS + File.separator + apkName);
                    if (file != null) {
                        String path = file.getAbsolutePath();
                        uri = Uri.parse("file://" + path);
                    }
                }
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        }
    }
}