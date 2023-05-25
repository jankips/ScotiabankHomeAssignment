package com.example.scotiabankhomeassignment.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.scotiabankhomeassignment.api.model.UserRepoList
import com.example.scotiabankhomeassignment.ui.theme.Dimens
import com.example.scotiabankhomeassignment.viewmodels.GithubUserViewModel

@Composable
fun getUserRepoList(githubUserViewModel: GithubUserViewModel, userId: String) {
    val userRepoList by githubUserViewModel.userRepoList.observeAsState(emptyList())
    val isLoading by githubUserViewModel.isLoading.observeAsState()

    LaunchedEffect(key1 = userId) {
        githubUserViewModel.fetchGithubUserRepoList(userId)
    }

    if (isLoading == true) {
        ProgressDialog() // Display loading bar when isLoading is true
    }
        // Render the user details using Jetpack Compose
        LazyColumn {
            items(userRepoList) { userRepo ->
                UserRepoItem(userRepo)
            }
        }
}

@Composable
fun UserRepoItem(user: UserRepoList) {
    Card(
        shape = RoundedCornerShape(Dimens.dimen_2),
        elevation = Dimens.dimen_4,
        modifier = Modifier
            .padding(Dimens.dimen_16)
            .fillMaxWidth()
    ) {
        Surface(
            modifier = Modifier.padding(Dimens.dimen_4),
            color = MaterialTheme.colors.surface,
        ) {
            Column(modifier = Modifier.padding(Dimens.dimen_8)) {
                user.name?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                }
                user.description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(top = Dimens.dimen_6),
                        color = Color.Gray
                    )
                }
            }
        }
    }
}