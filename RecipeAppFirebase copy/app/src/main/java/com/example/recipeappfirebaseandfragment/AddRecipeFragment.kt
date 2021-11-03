package com.example.recipeappfirebaseandfragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.recipeappfirebase.R


class AddRecipeFragment : Fragment() {
    private val myViewModel by lazy{ ViewModelProvider(this).get(MyViewModel::class.java)}

    private lateinit var etTitle: EditText
    lateinit var etAuthor: EditText
    lateinit var etIngredients: EditText
    lateinit var etInstructions: EditText
    lateinit var saveBtn: Button
    lateinit var viewBtn: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_recipe_add, container, false)

        etTitle = view.findViewById(R.id.etTitle)
        etAuthor = view.findViewById(R.id.etAuthor)
        etIngredients = view.findViewById(R.id.etIngredients)
        etInstructions = view.findViewById(R.id.etInstructions)
        viewBtn = view.findViewById(R.id.viewBtn)
        saveBtn = view.findViewById(R.id.saveBtn)



        viewBtn.setOnClickListener {
            view?.let { Navigation.findNavController(it).navigate(R.id.action_recipeAdd_to_viewRecipe) }
        }

        saveBtn.setOnClickListener {
            var title = etTitle.text.toString()
            var author = etAuthor.text.toString()
            var ingredients = etIngredients.text.toString()
            var instructions = etInstructions.text.toString()
            if (title.isNotEmpty() && author.isNotEmpty() && ingredients.isNotEmpty() && instructions.isNotEmpty()) {
                myViewModel.addRecipe(Recipe("", title, author, ingredients, instructions))

                Toast.makeText(activity, "New Recipe is added to your list!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(activity, "Please Enter a Full Recipe", Toast.LENGTH_LONG).show()
            }
        }
            clearText()

            return view
        }


        private fun clearText() {
            etTitle.setText("")
            etAuthor.setText("")
            etIngredients.setText("")
            etInstructions.setText("")
        }

    }
