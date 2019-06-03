package com.hqumath.androidmvvm.data;

import com.hqumath.androidmvvm.entity.DemoEntity;
import com.hqumath.androidmvvm.entity.LoginResponse;
import com.hqumath.androidmvvm.http.BaseResultEntity;
import io.reactivex.Observable;
import retrofit2.http.*;

import java.util.Map;

/**
 * Created by goldze on 2017/6/15.
 */

public interface DemoApiService {

    //用户登录
    @FormUrlEncoded
    @POST("api/v2/login")
    Observable<BaseResultEntity> userLogin(@FieldMap Map<String, Object> maps);


    @GET("action/apiv2/banner?catalog=1")
    Observable<BaseResultEntity<DemoEntity>> demoGet();

    @FormUrlEncoded
    @POST("action/apiv2/banner")
    Observable<BaseResultEntity<DemoEntity>> demoPost(@Field("catalog") String catalog);
}
