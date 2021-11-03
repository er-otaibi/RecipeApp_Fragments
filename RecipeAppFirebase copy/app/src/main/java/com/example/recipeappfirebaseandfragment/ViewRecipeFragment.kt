package com.example.recipeappfirebaseandfragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeappfirebase.R


class ViewRecipeFragment : Fragment() {

    private val myViewModel by lazy{ ViewModelProvider(this).get(MyViewModel::class.java)}
    lateinit var rvMain : RecyclerView
    private lateinit var rvAdapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_view_recipe, container, false)

        rvMain = view.findViewById(R.id.rvMain)

        myViewModel.getData()

        myViewModel.getRecipes().observe(viewLifecycleOwner, {

                recipes -> rvAdapter.update(recipes)
        })

        rvAdapter = RecipeAdapter(this)
        rvMain.adapter = rvAdapter
        rvMain.layoutManager = LinearLayoutManager(activity)

        return view
    }


    fun editAlert(idRecipe: String , title: String, author: String, ingredients: String,instructions: String) {

        val builder = AlertDialog.Builder(activity)

        val view = layoutInflater.inflate(R.layout.edit_alert, null)

        builder.setView(view)

        val alertDialog: AlertDialog = builder.create()

        val etTitle = view.findViewById<EditText>(R.id.etTitle)
        val etAuthor = view.findViewById<EditText>(R.id.etAuthor)
        val etIngredients = view.findViewById<EditText>(R.id.etIngredients)
        val etInstructions = view.findViewById<EditText>(R.id.etInstructions)
        val edit = view.findViewById<Button>(R.id.edit)

        etTitle.setText(title)
        etAuthor.setText(author)
        etIngredients.setText(ingredients)
        etInstructions.setText(instructions)

        alertDialog.show()


        edit.setOnClickListener {
            var utitle = etTitle.text.toString()
            var uauthor = etAuthor.text.toString()
            var uingredients = etIngredients.text.toString()
            var uinstructions = etInstructions.text.toString()

            myViewModel.editRecipe(idRecipe,utitle,uauthor,uingredients,uinstructions)

            alertDialog.dismiss()

        }

    }

    fun deleteAlert( id: String){
        val dialogBuilder = activity?.let { androidx.appcompat.app.AlertDialog.Builder(it) }


        dialogBuilder?.setMessage("Confirm delete ?")
            ?.setPositiveButton("Delete", DialogInterface.OnClickListener { _, _ ->

                myViewModel.deleteRecipe(id)
            })?.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ -> dialog.cancel()
        })
        val alert = dialogBuilder?.create()

        alert?.setTitle("Delete Alert")
        alert?.show()


    }
}