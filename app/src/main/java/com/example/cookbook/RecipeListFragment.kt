package com.example.cookbook

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class RecipeListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var sharedPreferences: SharedPreferences
    private var recipeList: MutableList<Recipe> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_list, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        sharedPreferences = requireActivity().getSharedPreferences("recipes", Context.MODE_PRIVATE)
        loadRecipes()

        recyclerView.adapter = RecipeAdapter(recipeList) { recipe ->
            (activity as MainActivity).loadFragment(RecipeDetailsFragment.newInstance(recipe))
        }

        return view
    }

    private fun loadRecipes() {
        val json = sharedPreferences.getString("recipe_list", "[]")
        val type = object : TypeToken<MutableList<Recipe>>() {}.type
        recipeList =  Gson().fromJson(json, type) ?: mutableListOf()
    }
}

