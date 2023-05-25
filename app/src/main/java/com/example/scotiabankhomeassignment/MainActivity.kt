package com.example.scotiabankhomeassignment


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.scotiabankhomeassignment.api.repository.GithubUserRepository
import com.example.scotiabankhomeassignment.compose.GithubUserContent
import com.example.scotiabankhomeassignment.compose.SearchBar
import com.example.scotiabankhomeassignment.compose.getUserRepoList
import com.example.scotiabankhomeassignment.ui.theme.ScotiabankHomeAssignmentTheme
import com.example.scotiabankhomeassignment.viewmodels.GithubUserDataViewModelFactory
import com.example.scotiabankhomeassignment.viewmodels.GithubUserViewModel

class MainActivity : ComponentActivity() {
    private val githubUserViewModel: GithubUserViewModel by viewModels {
        GithubUserDataViewModelFactory(GithubUserRepository())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                ScotiabankHomeAssignmentTheme(darkTheme = false) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            TopAppBar(
                                title = {
                                    Text(text = stringResource(id = R.string.lbl_toolbar))
                                }
                            )
                            GithubUserScreen(githubUserViewModel = githubUserViewModel)
                        }
                    }
                }
            }
    }
}

@Composable
fun GithubUserScreen(githubUserViewModel: GithubUserViewModel) {
    var searchResults by remember { mutableStateOf("") }
    var isSearchButtonClick by remember { mutableStateOf(false) }

    Column {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            onSearch = { query ->
                searchResults = query
                isSearchButtonClick = true
            }
        )
        if (isSearchButtonClick && searchResults.isNotEmpty()) {
            GithubUserContent(githubUserViewModel, searchResults)
            getUserRepoList(githubUserViewModel = githubUserViewModel, userId = searchResults)
        }
    }
}

