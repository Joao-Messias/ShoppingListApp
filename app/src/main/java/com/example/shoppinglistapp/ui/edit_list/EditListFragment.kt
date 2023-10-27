package com.example.shoppinglistapp.ui.edit_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.data.EditListAdapter
import com.example.shoppinglistapp.data.Product
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditListFragment : Fragment() {
    private val viewModel: EditListViewModel by viewModels()
    private val args: EditListFragmentArgs by navArgs()
    private lateinit var adapter: EditListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_edit_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = EditListAdapter(viewModel)
        val listId = args.listId
        viewModel.start(args.listId)

        // Vincule o RecyclerView ao Adapter
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewProducts)
        recyclerView.adapter = adapter

        viewModel.shoppingList.observe(viewLifecycleOwner) { shoppingList ->
            val listNameEditText = view.findViewById<EditText>(R.id.editTextListName)
            listNameEditText.setText(shoppingList?.name ?: "")
        }

        viewModel.products.observe(viewLifecycleOwner) { products ->
            adapter.submitList(products)
        }

        val saveChangesButton = view.findViewById<Button>(R.id.buttonSaveChanges)
        saveChangesButton.setOnClickListener {
            val listNameEditText = view.findViewById<EditText>(R.id.editTextListName)
            viewModel.updateListName(listNameEditText.text.toString())
            viewModel.saveListChanges()
        }


        val deleteListButton = view.findViewById<Button>(R.id.buttonDeleteList)
        deleteListButton.setOnClickListener {
            viewModel.deleteList()
        }
    }
}

