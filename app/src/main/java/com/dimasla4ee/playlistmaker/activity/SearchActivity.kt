package com.dimasla4ee.playlistmaker.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.dimasla4ee.playlistmaker.Debouncer
import com.dimasla4ee.playlistmaker.ItunesApiClient
import com.dimasla4ee.playlistmaker.LogUtil
import com.dimasla4ee.playlistmaker.PlayerActivity
import com.dimasla4ee.playlistmaker.PreferenceKeys
import com.dimasla4ee.playlistmaker.R
import com.dimasla4ee.playlistmaker.SearchHistory
import com.dimasla4ee.playlistmaker.Track
import com.dimasla4ee.playlistmaker.TracksAdapter
import com.dimasla4ee.playlistmaker.databinding.ActivitySearchBinding
import com.dimasla4ee.playlistmaker.setupWindowInsets
import com.google.gson.Gson
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchInputEditText: EditText
    private lateinit var searchHistoryAdapter: TracksAdapter
    private lateinit var searchResultsAdapter: TracksAdapter

    private lateinit var searchHistory: SearchHistory
    private var query: String = DEFAULT_QUERY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        setupWindowInsets(binding.root)

        searchInputEditText = binding.searchInputEditText

        prefs = getSharedPreferences(PreferenceKeys.SEARCH_PREFERENCES, MODE_PRIVATE)
        searchHistory = SearchHistory(prefs)
        searchHistoryAdapter = TracksAdapter { onItemClick(it) }
        searchResultsAdapter = TracksAdapter { onItemClick(it) }

        setContent(ContentType.NONE)
        searchInputEditText.setText(query)

        binding.searchHistoryRecyclerView.adapter = searchHistoryAdapter
        binding.searchResultsRecyclerView.adapter = searchResultsAdapter

        setupListeners()
    }

    private fun onItemClick(track: Track) {
        searchHistory.add(track)
        searchHistoryAdapter.submitList(searchHistory.get())

        val intent = Intent(
            this@SearchActivity,
            PlayerActivity::class.java
        ).apply {
            val json = Gson().toJson(track)
            putExtra("song_info", json)
        }

        startActivity(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(PreferenceKeys.Keys.SEARCH_QUERY, query)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        query = savedInstanceState.getString(PreferenceKeys.Keys.SEARCH_QUERY, DEFAULT_QUERY)
    }

    private val getSongs = Runnable {
        fetchSongsAndUpdateUi()
        LogUtil.d("SearchActivity", "getSongs: $query")
    }

    private fun setupListeners() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager

        prefs.registerOnSharedPreferenceChangeListener { _, _ ->
            searchHistoryAdapter.submitList(searchHistory.get())
        }

        binding.searchHistoryClearButton.setOnClickListener {
            searchHistory.clear()
            setContent(ContentType.NONE)
        }

        binding.panelHeader.setOnIconClickListener {
            finish()
        }

        searchInputEditText.apply {
            doOnTextChanged { text, _, _, _ ->
                binding.searchInputClearButton.visibility = setVisibility(!text.isNullOrEmpty())

                query = text?.toString() ?: DEFAULT_QUERY
                if (query.isEmpty()) {
                    searchResultsAdapter.submitList(emptyList())
                    searchHistoryAdapter.submitList(searchHistory.get())
                    setContent(ContentType.SEARCH_HISTORY)
                } else {
                    Debouncer.debounce {
                        getSongs.run()
                    }
                    setContent(ContentType.NONE)
                }
            }

            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus && query.isEmpty() && searchHistory.get().isNotEmpty()) {
                    setContent(ContentType.SEARCH_HISTORY)
                    searchHistoryAdapter.submitList(searchHistory.get())
                }
            }

            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    inputMethodManager?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                    lifecycleScope.launch {
                        fetchSongsAndUpdateUi()
                    }
                    true
                }
                false
            }
        }

        binding.searchInputClearButton.setOnClickListener {
            inputMethodManager?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            searchInputEditText.apply {
                setText(DEFAULT_QUERY)
                clearFocus()
                setContent(ContentType.NONE)
            }
        }

        binding.searchBarContainerLayout.setOnClickListener {
            searchInputEditText.requestFocus()
        }

        binding.searchStatusReloadButton.setOnClickListener {
            lifecycleScope.launch {
                fetchSongsAndUpdateUi()
            }
        }
    }

    private fun fetchSongsAndUpdateUi() {
        if (query.isEmpty()) {
            setContent(ContentType.SEARCH_HISTORY)
            return
        }

        binding.searchProgressBar.visibility = VISIBLE
        lifecycleScope.launch {
            ItunesApiClient.getSongs(query).run {
                onSuccess { tracksList ->
                    searchResultsAdapter.submitList(tracksList)
                    setContent(
                        if (tracksList.isEmpty()) {
                            ContentType.NO_RESULTS
                        } else {
                            ContentType.TRACKLIST
                        }
                    )
                }
                onFailure { exception ->
                    setContent(ContentType.ERROR)
                    LogUtil.e("SearchActivity", "fetchSongsAndUpdateUi: $exception")
                }
            }
        }
    }

    private fun setContent(type: ContentType) {
        binding.apply {
            searchHistoryLayout.visibility = setVisibility(type == ContentType.SEARCH_HISTORY)
            searchResultsRecyclerView.visibility = setVisibility(type == ContentType.TRACKLIST)
            searchProgressBar.visibility = GONE

            searchStatusLayout.apply {
                if (type == ContentType.NO_RESULTS || type == ContentType.ERROR) {
                    visibility = VISIBLE
                    searchStatusImageView.setImageResource(type.res!!)
                    searchStatusTextView.setText(type.text!!)
                    searchStatusReloadButton.visibility = setVisibility(type == ContentType.ERROR)
                } else {
                    visibility = GONE
                }
            }
        }
    }

    private fun setVisibility(isVisible: Boolean): Int = if (isVisible) VISIBLE else GONE

    private enum class ContentType(
        @param:DrawableRes val res: Int?,
        @param:StringRes val text: Int?
    ) {
        NONE(null, null),
        SEARCH_HISTORY(null, null),
        TRACKLIST(null, null),
        NO_RESULTS(R.drawable.ic_nothing_found_120, R.string.no_results),
        ERROR(R.drawable.ic_no_internet_120, R.string.network_error)
    }

    private companion object {
        const val DEFAULT_QUERY = ""
    }
}