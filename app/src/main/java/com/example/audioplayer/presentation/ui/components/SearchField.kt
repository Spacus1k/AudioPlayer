package com.example.audioplayer.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.audioplayer.R

@Composable
fun TopBarWithSearch(
    searchText: String,
    onSearchTextChanged: (String) -> Unit = {},
    onClearClick: () -> Unit,
    modifier: Modifier
) {
    var isSearchExpanded by remember { mutableStateOf(false) }
    if (isSearchExpanded) {
        ExpandedSearchBar(
            searchText = searchText,
            onClearClick = { onClearClick() },
            onBackPressed = { isSearchExpanded = false },
            onSearchTextChanged = onSearchTextChanged,
            modifier = modifier,
        )
    } else {
        CollapsedSearchBar(onIconClicked = { isSearchExpanded = true })
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ExpandedSearchBar(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onBackPressed: () -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier
) {
    var showClearButton by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(8.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.Black.copy(alpha = 0.1f))
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            modifier
                .padding(16.dp)
                .clickable {
                    onBackPressed()
                    onClearClick()
                }
        )

        TextField(
            value = searchText,
            onValueChange = onSearchTextChanged,
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    showClearButton = focusState.isFocused
                }
                .focusRequester(focusRequester),
            colors = TextFieldDefaults.textFieldColors(
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                cursorColor = Color.Black
            ),
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            placeholder = { Text(stringResource(id = R.string.search)) },
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                keyboardController?.hide()
            }),
            trailingIcon = {
                if (searchText.isNotEmpty()) {
                    AnimatedVisibility(
                        visible = showClearButton,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        IconButton(onClick = { onClearClick() }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = null
                            )
                        }
                    }
                }
            },
        )
    }
}

@Composable
fun CollapsedSearchBar(onIconClicked: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Tracks",
            style = MaterialTheme.typography.h6,
            modifier = modifier.padding(start = 16.dp)
        )
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            modifier
                .size(35.dp)
                .padding(end = 4.dp)
                .clickable { onIconClicked() }
        )
    }
}

@Composable
@Preview
fun PreviewSearchField() {
    ExpandedSearchBar(
        searchText = "",
        onSearchTextChanged = {},
        modifier = Modifier,
        onClearClick = {},
        onBackPressed = {},
    )
}

@Composable
@Preview
fun PreviewCollapsedSearchBar() {
    CollapsedSearchBar(onIconClicked = {})
}