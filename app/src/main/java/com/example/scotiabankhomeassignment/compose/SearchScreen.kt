package com.example.scotiabankhomeassignment.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import com.example.scotiabankhomeassignment.R
import com.example.scotiabankhomeassignment.ui.theme.ColorAccent
import com.example.scotiabankhomeassignment.ui.theme.Dimens

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.dimen_16, vertical = Dimens.dimen_4)
    ) {
        if (isFocused || searchQuery.isNotEmpty()) {
            val label = stringResource(id = R.string.lbl_enter_github_uid)
            Text(
                text = label,
                style = TextStyle(color = ColorAccent, fontSize = Dimens.text13, fontWeight = FontWeight.Normal),
                modifier = Modifier.padding(bottom = Dimens.dimen_4)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(Dimens.dimen_0)
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    },
                value = searchQuery,
                onValueChange = { query ->
                    searchQuery = query
                },
                textStyle = TextStyle(color = Color.Black),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = ColorAccent
                ),
                placeholder = { Text(text = stringResource(id = R.string.lbl_enter_github_uid))}
            )

            Button(
                onClick = { keyboardController?.hide()
                    onSearch(searchQuery)
                          },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                modifier = Modifier.padding(start = Dimens.dimen_8)
            ) {
                val buttonText = stringResource(id = R.string.search).toUpperCase(Locale.current)
                Text(text = buttonText)
            }
        }
    }

}


