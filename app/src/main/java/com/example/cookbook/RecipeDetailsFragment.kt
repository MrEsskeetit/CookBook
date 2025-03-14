package com.example.cookbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipeDetailsFragment : Fragment() {
    private var recipe: Recipe? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipe = Gson().fromJson(it.getString("recipe"), Recipe::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_details, container, false)

        val nameTextView = view.findViewById<TextView>(R.id.textViewName)
        val ingredientsTextView = view.findViewById<TextView>(R.id.textViewIngredients)
        val instructionsTextView = view.findViewById<TextView>(R.id.textViewInstructions)
        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)

        recipe?.let {
            nameTextView.text = it.name
            ingredientsTextView.text = it.ingredients
            instructionsTextView.text = it.instructions
            ratingBar.rating = it.rating
        }

        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            recipe?.rating = rating
            saveUpdatedRecipe()
        }

        return view
    }

    private fun saveUpdatedRecipe() {
        val sharedPreferences = requireActivity().getSharedPreferences("recipes", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("recipe_list", "[]")
        val type = object : TypeToken<MutableList<Recipe>>() {}.type
        val recipeList: MutableList<Recipe> = Gson().fromJson(json, type) ?: mutableListOf()

        recipeList.find { it.name == recipe?.name }?.rating = recipe?.rating ?: 0f
        sharedPreferences.edit().putString("recipe_list", Gson().toJson(recipeList)).apply()
    }

    companion object {
        fun newInstance(recipe: Recipe): RecipeDetailsFragment {
            val fragment = RecipeDetailsFragment()
            val args = Bundle()
            args.putString("recipe", Gson().toJson(recipe))
            fragment.arguments = args
            return fragment
        }
    }
}