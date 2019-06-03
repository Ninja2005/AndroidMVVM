package com.hqumath.androidmvvm.data;

import com.hqumath.androidmvvm.entity.DemoEntity;
import com.hqumath.androidmvvm.http.BaseResultEntity;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by goldze on 2017/6/15.
 */

public interface DemoApiService {
    @GET("action/apiv2/banner?catalog=1")
    Observable<BaseResultEntity<DemoEntity>> demoGet();

    @FormUrlEncoded
    @POST("action/apiv2/banner")
    Observable<BaseResultEntity<DemoEntity>> demoPost(@Field("catalog") String catalog);
}
