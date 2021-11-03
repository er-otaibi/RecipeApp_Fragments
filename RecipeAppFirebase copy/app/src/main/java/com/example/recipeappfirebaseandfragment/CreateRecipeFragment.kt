package com.example.recipeappfirebaseandfragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.recipeappfirebase.R


class CreateRecipeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_create, container, false)

        var tvRecipe = view.findViewById<TextView>(R.id.tvRecipe)
        var createBtn = view.findViewById<Button>(R.id.create)

        createBtn.setOnClickListener {

            view?.let { Navigation.findNavController(it).navigate(R.id.action_create2_to_recipeAdd) }
        }

        return view
    }


}