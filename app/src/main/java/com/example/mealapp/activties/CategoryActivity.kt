package com.example.mealapp.activties

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mealapp.CategoryAdaptater
import com.example.mealapp.CategoryRepo
import com.example.mealapp.R
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.net.URL

class CategoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    private lateinit var categoriesAdapter: CategoryAdaptater

    private lateinit var circularProgressIndicator: CircularProgressIndicator

    private lateinit var title: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        supportActionBar?.hide()
        recyclerView = findViewById(R.id.recycler_view)
        title= findViewById(R.id.title)
        title.text = "Meal App"
        

        val url = URL("https://www.themealdb.com/api/json/v1/1/categories.php")

        val request = Request.Builder()
            .url(url)
            .build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {


            override fun onFailure(call: Call, e: IOException) {
                Log.e("OKHTTP", e.localizedMessage)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let {
                    val gson = Gson()
                    val categoriesResponse = gson.fromJson(it, CategoryRepo::class.java)
                    categoriesResponse.categories?.let { it1 ->
                        runOnUiThread {
                            categoriesAdapter = CategoryAdaptater(it1)
                            recyclerView.adapter = categoriesAdapter
                            recyclerView.layoutManager = GridLayoutManager(applicationContext,2)
                            recyclerView.itemAnimator = DefaultItemAnimator()

                        }

                    }
                    Log.d("OKHTTP", "Got " + categoriesResponse.categories?.count() + " results")
                }
            }
        })

    }
}