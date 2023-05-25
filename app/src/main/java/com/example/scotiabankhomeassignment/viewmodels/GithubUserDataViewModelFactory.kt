package com.example.scotiabankhomeassignment.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.scotiabankhomeassignment.api.repository.GithubUserRepository

class GithubUserDataViewModelFactory(private val repository: GithubUserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GithubUserViewModel::class.java)) {
            return GithubUserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}