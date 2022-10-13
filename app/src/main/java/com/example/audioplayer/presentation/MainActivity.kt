package com.example.audioplayer.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.compose.material.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.audioplayer.presentation.theme.AudioPlayerTheme
import com.example.audioplayer.presentation.ui.AudioViewModel
import com.example.audioplayer.presentation.ui.screens.HomeScreen
import com.example.audioplayer.presentation.ui.screens.WithoutPermissionScreen
import com.example.audioplayer.presentation.utils.PermissionManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val multiplePermissionContract =
        registerForActivityResult(RequestMultiplePermissions()) { permissionsStatusMap ->
            setContent {
                AudioPlayerTheme {
                    Surface{
                        if (!permissionsStatusMap.containsValue(false)) {
                            HomeScreen(viewModel = viewModel(modelClass = AudioViewModel::class.java))
                        } else {
                            WithoutPermissionScreen()
                        }
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        multiplePermissionContract.launch(PermissionManager.PERMISSIONS)
    }
}