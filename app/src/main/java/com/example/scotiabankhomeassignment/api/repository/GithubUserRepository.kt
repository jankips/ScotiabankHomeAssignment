package com.example.scotiabankhomeassignment.api.repository

import android.content.res.Resources
import com.example.scotiabankhomeassignment.R
import com.example.scotiabankhomeassignment.api.ApiCallback
import com.example.scotiabankhomeassignment.api.ApiService
import com.example.scotiabankhomeassignment.api.model.User
import com.example.scotiabankhomeassignment.api.model.UserRepoList

open class GithubUserRepository {

    suspend fun fetchData(userId: String, callback: ApiCallback<User>) {
        try {
            val response = ApiService.userService.fetchUserData(userId)
            if (response.isSuccessful) {
                val dataResponse = response.body()
                if (dataResponse != null) {
                    callback.onSuccess(dataResponse)
                } else {
                    callback.onFailure(Resources.getSystem().getString(R.string.err_empty_response))
                }
            } else {
                callback.onFailure(Resources.getSystem().getString(R.string.err_api_failed_with_code, response.code()))
            }
        } catch (e: Exception) {
            callback.onFailure(e.message ?: Resources.getSystem().getString(R.string.err_api_call_failed))
        }
    }

    suspend fun fetchUserRepoList(userId: String, callback: ApiCallback<List<UserRepoList>>) {
        try {
            val response = ApiService.userService.getGithubUserRepoList(userId)
            if (response.isSuccessful) {
                val repoListResponse = response.body()
                if (repoListResponse != null) {
                    callback.onSuccess(repoListResponse)
                } else {
                    callback.onFailure(Resources.getSystem().getString(R.string.err_empty_response))
                }
            } else {
                callback.onFailure(Resources.getSystem().getString(R.string.err_api_failed_with_code, response.code()))
            }
        } catch (e: Exception) {
            callback.onFailure(e.message ?: Resources.getSystem().getString(R.string.err_api_call_failed))
        }
    }
}