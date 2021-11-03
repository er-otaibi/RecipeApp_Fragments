package com.example.recipeappfirebaseandfragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel(application: Application): AndroidViewModel(application) {

    private val recipes: MutableLiveData<List<Recipe>> = MutableLiveData()
    private var db: FirebaseFirestore = Firebase.firestore


    fun getRecipes(): LiveData<List<Recipe>> {
        return recipes
    }

    fun getData(){

        db.collection("recipes")
            .get()
            .addOnSuccessListener { result ->
                var recipeList = arrayListOf<Recipe>()

                for (document in result) {
                    var title =""
                    var author =""
                    var ingredients = ""
                    var instructions =""

                    document.data.map { (key, value)
                        ->
                        when (key) {
                            "title" -> title = value as String
                            "author" -> author = value as String
                            "ingredients" -> ingredients = value as String
                            "instructions" -> instructions = value as String
                        }
                    }
                    recipeList.add(Recipe(document.id,title,author,ingredients, instructions))

                }
                recipes.value = recipeList
            }
            .addOnFailureListener { exception ->
                Log.w("MainActivity", "Error getting documents.", exception)
            }
    }


    fun addRecipe( recipe: Recipe){
        CoroutineScope(Dispatchers.IO).launch {
            val newRecipe = hashMapOf(
                "title" to recipe.title,
                "author" to recipe.author,
                "ingredients" to recipe.ingredients,
                "instructions" to recipe.instructions
            )
            db.collection("recipes")
                .add(newRecipe)
            getData()
        }
    }

    fun editRecipe(recipeID: String, title: String, author: String, ingredients: String, instructions: String){
        CoroutineScope(Dispatchers.IO).launch {
            db.collection("recipes").document(recipeID)
                .update(mapOf(
                    "title" to title,
                    "author" to author,
                    "ingredients" to ingredients,
                    "instructions" to instructions
                ))
            getData()
        }
    }

    fun deleteRecipe(recipeID: String){
        CoroutineScope(Dispatchers.IO).launch {
            db.collection("recipes")
                .document(recipeID)
                .delete()
            getData()

        }
    }
}