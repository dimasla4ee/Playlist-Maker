package com.dimasla4ee.playlistmaker

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.dimasla4ee.playlistmaker.databinding.ActivitySearchBinding
import com.google.gson.Gson
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchInputEditText: EditText
    private lateinit var searchHistoryAdapter: TracksAdapter

    private lateinit var searchHistory: SearchHistory
    private var query: String = EMPTY_QUERY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        searchInputEditText = binding.searchInputEditText

        prefs = getSharedPreferences(PreferenceKeys.SEARCH_PREFERENCES, MODE_PRIVATE)
        searchHistory = SearchHistory(prefs)
        searchHistoryAdapter = TracksAdapter { onItemClick(it) }
        val searchResultsAdapter = TracksAdapter { onItemClick(it) }

        setContent(ContentType.NONE)
        searchInputEditText.setText(query)

        binding.searchHistoryRecyclerView.adapter = searchHistoryAdapter
        binding.searchResultsRecyclerView.adapter = searchResultsAdapter

        setupWindowInsets(binding.root)
        setupListeners(searchHistory, searchResultsAdapter)
    }

    private fun onItemClick(track: Track) {
        searchHistory.add(track)
        searchHistoryAdapter.submitList(searchHistory.get())

        val intent = Intent(this@SearchActivity, PlayerActivity::class.java).apply {
            val json = Gson().toJson(track)
            putExtra("song_info", json)
            Log.d("JSON", track.toString())
            Log.d("JSON", json)
        }
        startActivity(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(PreferenceKeys.Keys.SEARCH_QUERY, query)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        query = savedInstanceState.getString(PreferenceKeys.Keys.SEARCH_QUERY, EMPTY_QUERY)
    }

    private fun setupListeners(
        searchHistory: SearchHistory,
        tracksAdapter: TracksAdapter,
    ) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager

        prefs.registerOnSharedPreferenceChangeListener { _, _ ->
            searchHistoryAdapter.submitList(searchHistory.get())
        }

        binding.searchHistoryClearButton.setOnClickListener {
            searchHistory.clear()
            setContent(ContentType.NONE)
        }

        binding.headerBackButton.setOnClickListener {
            finish()
        }

        searchInputEditText.apply {
            doOnTextChanged { text, _, _, _ ->
                binding.searchInputClearButton.visibility = setVisibility(!text.isNullOrEmpty())

                query = text?.toString() ?: EMPTY_QUERY
                if (query.isEmpty()) {
                    tracksAdapter.submitList(emptyList())
                    searchHistoryAdapter.submitList(searchHistory.get())
                    setContent(ContentType.SEARCH_HISTORY)
                } else {
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
                        fetchSongsAndUpdateUi(tracksAdapter)
                    }
                    true
                }
                false
            }
        }

        binding.searchInputClearButton.setOnClickListener {
            inputMethodManager?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            searchInputEditText.apply {
                setText(EMPTY_QUERY)
                clearFocus()
                setContent(ContentType.NONE)
            }
        }

        binding.searchBarContainerLayout.setOnClickListener {
            searchInputEditText.requestFocus()
        }

        binding.searchStatusReloadButton.setOnClickListener {
            lifecycleScope.launch {
                fetchSongsAndUpdateUi(tracksAdapter)
            }
        }
    }

    private suspend fun fetchSongsAndUpdateUi(tracksAdapter: TracksAdapter) {
        ItunesApiClient.getSongs(
            query = query,
            doOnResponse = { _, response ->
                val responseBody = response.body()

                val loadedTracks = responseBody?.results ?: emptyList()
                tracksAdapter.submitList(loadedTracks)

                setContent(
                    when {
                        response.code() != 200 -> ContentType.ERROR
                        responseBody?.resultCount == 0 -> ContentType.NO_RESULTS
                        else -> ContentType.TRACKLIST
                    }
                )
            },
            doOnFailure = { _, _ ->
                setContent(ContentType.ERROR)
            }
        )
    }

    private fun setContent(contentType: ContentType) {
        binding.searchHistoryLayout.visibility =
            setVisibility(contentType == ContentType.SEARCH_HISTORY)
        binding.searchResultsRecyclerView.visibility =
            setVisibility(contentType == ContentType.TRACKLIST)

        binding.searchStatusLayout.apply {
            if (contentType == ContentType.NO_RESULTS || contentType == ContentType.ERROR) {
                visibility = VISIBLE
                binding.searchStatusImageView.setImageResource(contentType.res!!)
                binding.searchStatusTextView.setText(contentType.text!!)
                binding.searchStatusReloadButton.visibility =
                    setVisibility(contentType == ContentType.ERROR)
            } else {
                visibility = GONE
            }
        }
    }

    private fun setVisibility(isVisible: Boolean): Int = if (isVisible) VISIBLE else GONE

    enum class ContentType(
        val res: Int?,
        val text: Int?
    ) {
        NONE(null, null),
        SEARCH_HISTORY(null, null),
        TRACKLIST(null, null),
        NO_RESULTS(R.drawable.ic_nothing_found_120, R.string.no_results),
        ERROR(R.drawable.ic_no_internet_120, R.string.network_error)
    }

    private companion object {
        const val EMPTY_QUERY = ""
    }
}