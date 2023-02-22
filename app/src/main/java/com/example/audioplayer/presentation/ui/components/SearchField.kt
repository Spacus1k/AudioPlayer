package com.example.audioplayer.presentation.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.audioplayer.R
import com.example.audioplayer.presentation.ui.base.clearFocusOnKeyboardDismiss

@Composable
fun TopBarWithSearch(
    searchText: String,
    focusManager: FocusManager,
    onSearchBarAction: (SearchBarAction) -> Unit,
) {
    val modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)

    var isSearchExpanded by remember { mutableStateOf(false) }
    Surface(
        color = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.secondaryVariant,
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
    ) {
        Column(modifier= Modifier.fillMaxSize() ) {
            Spacer(modifier = Modifier
                .height(20.dp)
                .fillMaxWidth())

            AnimatedVisibility(
                visible = !isSearchExpanded,
                enter = slideIn { IntOffset(-700, 0) },
                exit = fadeOut()
            ) {

                CollapsedSearchBar(onIconClicked = { isSearchExpanded = true }, modifier = modifier)
            }
            AnimatedVisibility(
                visible = isSearchExpanded,
                enter = slideIn(initialOffset = { IntOffset(500, 0) }),
                exit = slideOut(animationSpec = spring(
                    stiffness = Spring.StiffnessMedium,
                    visibilityThreshold = IntOffset.VisibilityThreshold
                ),
                    targetOffset = { IntOffset(900, 0) })
            ) {
                ExpandedSearchBar(
                    searchText = searchText,
                    onBackPressed = { isSearchExpanded = false },
                    onSearchBarAction = onSearchBarAction,
                    focusManager = focusManager,
                    modifier = modifier
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ExpandedSearchBar(
    searchText: String,
    onBackPressed: () -> Unit,
    onSearchBarAction: (SearchBarAction) -> Unit,
    modifier: Modifier = Modifier,
    focusManager: FocusManager = LocalFocusManager.current,
) {
    var showClearButton by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = FocusRequester()

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(Color.Black.copy(alpha = 0.1f))
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            Modifier
                .padding(8.dp)
                .clickable {
                    onBackPressed()
                    onSearchBarAction(SearchBarAction.OnClearClick)
                }
        )

        val customTextSelectionColors = TextSelectionColors(
            handleColor = MaterialTheme.colors.primaryVariant,
            backgroundColor = MaterialTheme.colors.primaryVariant.copy(alpha = 0.4f)
        )

        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
            TextField(
                value = searchText,
                onValueChange = { onSearchBarAction(SearchBarAction.OnSearchTextChanged(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clearFocusOnKeyboardDismiss()
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        showClearButton = focusState.isFocused
                    },
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    backgroundColor = Color.Transparent,
                    cursorColor = MaterialTheme.colors.secondaryVariant
                ),
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                placeholder = { Text(stringResource(id = R.string.search)) },
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }),

                trailingIcon = {
                    if (searchText.isNotEmpty()) {
                        AnimatedVisibility(
                            visible = showClearButton,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            IconButton(onClick = { onSearchBarAction(SearchBarAction.OnClearClick) }) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun CollapsedSearchBar(onIconClicked: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth().padding(top = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Text(
            text = "Tracks",
            style = MaterialTheme.typography.h6
        )
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            Modifier
                .size(35.dp)
                .padding(end = 4.dp)
                .clickable { onIconClicked() }
        )
    }
}

sealed class SearchBarAction {
    class OnSearchTextChanged(val query: String) : SearchBarAction()
    object OnClearClick : SearchBarAction()
}

@Composable
@Preview
fun PreviewSearchField() {
    ExpandedSearchBar(
        searchText = "",
        modifier = Modifier,
        onSearchBarAction = {},
        onBackPressed = {}
    )
}

@Composable
@Preview
fun PreviewCollapsedSearchBar() {
    CollapsedSearchBar(onIconClicked = {})
}