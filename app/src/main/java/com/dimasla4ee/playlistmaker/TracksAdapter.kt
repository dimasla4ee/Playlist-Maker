package com.dimasla4ee.playlistmaker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class TracksAdapter(
    private val onItemClick: (Track) -> Unit
) : ListAdapter<Track, TracksViewHolder>(SongsCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TracksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_item, parent, false)
        return TracksViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TracksViewHolder,
        position: Int
    ) {
        val track = getItem(position)
        holder.bind(track, onItemClick)
    }
}

class SongsCallback() : DiffUtil.ItemCallback<Track>() {

    override fun areItemsTheSame(
        oldItem: Track,
        newItem: Track
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Track,
        newItem: Track
    ): Boolean = oldItem == newItem
}