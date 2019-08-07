package com.hqumath.androidmvvm.http.service;

import com.hqumath.androidmvvm.entity.CommitEntity;
import com.hqumath.androidmvvm.entity.ReposEntity;
import com.hqumath.androidmvvm.entity.UserInfoEntity;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    @GET("users/{userName}")
    Observable<Response<UserInfoEntity>> getUserInfo(@Path("userName") String userName);

    //获取用户仓库
    @GET("users/ninja2005/repos")
    Call<List<ReposEntity>> getMyRepos(@Query("per_page") int per_page, @Query("page") long page);

    //获取星标仓库
    @GET("users/ninja2005/starred")
    Call<List<ReposEntity>> getStarred(@Query("per_page") int per_page, @Query("page") long page);

    //获取我追随的
    @GET("users/{userName}/following")
    Observable<Response<List<UserInfoEntity>>> getFollowing(@Path("userName") String userName,
                                                            @Query("per_page") int per_page, @Query("page") long page);

    //获取被追随
    @GET("users/{userName}/followers")
    Observable<Response<List<UserInfoEntity>>> getFollowers(@Path("userName") String userName,
                                                            @Query("per_page") int per_page,
                                                            @Query("page") long page);

    //获取仓库信息
    @GET("repos/{userName}/{reposName}")
    Observable<Response<ReposEntity>> getReposInfo(@Path("userName") String userName,
                                                   @Path("reposName") String reposName);

    //获取仓库提交记录 分页
    @GET("repos/{userName}/{reposName}/commits?sha=master")
    Call<List<CommitEntity>> getCommits(@Path("userName") String userName,
                                        @Path("reposName") String reposName,
                                        @Query("per_page") int per_page, @Query("page") long page);
}
