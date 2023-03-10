package com.example.mealapp.activties

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mealapp.Category
import com.example.mealapp.CategoryDetailAdapter
import com.example.mealapp.CategoryDetailsRepo
import com.example.mealapp.R
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.net.URL

class CategoryDetailsActivity  : AppCompatActivity() {
   private lateinit var recyclerView: RecyclerView

   private lateinit var categoriesDetailsAdapter: CategoryDetailAdapter

   private lateinit var title: TextView

   private lateinit var backButton: ImageButton



   private var category = Category()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_category_details)
      supportActionBar?.hide()
      category.strCategory= intent.getStringExtra("strCategory")
      recyclerView = findViewById(R.id.recycler_view_details)
      title = findViewById(R.id.title)
      title.text = category.strCategory
      backButton =  findViewById(R.id.ib_back)
      backButton.setOnClickListener {
         finish()
      }


      val url = URL("https://www.themealdb.com/api/json/v1/1/filter.php?c="+category.strCategory)

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
               val categoryDetailsResponse = gson.fromJson(it, CategoryDetailsRepo::class.java)
               categoryDetailsResponse.meals?.let { it1 ->
                  runOnUiThread {
                     categoriesDetailsAdapter = CategoryDetailAdapter(it1)
                     recyclerView.adapter = categoriesDetailsAdapter
                     recyclerView.layoutManager = GridLayoutManager(applicationContext,2)
                     recyclerView.itemAnimator = DefaultItemAnimator()

                  }

               }
               Log.d("OKHTTP", "Meals : "
                  +category.strCategory+" Got " + categoryDetailsResponse.meals?.count() + " results")
            }
         }
      })

   }

}