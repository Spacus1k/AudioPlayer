package com.example.audioplayer.presentation

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.audioplayer.presentation.theme.AudioPlayerTheme
import com.example.audioplayer.presentation.ui.AudioViewModel
import com.example.audioplayer.presentation.ui.screens.WithoutPermissionScreen
import com.example.audioplayer.presentation.utils.PermissionManager
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    private val multiplePermissionContract =
        registerForActivityResult(RequestMultiplePermissions()) { permissionsStatusMap ->
            setContent {
                AudioPlayerTheme {
                    val navController = rememberAnimatedNavController()

                    Surface {
                        if (!permissionsStatusMap.containsValue(false)) {
                            AudioNavHost(
                                navController = navController,
                                modifier = Modifier,
                                audioViewModel = viewModel(modelClass = AudioViewModel::class.java)
                            )
                        } else {
                            WithoutPermissionScreen()
                        }
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        multiplePermissionContract.launch(PermissionManager.PERMISSIONS)
    }
}

