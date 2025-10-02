package com.dimasla4ee.playlistmaker.presentation.util

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

/**
 * Sets the visibility of a View.
 *
 * @param show If true, the View is set to `VISIBLE`. Otherwise, it's set to `GONE`.
 */
fun View.show(show: Boolean) {
    this.visibility = if (show) VISIBLE else GONE
}

/**
 * Sets up window insets for a given View.
 *
 * This function applies padding to the `View` to account for system bars
 * (like the status bar) and the Input Method Editor (IME, e.g., the on-screen keyboard).
 * It ensures that the content of the `View` is not obscured by these system UI elements.
 *
 * The padding is adjusted as follows:
 * - `top`: Padded by the top inset of the system bars.
 * - `left`: Padded by the left inset of the system bars.
 * - `right`: Padded by the right inset of the system bars.
 * - `bottom`: Padded by the maximum of the system bars' bottom inset and the IME's bottom inset.
 *   This handles cases where the keyboard is visible, ensuring content isn't hidden beneath it.
 *
 * An optional callback `extraInsetsHandler` can be provided to perform additional actions
 * after the insets have been processed and padding has been applied.
 *
 * @param extraInsetsHandler An optional lambda function that will be invoked after the
 *                        window insets has been processed and padding has been applied
 *                        to the `View`.
 *                        It receives the `WindowInsetsCompat` object
 *                        as a parameter, allowing for further custom handling if needed.
 *                        Defaults to `null` if no additional action is required.
 */
fun View.setupWindowInsets(
    extraInsetsHandler: ((insets: WindowInsetsCompat) -> Unit)? = null
) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, windowInsets ->
        val systemBarsInsets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        val imeInsets = windowInsets.getInsets(WindowInsetsCompat.Type.ime())

        view.updatePadding(
            top = systemBarsInsets.top,
            left = systemBarsInsets.left,
            right = systemBarsInsets.right,
            bottom = maxOf(systemBarsInsets.bottom, imeInsets.bottom)
        )

        extraInsetsHandler?.invoke(windowInsets)

        WindowInsetsCompat.CONSUMED
    }
}
