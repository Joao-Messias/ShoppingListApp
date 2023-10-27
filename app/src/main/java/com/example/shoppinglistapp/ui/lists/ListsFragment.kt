package com.example.shoppinglistapp.ui.lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglistapp.data.ShoppingListAdapter
import com.example.shoppinglistapp.databinding.FragmentListsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ListsFragment : Fragment() {

    private var _binding: FragmentListsBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchJob: Job
    private val viewModel: ListsViewModel by viewModels()
    private lateinit var adapter: ShoppingListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearch()
        setupRecyclerView()
        setupDeleteAllButton()
        observeShoppingLists()
    }

    private fun setupSearch() {
        binding.editTextSearch.doAfterTextChanged { text ->
            if (::searchJob.isInitialized) {
                searchJob.cancel()
            }
            searchJob = lifecycleScope.launch {
                delay(300)
                viewModel.searchLists(text.toString())
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = ShoppingListAdapter { shoppingList ->
            val action = ListsFragmentDirections.actionListsFragmentToEditListFragment(shoppingList.id)
            findNavController().navigate(action)
        }
        binding.recyclerViewLists.adapter = adapter
        binding.recyclerViewLists.layoutManager = LinearLayoutManager(requireContext())
    }


    private fun setupDeleteAllButton() {
        binding.buttonDeleteAllLists.setOnClickListener {
            viewModel.deleteAllLists()
        }
    }

    private fun observeShoppingLists() {
        viewModel.shoppingLists.observe(viewLifecycleOwner) { lists ->
            adapter.submitList(lists)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

