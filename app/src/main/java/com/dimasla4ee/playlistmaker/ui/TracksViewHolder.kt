package com.dimasla4ee.playlistmaker.ui

import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dimasla4ee.playlistmaker.R
import com.dimasla4ee.playlistmaker.databinding.TrackItemBinding
import com.dimasla4ee.playlistmaker.domain.model.Track
import com.dimasla4ee.playlistmaker.presentation.mapper.TrackDetailedInfoMapper
import com.dimasla4ee.playlistmaker.presentation.util.dpToPx
import com.dimasla4ee.playlistmaker.util.LogUtil

class TracksViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val binding: TrackItemBinding = TrackItemBinding.bind(itemView)

    private val context = itemView.context

    fun bind(
        track: Track,
        onItemClick: (Track) -> Unit
    ) {
        val dpRadius = itemView.resources.getDimension(R.dimen.small_25)
        val pxRadius = dpRadius.dpToPx(context).toInt()
        val placeholder = AppCompatResources.getDrawable(context, R.drawable.ic_placeholder_45)
        val trackUi = TrackDetailedInfoMapper.map(track)

        placeholder?.setTint(context.getColor(R.color.light_gray))

        binding.titleTextView.text = track.title

        binding.artistAndTimeTextView.text = context.getString(
            R.string.artist_and_time,
            track.artist, trackUi.duration
        )

        binding.trackContainer.setOnClickListener {
            if (Debouncer.isClickAllowed()) {
                onItemClick(track)
                LogUtil.d("TracksViewHolder", "bind: $track")
            }
        }

        Glide.with(itemView)
            .load(track.thumbnailUrl)
            .placeholder(placeholder)
            .transform(RoundedCorners(pxRadius))
            .fitCenter()
            .into(binding.albumCover)
    }
}