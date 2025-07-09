package com.dimasla4ee.playlistmaker

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backButton = findViewById<ImageButton>(R.id.backButton).apply {
            setOnClickListener {
                finish()
            }
        }

        val darkThemeSwitch = findViewById<SwitchCompat>(R.id.darkThemeSwitch)

        val darkThemeLayout = findViewById<ConstraintLayout>(R.id.darkThemeLayout).apply {
            setOnClickListener {
                darkThemeSwitch.isChecked = !darkThemeSwitch.isChecked
            }
        }

        val shareAppLayout = findViewById<ConstraintLayout>(R.id.shareAppContainer).apply {
            setOnClickListener {
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, getString(R.string.yandex_practicum_course_link))
                    type = getString(R.string.plain_text_intent_type)
                }
                startActivity(shareIntent)
            }
        }

        val textSupportLayout = findViewById<ConstraintLayout>(R.id.textSupportContainer).apply {
            setOnClickListener {
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SENDTO
                    data = context.getString(R.string.mailto_uri_data).toUri()
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(context.getString(R.string.developer_email)))
                    putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.letter_subject))
                    putExtra(Intent.EXTRA_TEXT, context.getString(R.string.letter_text))
                }

                startActivity(shareIntent)
            }
        }

        val userAgreementLayout =
            findViewById<ConstraintLayout>(R.id.userAgreementContainer).apply {
                setOnClickListener {
                    val shareIntent = Intent().apply {
                        action = Intent.ACTION_VIEW
                        data = getString(R.string.user_agreement_link).toUri()
                    }

                    startActivity(shareIntent)
                }
            }
    }
}