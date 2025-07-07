package com.dimasla4ee.playlistmaker

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

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

        val searchBarTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                //TODO: Not yet implemented
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                clearQueryButton.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
                query = s?.toString() ?: EMPTY_QUERY
            }

            override fun afterTextChanged(s: Editable?) {
                //TODO: Not yet implemented
            }
        }

        searchbar.addTextChangedListener(searchBarTextWatcher)

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

    companion object {
        const val EMPTY_QUERY = ""
        const val QUERY_KEY = "QUERY_KEY"
    }
}