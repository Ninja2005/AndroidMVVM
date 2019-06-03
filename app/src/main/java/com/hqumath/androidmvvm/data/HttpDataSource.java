package com.hqumath.androidmvvm.data;

import com.hqumath.androidmvvm.entity.DemoEntity;
import com.hqumath.androidmvvm.http.BaseResponse;
import io.reactivex.Observable;

/**
 * Created by goldze on 2019/3/26.
 */
public interface HttpDataSource {
    //模拟登录
    Observable<Object> login();

    //模拟上拉加载
    Observable<DemoEntity> loadMore();

    Observable<BaseResponse<DemoEntity>> demoGet();

    Observable<BaseResponse<DemoEntity>> demoPost(String catalog);


}
