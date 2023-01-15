package com.example.mealapp

import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import okhttp3.*
import java.io.IOException
import java.net.URL


class RecipeActivity : AppCompatActivity() {
    private lateinit var details: DetailsRecipe
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_page)

        val url = URL("https://www.themealdb.com/api/json/v1/1/lookup.php?i=52772")

        val request = Request.Builder()
            .url(url)
            .build()

        var titleTextView : TextView = findViewById(R.id.titleRecipe)
        var ingredientsListView : ListView = findViewById(R.id.ingredients)
        var recipeTextView : TextView = findViewById(R.id.recipeText)
        var mealImageView : ImageView = findViewById(R.id.imageMeal)
        var youtubeLinkTextView : TextView = findViewById(R.id.youtubeLink)
        val spannableString : SpannableString = SpannableString("Youtube video of the recipe")
        val clickSpan : ClickableSpan = object : ClickableSpan() {
            override fun onClick(TextView: View) {

            }

        }

        val recipeRunnable = java.lang.Runnable {
            titleTextView.text = details.strMeal

            Picasso.get().load(Uri.parse(details.strMealThumb)).into(mealImageView)
            //ajout Ingrédients
            val listItems : Array<String> = getIngredients(details)
            val adapter =ArrayAdapter<String>(this, R.layout.ingredient_item, listItems)
            ingredientsListView.adapter = adapter
            val vg: ViewGroup = ingredientsListView
            var totalHeight = 0
            for (i in 0 until adapter.count) {
                val listItem: View = adapter.getView(i, null, vg)
                listItem.measure(0, 0)
                totalHeight += listItem.getMeasuredHeight()
            }

            val par: ViewGroup.LayoutParams = ingredientsListView.getLayoutParams()
            par.height = totalHeight + ingredientsListView.getDividerHeight() * (adapter.count - 1)
            ingredientsListView.setLayoutParams(par)
            ingredientsListView.requestLayout()


            recipeTextView.text = details.strInstructions

        }

        var client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("OKHTTP", e.localizedMessage)
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonString = response.body?.string()
                val meals: Meals = Gson().fromJson(jsonString, Meals::class.java)
                details = meals.meals[0]
                if (details != null) {
                    Log.d("OKHTTP", details!!.strCategory!!)
                } else {
                    Log.d("OKHTTP", "null details")
                }

                this@RecipeActivity.runOnUiThread(recipeRunnable)
            }

        })


    }

    private fun getIngredients(details: DetailsRecipe): Array<String> {
        var arrayIngredient = arrayOfNulls<String?>(20)
        arrayIngredient[0] = details.strIngredient1
        arrayIngredient[1] = details.strIngredient2
        arrayIngredient[2] = details.strIngredient3
        arrayIngredient[3] = details.strIngredient4
        arrayIngredient[4] = details.strIngredient5
        arrayIngredient[5] = details.strIngredient6
        arrayIngredient[6] = details.strIngredient7
        arrayIngredient[7] = details.strIngredient8
        arrayIngredient[8] = details.strIngredient9
        arrayIngredient[9] = details.strIngredient10
        arrayIngredient[10] = details.strIngredient11
        arrayIngredient[11] = details.strIngredient12
        arrayIngredient[12] = details.strIngredient13
        arrayIngredient[13] = details.strIngredient14
        arrayIngredient[14] = details.strIngredient15
        arrayIngredient[15] = details.strIngredient16
        arrayIngredient[16] = details.strIngredient17
        arrayIngredient[17] = details.strIngredient18
        arrayIngredient[18] = details.strIngredient19
        arrayIngredient[19] = details.strIngredient20


        var arrayMeasure = arrayOfNulls<String?>(20)
        arrayMeasure[0] = details.strMeasure1
        arrayMeasure[1] = details.strMeasure2
        arrayMeasure[2] = details.strMeasure3
        arrayMeasure[3] = details.strMeasure4
        arrayMeasure[4] = details.strMeasure5
        arrayMeasure[5] = details.strMeasure6
        arrayMeasure[6] = details.strMeasure7
        arrayMeasure[7] = details.strMeasure8
        arrayMeasure[8] = details.strMeasure9
        arrayMeasure[9] = details.strMeasure10
        arrayMeasure[10] = details.strMeasure11
        arrayMeasure[11] = details.strMeasure12
        arrayMeasure[12] = details.strMeasure13
        arrayMeasure[13] = details.strMeasure14
        arrayMeasure[14] = details.strMeasure15
        arrayMeasure[15] = details.strMeasure16
        arrayMeasure[16] = details.strMeasure17
        arrayMeasure[17] = details.strMeasure18
        arrayMeasure[18] = details.strMeasure19
        arrayMeasure[19] = details.strMeasure20

        var textIngredientList : ArrayList<String> = arrayListOf<String>()

        for (i in arrayIngredient.indices) {
            if (arrayIngredient[i] != null && arrayIngredient[i] != "") {
                var textIngredient: String = arrayIngredient[i]!!
                if (arrayMeasure[i] != null && arrayMeasure[i] != "") {
                    textIngredient += " : " + arrayMeasure[i]
                }
            textIngredientList.add(textIngredient)
            }
        }
        return textIngredientList.toTypedArray()
    }

}

