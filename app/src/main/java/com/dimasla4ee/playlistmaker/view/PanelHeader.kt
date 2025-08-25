package com.dimasla4ee.playlistmaker.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.dimasla4ee.playlistmaker.R

@Suppress("unused")
class PanelHeader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val iconButton: ImageButton
    private val titleView: TextView

    init {
        orientation = HORIZONTAL
        inflate(context, R.layout.view_panel_header, this)

        iconButton = findViewById(R.id.headerBackButton)
        titleView = findViewById(R.id.headerTitleTextView)

        context.theme.obtainStyledAttributes(
            attrs, R.styleable.PanelHeader, defStyleAttr, 0
        ).apply {
            try {
                titleView.text = getString(R.styleable.PanelHeader_android_text)
                titleView.setTextColor(
                    getColor(R.styleable.PanelHeader_android_textColor, titleView.currentTextColor)
                )
                if (hasValue(R.styleable.PanelHeader_android_textSize)) {
                    titleView.textSize = getDimension(
                        R.styleable.PanelHeader_android_textSize, titleView.textSize
                    )
                }
                val showIcon = getBoolean(R.styleable.PanelHeader_showIcon, false)
                iconButton.visibility = if (showIcon) VISIBLE else GONE
                if (hasValue(R.styleable.PanelHeader_iconSrc)) {
                    iconButton.setImageResource(getResourceId(R.styleable.PanelHeader_iconSrc, 0))
                }
                if (hasValue(R.styleable.PanelHeader_iconTint)) {
                    iconButton.imageTintList = getColorStateList(R.styleable.PanelHeader_iconTint)
                }
            } finally {
                recycle()
            }
        }
    }

    fun setText(text: CharSequence) {
        titleView.text = text
    }

    fun setOnIconClickListener(listener: OnClickListener) {
        iconButton.setOnClickListener(listener)
    }

    fun setIconVisible(visible: Boolean) {
        iconButton.visibility = if (visible) VISIBLE else GONE
    }
}