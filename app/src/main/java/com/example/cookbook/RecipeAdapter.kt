package com.example.cookbook

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RatingBar
import android.widget.TextView
import com.example.mycookbook.Recipe

class RecipeAdapter(private val context: Context, private val recipes: List<Recipe>) : BaseAdapter() {

    override fun getCount(): Int = recipes.size

    override fun getItem(position: Int): Any = recipes[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.fragment_recipe_list, parent, false)

        val name = view.findViewById<TextView>(R.id.recipe_name)
        val ratingBar = view.findViewById<RatingBar>(R.id.recipe_rating_bar)

        val recipe = recipes[position]
        name.text = recipe.name
        ratingBar.rating = recipe.rating

        return view
    }
}
