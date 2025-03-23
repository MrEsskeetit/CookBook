package com.example.cookbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import androidx.fragment.app.Fragment
import com.example.mycookbook.Recipe

class AddRecipeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_add_recipe, container, false)

        val nameInput = view.findViewById<EditText>(R.id.recipe_name)
        val ingredientsInput = view.findViewById<EditText>(R.id.recipe_ingredients)
        val instructionsInput = view.findViewById<EditText>(R.id.recipe_instructions)
        val ratingBar = view.findViewById<RatingBar>(R.id.recipe_rating_bar)
        val saveButton = view.findViewById<Button>(R.id.save_recipe_button)

        saveButton.setOnClickListener {
            val newRecipe = Recipe(
                nameInput.text.toString(),
                ingredientsInput.text.toString(),
                instructionsInput.text.toString(),
                ratingBar.rating
            )

            val recipes = SharedPrefManager.getRecipes(requireContext())
            recipes.add(newRecipe)
            SharedPrefManager.saveRecipes(requireContext(), recipes)

            parentFragmentManager.popBackStack()
        }

        return view
    }
}
