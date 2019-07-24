package com.hqumath.androidmvvm.ui.starred;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.data.MyRepository;
import com.hqumath.androidmvvm.entity.ReposEntity;
import com.hqumath.androidmvvm.http.BaseApi;
import com.hqumath.androidmvvm.http.HandlerException;
import com.hqumath.androidmvvm.http.HttpOnNextListener;
import com.hqumath.androidmvvm.http.RetrofitClient;
import com.hqumath.androidmvvm.http.service.MyApiService;
import com.hqumath.androidmvvm.utils.ToastUtil;
import io.reactivex.Observable;
import retrofit2.Retrofit;

import java.util.List;

/**
 * ****************************************************************
 * 文件名称: StarredViewModel
 * 作    者: Created by gyd
 * 创建时间: 2019/7/24 15:41
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class StarredViewModel extends BaseViewModel<MyRepository> {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<List<ReposEntity>> list = new MutableLiveData<>();

    public StarredViewModel(@NonNull Application application) {
        super(application);
        model = MyRepository.getInstance();
    }

    public void getData() {
        RetrofitClient.getInstance().sendHttpRequestIO(new BaseApi(new HttpOnNextListener() {
            @Override
            public void onSubscribe() {
                isLoading.postValue(true);
            }

            @Override
            public void onNext(Object o) {
                list.postValue((List<ReposEntity>) o);
                isLoading.postValue(false);
            }

            @Override
            public void onError(HandlerException.ResponseThrowable e) {
                isLoading.postValue(false);
                appExecutors.mainThread().execute(() -> ToastUtil.toast(e.getMessage()));
            }

            @Override
            public void onComplete() {
            }
        }, getLifecycleProvider()) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                return retrofit.create(MyApiService.class).getStarred();
            }
        });
    }
}
