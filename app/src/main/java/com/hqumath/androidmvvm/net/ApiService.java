package com.hqumath.androidmvvm.net;


import com.hqumath.androidmvvm.bean.ReposEntity;
import com.hqumath.androidmvvm.bean.UserInfoEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * ****************************************************************
 * 文件名称: MainService
 * 作    者: Created by gyd
 * 创建时间: 2019/1/22 17:11
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public interface ApiService {

    //获取用户信息
    @GET("users/{userName}")
    Observable<UserInfoEntity> getUserInfo(@Path("userName") String userName);

    //获取用户仓库
    @GET("users/{userName}/repos")
    Observable<List<ReposEntity>> getMyRepos(@Path("userName") String userName, @Query("per_page") int per_page, @Query("page") long page);

    //获取星标仓库
    @GET("users/{userName}/starred")
    Observable<List<ReposEntity>> getStarred(@Path("userName") String userName, @Query("per_page") int per_page, @Query("page") long page);

    //获取被追随
    @GET("users/{userName}/followers")
    Observable<List<UserInfoEntity>> getFollowers(@Path("userName") String userName, @Query("per_page") int per_page, @Query("page") long page);


}
