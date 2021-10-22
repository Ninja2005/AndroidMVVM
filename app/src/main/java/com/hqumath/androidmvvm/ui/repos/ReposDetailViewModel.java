package com.hqumath.androidmvvm.ui.repos;

import androidx.lifecycle.MutableLiveData;

import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.bean.CommitEntity;
import com.hqumath.androidmvvm.bean.ReposEntity;
import com.hqumath.androidmvvm.net.HttpListener;
import com.hqumath.androidmvvm.repository.MyModel;
import com.hqumath.androidmvvm.utils.CommonUtil;
import com.hqumath.androidmvvm.utils.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReposDetailViewModel extends BaseViewModel {

    private final static int pageSize = 10;//分页
    public String userName;
    public String reposName;
    //仓库详情
    public MutableLiveData<String> reposResultCode = new MutableLiveData<>();//0成功；other失败
    public String avatar_url;
    public MutableLiveData<String> description = new MutableLiveData<>();
    public MutableLiveData<String> fullName = new MutableLiveData<>();
    public MutableLiveData<String> createdAt = new MutableLiveData<>();
    public MutableLiveData<String> languageSize = new MutableLiveData<>();
    //提交记录列表
    private long commitPageIndex;//索引
    public MutableLiveData<String> commitResultCode = new MutableLiveData<>();//0成功；other失败
    public String commitResultMsg;
    public boolean commitRefresh;//true 下拉刷新；false 上拉加载
    public boolean commitNewEmpty;//true 增量为空；false 增量不为空
    public List<CommitEntity> commitData = new ArrayList<>();//列表数据

    public ReposDetailViewModel() {
        mModel = new MyModel();
    }

    public void getReposInfo() {
        ((MyModel) mModel).getReposInfo(userName, reposName, new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                ReposEntity data = (ReposEntity) object;
                avatar_url = data.getOwner().getAvatar_url();
                description.setValue(data.getDescription());
                fullName.setValue(data.getFull_name());
                //时间格式化
                String date = data.getCreated_at();//2011-12-29T04:45:11Z
                date = date.replace("Z", " UTC");//UTC是世界标准时间
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date date1 = format1.parse(date);
                    String date2 = format2.format(date1);
                    createdAt.setValue(date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String info = String.format(Locale.getDefault(), "Language %s, size %s",
                        data.getLanguage(), StringUtil.getSizeString(data.getSize() * 1024));
                languageSize.setValue(info);
                reposResultCode.setValue("0");
            }

            @Override
            public void onError(String errorMsg, String code) {
                CommonUtil.toast(errorMsg);
            }
        });
    }

    /**
     * 获取列表
     *
     * @param isRefresh true 下拉刷新；false 上拉加载
     */
    public void getCommits(boolean isRefresh) {
        if (isRefresh) {
            commitPageIndex = 1;
        }
        ((MyModel) mModel).getCommits(userName, reposName, pageSize, commitPageIndex, new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                List<CommitEntity> list = (List<CommitEntity>) object;
                commitPageIndex++;//偏移量+1
                if (isRefresh) //下拉覆盖，上拉增量
                    commitData.clear();
                if (!list.isEmpty())
                    commitData.addAll(list);
                commitRefresh = isRefresh;
                commitNewEmpty = list.isEmpty();
                commitResultCode.setValue("0");
            }

            @Override
            public void onError(String errorMsg, String code) {
                commitResultMsg = errorMsg;
                commitRefresh = isRefresh;
                commitResultCode.setValue(code);
            }
        });
    }
}
