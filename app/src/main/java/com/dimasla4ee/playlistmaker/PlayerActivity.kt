package com.dimasla4ee.playlistmaker

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dimasla4ee.playlistmaker.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_player)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val radius = IMAGE_CORNER_RADIUS.dpToPx(this).toInt()
        val placeholder = AppCompatResources.getDrawable(this, R.drawable.ic_placeholder_45)

        placeholder?.setTint(getColor(R.color.light_gray))

        val binding: ActivityPlayerBinding = ActivityPlayerBinding.inflate(layoutInflater)

        Glide.with(binding.root)
            .load("https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/a0/4d/c4/a04dc484-03cc-02aa-fa82-5334fcb4bc16/18UMGIM24878.rgb.jpg/100x100bb.jpg")
            .placeholder(placeholder)
            .transform(RoundedCorners(radius))
            .fitCenter()
            .into(binding.songCover)
    }

    private companion object {
        const val IMAGE_CORNER_RADIUS = 8f
    }
}