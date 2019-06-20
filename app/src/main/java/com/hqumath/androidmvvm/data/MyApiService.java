package com.hqumath.androidmvvm.data;

import com.hqumath.androidmvvm.entity.ActivityEntity;
import com.hqumath.androidmvvm.entity.CommitEntity;
import com.hqumath.androidmvvm.entity.LoginResponse;
import com.hqumath.androidmvvm.http.BaseResultEntity;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by goldze on 2017/6/15.
 */

public interface MyApiService {

    //用户登录
    @FormUrlEncoded
    @POST("api/v2/login")
    Observable<Response<LoginResponse>> userLogin(@FieldMap Map<String, Object> map);

    //提交记录
    @GET("repos/ninja2005/AndroidMVVM/commits")
    Observable<Response<List<CommitEntity>>> getActivityList(@QueryMap Map<String, Object> map);

}
