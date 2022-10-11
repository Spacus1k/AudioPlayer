package com.example.audioplayer.presentation.utils

import android.util.Log

fun Any.debugLog(message: String){
    Log.d(this.javaClass.kotlin.simpleName, message)
}

fun Any.errorLog(message: String){
    Log.e(this.javaClass.kotlin.simpleName, message)
}