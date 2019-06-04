package com.hqumath.androidmvvm.data;

import com.hqumath.androidmvvm.entity.ActivityEntity;
import com.hqumath.androidmvvm.http.BaseResultEntity;
import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;
import java.util.Map;

/**
 * Created by goldze on 2017/6/15.
 */

public interface MyApiService {

    //用户登录
    @FormUrlEncoded
    @POST("api/v2/login")
    Observable<BaseResultEntity> userLogin(@FieldMap Map<String, Object> maps);

    //获取活动列表
    @FormUrlEncoded
    @POST("api/v2/activity/getActivities?token={token}")
    Observable<BaseResultEntity<List<ActivityEntity>>> getActivityList(@Path("token") String token,
                                                                       @FieldMap Map<String, Object> maps);

}
