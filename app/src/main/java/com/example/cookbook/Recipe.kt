package com.example.cookbook

data class Recipe(
    var name: String,
    var ingredients: String,
    var instructions: String,
    var rating: Float,
    var imageUri: String?
)