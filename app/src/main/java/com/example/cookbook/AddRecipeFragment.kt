package com.example.cookbook

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AddRecipeFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_recipe, container, false)

        val nameEditText = view.findViewById<EditText>(R.id.editTextName)
        val ingredientsEditText = view.findViewById<EditText>(R.id.editTextIngredients)
        val instructionsEditText = view.findViewById<EditText>(R.id.editTextInstructions)
        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)
        val saveButton = view.findViewById<Button>(R.id.buttonSave)

        sharedPreferences = requireActivity().getSharedPreferences("recipes", Context.MODE_PRIVATE)

        saveButton.setOnClickListener {
            val recipe = Recipe(
                nameEditText.text.toString(),
                ingredientsEditText.text.toString(),
                instructionsEditText.text.toString(),
                ratingBar.rating,
                null
            )
            saveRecipe(recipe)
            (activity as MainActivity).loadFragment(RecipeListFragment()
        }

        return view
    }

    private fun saveRecipe(recipe: Recipe) {
        val json = sharedPreferences.getString("recipe_list", "[]")
        val type = object : TypeToken<MutableList<Recipe>>() {}.type
        val recipeList: MutableList<Recipe> = Gson().fromJson(json, type) ?: mutableListOf()

        recipeList.add(recipe)
        sharedPreferences.edit().putString("recipe_list", Gson().toJson(recipeList)).apply()
    }
}