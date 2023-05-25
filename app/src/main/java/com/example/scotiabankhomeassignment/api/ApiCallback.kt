package com.example.scotiabankhomeassignment.api

interface ApiCallback<T> {
    fun onSuccess(result: T)
    fun onFailure(error: String)
}