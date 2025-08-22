package com.dimasla4ee.playlistmaker

import android.util.Log

/**
 * Utility object for logging messages.
 *
 * This object is a wrapper for Log API that provides methods to log debug messages
 * that will only be output when the application is in debug mode(i.e., `BuildConfig.DEBUG` is true).
 */
@Suppress("unused")
object LogUtil {
    fun d(tag: String, msg: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg)
        }
    }
}