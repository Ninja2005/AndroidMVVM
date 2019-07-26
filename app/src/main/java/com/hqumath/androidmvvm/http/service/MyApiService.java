package com.hqumath.androidmvvm.http.service;

import com.hqumath.androidmvvm.entity.ReposEntity;
import com.hqumath.androidmvvm.entity.UserInfoEntity;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

/**
 * ****************************************************************
 * 文件名称: MyApiService
 * 作    者: Created by gyd
 * 创建时间: 2019/7/17 11:49
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public interface MyApiService {

    //获取用户信息
//    @GET("users/ninja2005")
//    Observable<Response<UserInfoEntity>> getUserInfo();

    @GET("users/{userName}")
    Observable<Response<UserInfoEntity>> getUserInfo(@Path("userName") String userName);

    //获取用户仓库
    @GET("users/ninja2005/repos")
    Observable<Response<List<ReposEntity>>> getMyRepos();

    //获取星标仓库
    @GET("users/ninja2005/starred")
    Observable<Response<List<ReposEntity>>> getStarred();

    //获取跟随
    @GET("users/ninja2005/following")
    Observable<Response<List<UserInfoEntity>>> getFollowing();

    //获取跟随者
    @GET("users/ninja2005/followers")
    Observable<Response<List<UserInfoEntity>>> getFollowers();
}
