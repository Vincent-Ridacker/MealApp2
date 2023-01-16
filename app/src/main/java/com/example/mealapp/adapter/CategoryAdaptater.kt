package com.example.mealapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mealapp.activties.CategoryDetailsActivity
import com.squareup.picasso.Picasso

class CategoryViewHolder(itemView: View,var category: Category?=null) : RecyclerView.ViewHolder(itemView) {
    var nameTextView: TextView = itemView.findViewById(R.id.categoryName)
    var imageView : ImageView = itemView.findViewById<ImageView>(R.id.categoryImage)
    init{
        itemView.setOnClickListener{
            val intent = Intent(itemView.context, CategoryDetailsActivity::class.java)
            intent.putExtra("strCategory", category?.strCategory)
            itemView.context.startActivity(intent)
        }
    }

}

class CategoryAdaptater(val categories: List<Category>): RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)

        return CategoryViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.nameTextView.setText(categories.get(position).strCategory)
        Picasso.get().load(categories.get(position).strCategoryThumb).into(holder.imageView)
        holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context, android.R.anim.slide_in_left)
        holder.category= categories[position]


    }

    override fun getItemCount(): Int {
        return categories.count()
    }
}