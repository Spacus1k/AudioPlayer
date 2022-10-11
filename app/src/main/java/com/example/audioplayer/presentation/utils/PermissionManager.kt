package com.example.audioplayer.presentation.utils

import android.Manifest.permission.FOREGROUND_SERVICE
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionManager {
    private const val REQUEST_CODE = 123
     val PERMISSIONS = mutableListOf(READ_EXTERNAL_STORAGE).apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            FOREGROUND_SERVICE
        }
    }.toTypedArray()

    private fun isStoragePermissionGranted(activity: Activity, permission: String): Boolean =
        ContextCompat.checkSelfPermission(
            activity, permission
        ) == PackageManager.PERMISSION_GRANTED

    private fun requestPermission(activity: Activity, permission: String) =
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(permission),
            REQUEST_CODE
        )

    private fun checkPermission(
        activity: Activity,
        permission: String,
        rationaleAction: () -> Unit,
        afterRequestAction: () -> Unit
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!isStoragePermissionGranted(activity, permission)) {
                if (activity.shouldShowRequestPermissionRationale(permission)) {
                    rationaleAction()
                } else {
                    // Request permission
                    requestPermission(activity, permission)
                    afterRequestAction()
                }
            }
        }
    }

    fun hasPermissions(context: Context, vararg permissions: String = PERMISSIONS): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }


    fun checkMultiplePermissions(
        activity: Activity,
        permissions: Array<String> = PERMISSIONS,
        rationaleAction: () -> Unit = {},
        afterRequestAction: () -> Unit = {}
    ) {
        permissions.forEach {
            checkPermission(activity, it, rationaleAction, afterRequestAction)
        }
    }
}