package com.example.playlistmaker

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Adapter
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random
import android.content.res.Configuration
import android.widget.Button
import android.widget.LinearLayout

class SearchActivity : AppCompatActivity() {

    private val iTunesUrl = "https://itunes.apple.com"
    private val retrofit = Retrofit.Builder().baseUrl(iTunesUrl).addConverterFactory(
        GsonConverterFactory.create()).build()
    private val iTunesService = retrofit.create(ITunesApi::class.java)
    private lateinit var recycler: RecyclerView

    private lateinit var placeholderError: LinearLayout
    private lateinit var placeholderEmpty: LinearLayout
    private  var lastQuery:String =""
    var editTextInfo:String?=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        placeholderError = findViewById<LinearLayout>(R.id.placeholderError)
        placeholderEmpty = findViewById<LinearLayout>(R.id.placeholderEmpty)

        val editTextLine = findViewById<EditText>(R.id.EditTextLine)
        val clearButton = findViewById<ImageView>(R.id.clearButton)
        editTextLine.setText(editTextInfo)

        val textWatcherOnLineSearch = object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                editTextInfo = s?.toString()?:""
                clearButton.visibility = clearButtonVisibility(s)
            }
            override fun afterTextChanged(s: Editable?) {
            }
        }

        editTextLine.addTextChangedListener(textWatcherOnLineSearch)

        clearButton.setOnClickListener {
            editTextLine.setText("")
            val editTextLine = findViewById<EditText>(R.id.EditTextLine)
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(editTextLine.windowToken, 0)
            recycler.visibility = View.GONE
            placeholderEmpty.visibility = View.GONE
            placeholderError.visibility = View.GONE
        }

        val refreshButton = findViewById<Button>(R.id.refreshButton)
        refreshButton.setOnClickListener {
            searchData(lastQuery)
        }

        recycler = findViewById<RecyclerView>(R.id.tracksList)
        recycler.layoutManager = LinearLayoutManager(this)

        editTextLine.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                lastQuery = editTextLine.text.toString().trim()
                searchData(lastQuery)
                true
            }
            else
                false
        }

        val placeholderErrorImage = findViewById<ImageView>(R.id.placeholderErrorImage)
        placeholderErrorImage.setImageResource(
            if ((resources.configuration.uiMode and
                        Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
            ) R.drawable.placeholder_error_night
            else R.drawable.placeholder_error_light
        )

        val placeholderEmptyImage = findViewById<ImageView>(R.id.placeholderEmptyImage)
        placeholderEmptyImage.setImageResource(
            if ((resources.configuration.uiMode and
                        Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
            ) R.drawable.placeholder_empty_night
            else R.drawable.placeholder_empty_light
        )

    }
    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EDIT_TEXT_INFO,editTextInfo)
    }

    companion object{
        const val EDIT_TEXT_INFO="EDIT_TEXT_INFO"
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        editTextInfo = savedInstanceState.getString(EDIT_TEXT_INFO)
    }

    fun searchData(query: String){
        if(query.isNotEmpty())
            iTunesService.search(query).enqueue(object : Callback<TrackResponse>{
                override fun onResponse(
                    call: Call<TrackResponse?>,
                    response: Response<TrackResponse?>
                ) {
                    if(response.code()==200)
                        if(response.body()?.results?.isNotEmpty()==true){
                            val testData = listOf(Track(null,null,null,null))
                            recycler.adapter = TrackAdapter(response.body()?.results?:emptyList())
                            recycler.visibility = View.VISIBLE
                            placeholderEmpty.visibility = View.GONE
                            placeholderError.visibility = View.GONE
                        }else{
                            recycler.visibility = View.GONE
                            placeholderEmpty.visibility = View.VISIBLE
                            placeholderError.visibility = View.GONE
                        }
                    else{
                        recycler.visibility = View.GONE
                        placeholderEmpty.visibility = View.GONE
                        placeholderError.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<TrackResponse?>, t: Throwable) {
                    recycler.visibility = View.GONE
                    placeholderEmpty.visibility = View.GONE
                    placeholderError.visibility = View.VISIBLE
                }
            }
            )
    }
}