package com.hqumath.androidmvvm.ui.fileupdown;

import androidx.lifecycle.MutableLiveData;

import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.net.HttpListener;
import com.hqumath.androidmvvm.net.download.DownloadListener;
import com.hqumath.androidmvvm.repository.MyModel;
import com.hqumath.androidmvvm.utils.FileUtil;

import java.io.File;

public class FileUpDownViewModel extends BaseViewModel {

    //下载
    public MutableLiveData<Boolean> isDownloading = new MutableLiveData<>();
    public MutableLiveData<Long> downloadProgress = new MutableLiveData<>();
    public long downloadMax;//总量
    public MutableLiveData<String> downloadResultCode = new MutableLiveData<>();//0成功；other失败
    public String downloadResultMsg;
    public File downloadResultData;

    //上传
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<String> uploadResultCode = new MutableLiveData<>();//0成功；other失败
    public String uploadResultMsg;
    public Object uploadResultData;

    public FileUpDownViewModel() {
        mModel = new MyModel();
    }

    public void download(String url) {
        isDownloading.setValue(true);
        File file = FileUtil.getFileFromUrl(url);
        ((MyModel) mModel).download(url, file, new DownloadListener() {
            @Override
            public void onSuccess(Object object) {
                isDownloading.setValue(false);
                downloadResultData = (File) object;
                downloadResultCode.setValue("0");
            }

            @Override
            public void onError(String errorMsg, String code) {
                isDownloading.setValue(false);
                downloadResultMsg = errorMsg;
                downloadResultCode.setValue(code);
            }

            @Override
            public void update(long read, long count) {
                downloadMax = count;
                downloadProgress.postValue(read);
            }
        });
    }

    public void upload(String key, File file) {
        isLoading.setValue(true);
        ((MyModel) mModel).upload(key, file, new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                isLoading.setValue(false);
                uploadResultData = object;
                uploadResultCode.setValue("0");
            }

            @Override
            public void onError(String errorMsg, String code) {
                isLoading.setValue(false);
                uploadResultMsg = errorMsg;
                uploadResultCode.setValue(code);
            }
        });
    }
}


