package com.example.scotiabankhomeassignment.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scotiabankhomeassignment.api.ApiCallback
import com.example.scotiabankhomeassignment.api.model.User
import com.example.scotiabankhomeassignment.api.model.UserRepoList
import com.example.scotiabankhomeassignment.api.repository.GithubUserRepository
import kotlinx.coroutines.launch

class GithubUserViewModel(private val repository: GithubUserRepository) : ViewModel() {

    private val _userData = MutableLiveData<User?>(null)
    val userData: LiveData<User?> = _userData

    private val _userRepoList = MutableLiveData<List<UserRepoList>>()
    val userRepoList: LiveData<List<UserRepoList>> get() = _userRepoList

    private val _errorMessage = MutableLiveData<String>(null)
    val errorMessage: LiveData<String> = _errorMessage

    private val _isLoading = MutableLiveData(false)
    var isLoading: LiveData<Boolean> = _isLoading

    fun fetchGithubUserData(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true // Set isLoading to true when starting API call
            repository.fetchData(userId, object : ApiCallback<User> {
                override fun onSuccess(result: User) {
                    _userData.value = result
                    _errorMessage.value = null
                    _isLoading.value = false // Set isLoading to false on success
                }

                override fun onFailure(error: String) {
                    _errorMessage.value = error
                    _isLoading.value = false // Set isLoading to false on failure
                }
            })
        }
    }

    fun fetchGithubUserRepoList(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true // Set isLoading to true when starting API call
            repository.fetchUserRepoList(userId, object : ApiCallback<List<UserRepoList>> {

                override fun onSuccess(result: List<UserRepoList>) {
                    _userRepoList.value = result
                    _errorMessage.value = null
                    _isLoading.value = false
                }
                override fun onFailure(error: String) {
                    _errorMessage.value = error
                    _isLoading.value = false // Set isLoading to false on failure
                }
            })
        }
    }
}