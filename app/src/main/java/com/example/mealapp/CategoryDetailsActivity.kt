package com.example.mealapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.net.URL

class CategoryDetailsActivity  : AppCompatActivity() {
   private lateinit var recyclerView: RecyclerView

   private lateinit var categoriesDetailsAdapter: CategoryDetailAdapter

   private lateinit var circularProgressIndicator: CircularProgressIndicator

   private lateinit var title: TextView

   private lateinit var backButton: ImageButton



   private var category = Category()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_category_details)
      window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
      supportActionBar?.hide()
      category.strCategory= intent.getStringExtra("strCategory")
      recyclerView = findViewById(R.id.recycler_view_details)
      title = findViewById(R.id.title)
      title.text = category.strCategory
      backButton =  findViewById(R.id.ib_back)
      backButton.setOnClickListener {
         finish()
      }
      //circularProgressIndicator = findViewById(R.id.circular_progress_indicator)

      val url = URL("https://www.themealdb.com/api/json/v1/1/filter.php?c="+category.strCategory)

      val request = Request.Builder()
         .url(url)
         .build()

      val client = OkHttpClient()

      //circularProgressIndicator.visibility = View.VISIBLE

      client.newCall(request).enqueue(object : Callback {


         override fun onFailure(call: Call, e: IOException) {
            Log.e("OKHTTP", e.localizedMessage)
            //circularProgressIndicator.visibility = View.GONE
         }

         override fun onResponse(call: Call, response: Response) {
            response.body?.string()?.let {
               val gson = Gson()
               val categoryDetailsResponse = gson.fromJson(it, CategoryDetailsRepo::class.java)
               categoryDetailsResponse.meals?.let { it1 ->
                  runOnUiThread {
                     //circularProgressIndicator.visibility = View.GONE
                     categoriesDetailsAdapter = CategoryDetailAdapter(it1)
                     recyclerView.adapter = categoriesDetailsAdapter
                     recyclerView.layoutManager = GridLayoutManager(applicationContext,2)
                     recyclerView.itemAnimator = DefaultItemAnimator()
                     //circularProgressIndicator.visibility = View.GONE


                  }

               }
               Log.d("OKHTTP", "Meals : "
                  +category.strCategory+" Got " + categoryDetailsResponse.meals?.count() + " results")
            }
         }
      })

   }

}