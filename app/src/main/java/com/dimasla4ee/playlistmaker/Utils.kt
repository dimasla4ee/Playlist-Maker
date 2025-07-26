package com.dimasla4ee.playlistmaker

import android.content.Context
import android.util.TypedValue
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

fun Float.dpToPx(context: Context): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this,
    context.resources.displayMetrics
)

fun setupWindowInsets(windowView: View) {
    ViewCompat.setOnApplyWindowInsetsListener(windowView) { view, windowInsets ->
        val systemBarsInsets = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars())
        val imeInsets = windowInsets.getInsets(WindowInsetsCompat.Type.ime())

        view.updatePadding(
            top = systemBarsInsets.top,
            left = systemBarsInsets.left,
            right = systemBarsInsets.right,
            bottom = maxOf(systemBarsInsets.bottom, imeInsets.bottom)
        )

        WindowInsetsCompat.CONSUMED
    }
}