package com.example.mealapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CategoryDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var nameTextView: TextView = itemView.findViewById(R.id.categoryDetailsName)
    var imageView : ImageView = itemView.findViewById(R.id.categoryDetailsImage)

}

class CategoryDetailAdapter(val categoriesdetails: List<CategoryDetails>): RecyclerView.Adapter<CategoryDetailsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryDetailsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.category_details_item, parent, false)
        return CategoryDetailsViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: CategoryDetailsViewHolder, position: Int) {
        holder.nameTextView.setText(categoriesdetails.get(position).strMeal)
        Picasso.get().load(categoriesdetails.get(position).strMealThumb).into(holder.imageView)
        holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context, android.R.anim.slide_in_left)

    }

    override fun getItemCount(): Int {
        return categoriesdetails.count()
    }


}
