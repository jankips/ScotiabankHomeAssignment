package com.example.scotiabankhomeassignment.api.model

data class User(
    val name: String,
    val avatar_url: String,
    val location: String,
)

data class UserRepoList(
    val name: String?,
    val description: String?,
    val updated_at: String,
)