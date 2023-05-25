package com.example.scotiabankhomeassignment.api

import com.example.scotiabankhomeassignment.api.model.User
import com.example.scotiabankhomeassignment.api.model.UserRepoList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("users/{userId}")
    suspend fun fetchUserData(@Path("userId") userId: String): Response<User>

    @GET("users/{userId}/repos")
    suspend fun getGithubUserRepoList(@Path("userId") userId: String): Response<List<UserRepoList>>

}