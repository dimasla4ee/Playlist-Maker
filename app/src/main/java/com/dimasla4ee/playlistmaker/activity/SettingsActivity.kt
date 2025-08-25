package com.dimasla4ee.playlistmaker.activity

import android.content.Intent
import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import com.dimasla4ee.playlistmaker.App
import com.dimasla4ee.playlistmaker.R
import com.dimasla4ee.playlistmaker.databinding.ActivitySettingsBinding
import com.dimasla4ee.playlistmaker.setupWindowInsets

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var toggleThemeButton: ConstraintLayout
    private lateinit var toggleThemeSwitch: CompoundButton
    private lateinit var shareAppButton: ConstraintLayout
    private lateinit var contactSupportButton: ConstraintLayout
    private lateinit var userAgreementButton: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        toggleThemeButton = binding.darkThemeLayout
        toggleThemeSwitch = binding.darkThemeSwitch.apply {
            isChecked = (applicationContext as App).isDarkThemeEnabled
        }
        shareAppButton = binding.shareAppContainer
        contactSupportButton = binding.textSupportContainer
        userAgreementButton = binding.userAgreementContainer

        setContentView(binding.root)
        enableEdgeToEdge()

        setupWindowInsets(binding.root)

        binding.panelHeader.setOnIconClickListener {
            finish()
        }

        toggleThemeButton.setOnClickListener {
            toggleThemeSwitch.isChecked = !toggleThemeSwitch.isChecked
        }

        binding.darkThemeSwitch.setOnCheckedChangeListener { _, checked ->
            (applicationContext as App).setAppTheme(checked)
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