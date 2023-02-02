package com.example.audioplayer.domain.utils

import java.util.*

fun formatDuration(timeMs: Float): String {
    val mFormatBuilder = StringBuilder()
    val mFormatter = Formatter(mFormatBuilder, Locale.getDefault())
    val totalSeconds = toSec(timeMs)
    val seconds = totalSeconds % 60
    val minutes = totalSeconds / 60 % 60
    val hours = totalSeconds / 3600
    mFormatBuilder.setLength(0)
    return when {
        hours > 0 -> mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString()
        minutes < 0 -> mFormatter.format("%02d:%02d", minutes, seconds).toString()
        else -> mFormatter.format("%02d:%02d", minutes, seconds).toString()
    }
}

fun formatDuration(totalSeconds: Int): String {
    val mFormatBuilder = StringBuilder()
    val mFormatter = Formatter(mFormatBuilder, Locale.getDefault())
    val seconds = totalSeconds % 60
    val minutes = totalSeconds / 60 % 60
    val hours = totalSeconds / 3600
    mFormatBuilder.setLength(0)
    return when {
        hours > 0 -> mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString()
        minutes < 0 -> mFormatter.format("%02d:%02d", minutes, seconds).toString()
        else -> mFormatter.format("%02d:%02d", minutes, seconds).toString()
    }
}

fun toSec(timeMs: Float) = timeMs.toInt() / 1000