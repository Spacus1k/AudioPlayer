package com.example.audioplayer.presentation.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionManager {

    companion object {
        private const val REQUEST_CODE = 123

        private fun isStoragePermissionGranted(activity: Activity): Boolean =
            ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED

        fun requestStoragePermission(activity: Activity) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE
            )
        }

        fun checkStoragePermission(
            activity: Activity,
            rationaleAction: () -> Unit,
            permissionGrantedAction: () -> Unit
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!isStoragePermissionGranted(activity)) {
                    if (activity.shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        rationaleAction()
                    } else {
                        // Request permission
                        requestStoragePermission(activity)
                    }
                } else {
                    // Permission already granted
                    permissionGrantedAction()
                }
            }
        }
    }
}