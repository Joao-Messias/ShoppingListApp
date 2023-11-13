package com.example.shoppinglistapp.ui.lists

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.shoppinglistapp.data.ShoppingList
import com.example.shoppinglistapp.data.ShoppingListRepositorySQL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(
    private val repository: ShoppingListRepositorySQL
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun searchLists(query: String) {
        _searchQuery.value = query
    }

    val shoppingLists: LiveData<List<ShoppingList>> = repository.getAllShoppingLists()
        .combine(searchQuery) { lists, query ->
            if (query.isBlank()) lists
            else lists.filter { it.name.contains(query, ignoreCase = true) }
        }
        .asLiveData()

    fun deleteAllLists() = viewModelScope.launch {
        repository.deleteAllShoppingLists()
    }
}
