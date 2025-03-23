package com.example.cookbook

import android.content.Context
import android.content.SharedPreferences

class RecipeManager(context: Context) {
    companion object {
        private const val PREFS_NAME = "CookBookPrefs"
        private const val RECIPES_KEY = "Recipes"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveRecipes(recipes: List<Recipe>) {
        val editor = sharedPreferences.edit()
        val recipeSet = recipes.map { it.toString() }.toSet()
        editor.putStringSet(RECIPES_KEY, recipeSet)
        editor.apply()
    }

    fun loadRecipes(): List<Recipe> {
        val recipeSet = sharedPreferences.getStringSet(RECIPES_KEY, emptySet()) ?: emptySet()
        return recipeSet.mapNotNull { recipeString ->
            val parts = recipeString.split("|")
            if (parts.size == 4) {
                Recipe(parts[0], parts[1], parts[2], parts[3].toFloat())
            } else null
        }
    }
}

data class Recipe(
    val name: String,
    val ingredients: String,
    val instructions: String,
    var rating: Float
) {
    override fun toString(): String {
        return "$name|$ingredients|$instructions|$rating"
    }
}
