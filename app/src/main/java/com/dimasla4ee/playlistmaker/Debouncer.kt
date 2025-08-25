package com.dimasla4ee.playlistmaker

import android.os.Handler
import android.os.Looper

object Debouncer {
    private const val CLICK_DEBOUNCE_MILLIS = 1000L
    private const val DEFAULT_DEBOUNCE_MILLIS = 2000L

    private var isClickAllowed = true

    private val handler = Handler(Looper.getMainLooper())

    fun isClickAllowed(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_MILLIS)
        }
        return current
    }

    fun debounce(
        action: Runnable
    ) {
        handler.removeCallbacks(action)
        handler.postDelayed(action, DEFAULT_DEBOUNCE_MILLIS)
    }
}