package com.dimasla4ee.playlistmaker.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.dimasla4ee.playlistmaker.App
import com.dimasla4ee.playlistmaker.R
import com.dimasla4ee.playlistmaker.databinding.ActivitySettingsBinding
import com.dimasla4ee.playlistmaker.util.setupWindowInsets

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)

        setContentView(binding.root)
        enableEdgeToEdge()
        setupWindowInsets(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.darkThemeSwitch.apply {
            isChecked = (applicationContext as App).isDarkThemeEnabled
        }

        binding.panelHeader.setOnIconClickListener {
            finish()
        }

        binding.darkThemeLayout.setOnClickListener {
            binding.darkThemeSwitch.isChecked = !binding.darkThemeSwitch.isChecked
        }

        binding.darkThemeSwitch.setOnCheckedChangeListener { _, checked ->
            (applicationContext as App).setAppTheme(checked)
        }

        binding.shareAppContainer.setOnClickListener {
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

        binding.textSupportContainer.setOnClickListener {
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

        binding.userAgreementContainer.setOnClickListener {
            val agreementIntent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = getString(R.string.user_agreement_link).toUri()
            }

            startActivity(agreementIntent)
        }
    }
}