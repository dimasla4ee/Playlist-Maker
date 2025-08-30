package com.dimasla4ee.playlistmaker

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Converts a value in density-independent pixels (dp) to pixels (px).
 *
 * @param context The context to access resources and display metrics.
 * @return The converted value in pixels.
 */
fun Float.dpToPx(context: Context): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this,
    context.resources.displayMetrics
)

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
 * This function applies padding to the provided `windowView` to account for system bars
 * (like the status bar) and the Input Method Editor (IME, e.g., the on-screen keyboard).
 * It ensures that the content of the `windowView` is not obscured by these system UI elements.
 *
 * The padding is adjusted as follows:
 * - `top`: Padded by the height of the status bar.
 * - `left`: Padded by the left inset of the status bar.
 * - `right`: Padded by the right inset of the status bar.
 * - `bottom`: Padded by the maximum of the status bar's bottom inset and the IME's bottom inset.
 *   This handles cases where the keyboard is visible, ensuring content isn't hidden beneath it.
 *
 * An optional callback `extraInsetsHandler` can be provided to perform additional actions
 * after the insets have been processed and padding has been applied.
 *
 * @param windowView The [View] to which the window insets and padding will be applied.
 *                   This is typically the root view of a layout or a specific container
 *                   that needs to react to system UI changes.
 * @param extraInsetsHandler An optional lambda function that will be invoked after the
 *                        window insets has been processed and padding has been applied
 *                        to the `windowView`.
 *                        It receives the `WindowInsetsCompat` object
 *                        as a parameter, allowing for further custom handling if needed.
 *                        Defaults to `null` if no additional action is required.
 */
fun setupWindowInsets(
    windowView: View,
    extraInsetsHandler: ((insets: WindowInsetsCompat) -> Unit)? = null
) {
    ViewCompat.setOnApplyWindowInsetsListener(windowView) { view, windowInsets ->
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

/**
 * Formats an integer representing milliseconds into a "mm:ss" string.
 *
 * @return A string representation of the time in minutes and seconds.
 */
fun Int.toMmSs(): String = SimpleDateFormat("mm:ss", Locale.getDefault()).format(this)