package com.moviesearchapp

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.moviesearchapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var list: ArrayList<MoviesDataClass>
    private lateinit var movieName: String
    lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        list = ArrayList()
        movieName = ""
        sharedPref = getSharedPreferences("moviesName", Context.MODE_PRIVATE)


        defaultSearch()

        binding.imageButton.setOnClickListener {
            if (binding.searchET.text.isNotEmpty()) {
                searchBtnClick()
            }
        }
    }

    private fun searchBtnClick() {
        list.clear()
        movieName = binding.searchET.text.toString()
        getMovies(movieName)
        savedLastSearchValue(binding.searchET.text.toString())
    }

    private fun defaultSearch() {
        val savedMovie = retrieveLastSearchValue()
        movieName = if (savedMovie == "") "wonder"
        else savedMovie
        getMovies(savedMovie)
    }

    private fun retrieveLastSearchValue(): String {
        return sharedPref.getString("savedMovieMame", "")!!
    }

    private fun savedLastSearchValue(str: String) {
        val editor = sharedPref.edit()
        editor.putString("savedMovieMame", str)
        editor.apply()
    }

    private fun getMovies(str: String) {
        val url =
            "https://api.themoviedb.org/3/search/movie?query=$str&api_key=6db88f854da28189f30b3b2dba9b3877"
        val queue = Volley.newRequestQueue(this)
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            {
                val results = it.getJSONArray("results")
                for (i in 0 until results.length()) {
                    val childObject = results.getJSONObject(i)
                    val language = childObject.getString("original_language")
                    val title = childObject.getString("original_title")
                    val overview = childObject.getString("overview")
                    val date = childObject.getString("release_date")
                    val image = childObject.getString("poster_path")
                    list.add(MoviesDataClass(title, overview, date, language, image))
                }
                binding.recyclerView.layoutManager =
                    LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                binding.recyclerView.adapter = MyAdapter(this, R.layout.movie_card_layout, list)
            },
            {
            }
        )
        queue.add(jsonRequest)
    }
}