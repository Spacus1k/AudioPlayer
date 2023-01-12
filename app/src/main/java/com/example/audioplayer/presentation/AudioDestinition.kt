package com.example.audioplayer.presentation

import androidx.navigation.navArgument

interface AudioDestination{
    val route: String
}

object AudioList: AudioDestination {
    override val route: String = "audio_list"
}

object AudioDetails: AudioDestination {
    override val route: String = "audio_details"
    private const val audioFileArg = "audio_file"
    val arguments = listOf(
        navArgument(audioFileArg){}
    )
    val routeWithArgs = "$route/{$audioFileArg}"
}