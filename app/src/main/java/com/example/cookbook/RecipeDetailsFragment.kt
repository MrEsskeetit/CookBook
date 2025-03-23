package com.example.cookbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment

class RecipeDetailsFragment : Fragment() {

    private var position: Int = 0

    companion object {
        private const val ARG_POSITION = "position"

        fun newInstance(position: Int): RecipeDetailsFragment {
            return RecipeDetailsFragment().apply {
                arguments = Bundle().apply { putInt(ARG_POSITION, position) }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        position = arguments?.getInt(ARG_POSITION) ?: 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_recipe_details, container, false)
        val recipes = SharedPrefManager.getRecipes(requireContext())
        val recipe = recipes[position]

        view.findViewById<TextView>(R.id.recipe_name).text = recipe.name
        view.findViewById<TextView>(R.id.recipe_ingredients).text = recipe.ingredients
        view.findViewById<TextView>(R.id.recipe_instructions).text = recipe.instructions

        val ratingBar = view.findViewById<RatingBar>(R.id.recipe_rating_bar)
        ratingBar.rating = recipe.rating

        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            recipes[position].rating = rating
            SharedPrefManager.saveRecipes(requireContext(), recipes)
        }

        return view
    }
}
