package com.example.recipeappfirebaseandfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeappfirebase.R
import kotlinx.android.synthetic.main.recipe_row.view.*

class RecipeAdapter(private val activity: ViewRecipeFragment):  RecyclerView.Adapter<RecipeAdapter.ItemViewHolder>(){

    private var myRecipe = emptyList<Recipe>()

    class ItemViewHolder (itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recipe_row,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var recipe = myRecipe[position]

        holder.itemView.apply {
            tvTitle.text = "Title: ${recipe.title}"
            tvAuthor.text = "Author: ${recipe.author}"
            ingredients2.isVisible = false
            instructions2.isVisible = false
            button.isVisible = false
            cvCard.setOnClickListener {

                ingredients2.isVisible = true
                instructions2.isVisible = true
                button.isVisible = true
                ingredients2.text = "Ingredients: ${recipe.ingredients}"
                instructions2.text = "Instructions: ${recipe.instructions}"
                button.setOnClickListener {
                    ingredients2.isVisible = false
                    instructions2.isVisible = false
                    button.isVisible = false
                }
            }
            editBtn.setOnClickListener {

                var title = recipe.title
                var author = recipe.author
                var ingredients = recipe.ingredients
                var instructions = recipe.instructions
                activity.editAlert(recipe.id, title, author, ingredients, instructions)
            }
            deleteBtn.setOnClickListener {
                activity.deleteAlert(recipe.id)
            }
        }
    }

    override fun getItemCount() = myRecipe.size

    fun update(recipe: List<Recipe>){

        this.myRecipe = recipe
        notifyDataSetChanged()
    }
}