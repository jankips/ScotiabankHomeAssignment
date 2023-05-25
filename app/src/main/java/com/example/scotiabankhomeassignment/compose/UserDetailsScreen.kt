package com.example.scotiabankhomeassignment.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberAsyncImagePainter
import com.example.scotiabankhomeassignment.api.model.User
import com.example.scotiabankhomeassignment.ui.theme.Dimens
import com.example.scotiabankhomeassignment.viewmodels.GithubUserViewModel

@Composable
fun GithubUserContent(githubUserViewModel: GithubUserViewModel, userId: String) {
    val userData by githubUserViewModel.userData.observeAsState()
    val errorMessage by githubUserViewModel.errorMessage.observeAsState()
    val isLoading by githubUserViewModel.isLoading.observeAsState()

    LaunchedEffect(key1 = userId) {
        githubUserViewModel.fetchGithubUserData(userId)
    }

    if (userData != null) {
        userData?.let {
            UserDetailView(it)
        }
    } else {
        errorMessage?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = Dimens.dimen_16, vertical = Dimens.dimen_4),
                color = Color.Gray
            )
        }
    }
        if (isLoading == true) {
            ProgressDialog() // Display loading bar when isLoading is true
        }
}

@Composable
fun UserDetailView(user: User) {
    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(Dimens.dimen_16))
        Image(
            painter = rememberAsyncImagePainter(user.avatar_url),
            contentDescription = null,
            modifier = Modifier.size(Dimens.dimen_128)
        )
        Text(
            text = "${user.name}",
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = Dimens.dimen_8),
            color = Color.Gray
        )
    }
}