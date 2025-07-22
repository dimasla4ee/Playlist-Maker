package com.dimasla4ee.playlistmaker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.dimasla4ee.playlistmaker.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySettingsBinding.inflate(layoutInflater)
        val backButton = binding.backButton
        val toggleThemeSwitch = binding.darkThemeSwitch
        val toggleThemeButton = binding.darkThemeLayout
        val shareAppButton = binding.shareAppContainer
        val contactSupportButton = binding.textSupportContainer
        val userAgreementButton = binding.userAgreementContainer

        setContentView(binding.root)

        backButton.setOnClickListener {
            finish()
        }

        toggleThemeButton.setOnClickListener {
            toggleThemeSwitch.isChecked = !toggleThemeSwitch.isChecked
        }

        shareAppButton.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    getString(R.string.yandex_practicum_course_link)
                )
                type = getString(R.string.plain_text_intent_type)
            }
            startActivity(shareIntent)
        }

        contactSupportButton.setOnClickListener {
            val emailIntent = Intent().apply {
                action = Intent.ACTION_SENDTO
                data = getString(R.string.mailto_uri_data).toUri()
                putExtra(
                    Intent.EXTRA_EMAIL,
                    arrayOf(getString(R.string.developer_email))
                )
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.letter_subject))
                putExtra(Intent.EXTRA_TEXT, getString(R.string.letter_text))
            }

            startActivity(emailIntent)
        }

        userAgreementButton.setOnClickListener {
            val agreementIntent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = getString(R.string.user_agreement_link).toUri()
            }

            startActivity(agreementIntent)
        }
    }
}