package com.example.cookbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.mycookbook.Recipe

class RecipeListFragment : Fragment() {

    private lateinit var adapter: RecipeAdapter
    private lateinit var recipes: MutableList<Recipe>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_recipe_list, container, false)

        recipes = SharedPrefManager.getRecipes(requireContext())
        adapter = RecipeAdapter(requireContext(), recipes)

        val listView: ListView = view.findViewById(R.id.recipe_list)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, RecipeDetailsFragment.newInstance(position))
                .addToBackStack(null)
                .commit()
        }

        val addButton: Button = view.findViewById(R.id.add_recipe_button)
        addButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AddRecipeFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}
