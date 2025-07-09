package com.dimasla4ee.playlistmaker

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged

class SearchActivity : AppCompatActivity() {
    private var query: String = EMPTY_QUERY

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(QUERY_KEY, query)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        query = savedInstanceState.getString(QUERY_KEY, EMPTY_QUERY)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager

        val searchbarLayout = findViewById<LinearLayout>(R.id.searchBar)
        val searchbar = findViewById<EditText>(R.id.searchBarEditText)
        val clearQueryButton = findViewById<ImageView>(R.id.clearSearchBarButton)
        val backButton = findViewById<ImageButton>(R.id.backButton)

        searchbar.setText(query)

        searchbar.doOnTextChanged { text, _, _, _ ->
            clearQueryButton.visibility = if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
            query = text?.toString() ?: EMPTY_QUERY
        }

        clearQueryButton.setOnClickListener {
            inputMethodManager?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            searchbar.apply {
                setText(EMPTY_QUERY)
                clearFocus()
            }
        }

        searchbarLayout.setOnClickListener {
            searchbar.requestFocus()
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    private companion object {
        const val EMPTY_QUERY = ""
        const val QUERY_KEY = "QUERY_KEY"
    }
}