package com.example.audioplayer.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.audioplayer.presentation.ui.AudioViewModel
import com.example.audioplayer.presentation.ui.components.controller.MediaPlayerControllerAction
import com.example.audioplayer.presentation.ui.components.SearchBarAction
import com.example.audioplayer.presentation.ui.screens.AudioDetailsScreen
import com.example.audioplayer.presentation.ui.screens.HomeScreen
import com.example.audioplayer.presentation.utils.getFakeAudioFile
import com.example.audioplayer.presentation.utils.toPresentation

@Composable
fun AudioNavHost(
    navController: NavHostController,
    audioViewModel: AudioViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AudioList.route,
        modifier = modifier
    ) {
        composable(route = AudioList.route) {

            ConfigureHomeScreen(navController = navController, viewModel = audioViewModel)
        }

        composable(
            route = AudioDetails.routeWithArgs,
            arguments = AudioDetails.arguments
        ) {
            ConfigureAudioDetailsScreen(audioViewModel)
        }
    }
}

@Composable
fun ConfigureHomeScreen(
    navController: NavHostController,
    viewModel: AudioViewModel
) {
    HomeScreen(
        filteredAudioList = viewModel.filteredList,
        onAudioClick = { viewModel.playAudio(it) },
        currentAudioFile = viewModel.currentPlayingAudio.value?.toPresentation(),
        isAudioPlaying = viewModel.isAudioPlaying,
        progress = viewModel.currentAudioProgress.value,
        searchText = viewModel.searchQuery.value,
        allAudioListIsNotEmpty = viewModel.allAudioListIsNotEmpty.value,
        onAudioInfoClick = {
            navController.navigateToAudioDetails(AudioDetails.route)
        },
        onSearchBarAction = { action ->
            when (action) {
                is SearchBarAction.OnSearchTextChanged -> {
                    viewModel.setSearchQuery(action.query)
                }
                SearchBarAction.OnClearClick -> viewModel.setSearchQuery("")
            }
        },
        onPlayerAction = { action -> configurePlayerAction(action, viewModel) }
    )
}

fun configurePlayerAction(action: MediaPlayerControllerAction, viewModel: AudioViewModel) {
    when (action) {
        is MediaPlayerControllerAction.OnStart -> viewModel.playAudio(action.audioFile)
        is MediaPlayerControllerAction.OnProgressChange -> viewModel.seekTo(action.value)
        MediaPlayerControllerAction.OnNext -> viewModel.skipToNext()
        MediaPlayerControllerAction.OnPrevious -> viewModel.skipToPrevious()
        MediaPlayerControllerAction.OnRestart -> viewModel.restart()
    }
}

@Composable
fun ConfigureAudioDetailsScreen(
    audioViewModel: AudioViewModel,
) {
    AudioDetailsScreen(
        playingAudioFile = audioViewModel.currentPlayingAudio.value?.toPresentation()
            ?: getFakeAudioFile(),
        progress = audioViewModel.currentAudioProgress.value,
        isAudioPlaying = audioViewModel.isAudioPlaying,
        onPlayerAction = { action ->
            configurePlayerAction(action, audioViewModel)
        },
    )
}

fun NavHostController.navigateSingleTopTo(route: String) = this.navigate(route) {
    // Pop up to the start destination of the graph to
    // avoid building up a large stack of destinations
    // on the back stack as users select items
    popUpTo(
        this@navigateSingleTopTo.graph.findStartDestination().id
    ) {
        saveState = true
    }
    // Avoid multiple copies of the same destination when
    // reselecting the same item
    launchSingleTop = true
    // Restore state when reselecting a previously selected item
    restoreState = true
}

private fun NavHostController.navigateToAudioDetails(text: String) {
    this.navigateSingleTopTo("${AudioDetails.route}/$text")
}