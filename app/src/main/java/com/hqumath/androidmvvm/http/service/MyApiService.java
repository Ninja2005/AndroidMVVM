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
    Observable<Response<List<ReposEntity>>> getMyRepos();

    //获取星标仓库
    @GET("users/ninja2005/starred")
    Observable<Response<List<ReposEntity>>> getStarred();

    //获取跟随
    @GET("users/ninja2005/following")
    Observable<Response<List<UserInfoEntity>>> getFollowing();

    //获取跟随
    @GET("users/ninja2005/following")
    Observable<Response<List<UserInfoEntity>>> getFollowing1(@Query("per_page") int per_page, @Query("page") long page);

    //获取被跟随
    @GET("users/{userName}/following")
    Call<List<UserInfoEntity>> getFollowing2(@Path("userName") String userName, @Query("per_page") int per_page,
                                            @Query("page") long page);

    //获取被跟随
    @GET("users/{userName}/followers")
    Call<List<UserInfoEntity>> getFollowers(@Path("userName") String userName, @Query("per_page") int per_page,
                                           @Query("page") long page);

    //获取被跟随
    @GET("users/{userName}/followers")
    Observable<Response<List<UserInfoEntity>>> getFollowers1(@Path("userName") String userName, @Query("per_page") int per_page,
                                            @Query("page") long page);

    //获取仓库信息
    @GET("repos/{userName}/{reposName}")
    Observable<Response<ReposEntity>> getReposInfo(@Path("userName") String userName,
                                                   @Path("reposName") String reposName);

    //获取仓库提交记录
    @GET("repos/{userName}/{reposName}/commits?sha=master")
    Observable<Response<List<CommitEntity>>> getCommits(@Path("userName") String userName,
                                                        @Path("reposName") String reposName);

    /*@GET("repos/{userName}/{reposName}/commits?sha=master")
    Observable<Response<List<CommitEntity>>> getCommits1(@Path("userName") String userName,
                                                        @Path("reposName") String reposName,
                                                        @Query("per_page") int per_page, @Query("page") long page);*/

    //获取仓库提交记录 分页
    @GET("repos/{userName}/{reposName}/commits?sha=master")
    Call<List<CommitEntity>> getCommits2(@Path("userName") String userName,
                                         @Path("reposName") String reposName,
                                         @Query("per_page") int per_page, @Query("page") long page);
}
