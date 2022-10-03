package com.example.audioplayer.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.audioplayer.presentation.theme.AudioPlayerTheme
import com.example.audioplayer.presentation.ui.screens.HomeScreen
import com.example.audioplayer.presentation.ui.AudioViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AudioPlayerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomeScreen(
                        this,
                        viewModel = viewModel(modelClass = AudioViewModel::class.java)
                    )
                }
            }
        }
    }
}