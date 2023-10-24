package com.example.shoppinglistapp.ui.new_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglistapp.data.Product
import com.example.shoppinglistapp.data.ShoppingList
import com.example.shoppinglistapp.data.ShoppingListRepository
import kotlinx.coroutines.launch

class NewListViewModel(private val repository: ShoppingListRepository) : ViewModel() {
    private val _produtos = mutableListOf<Product>()
    val produtos: List<Product> get() = _produtos

    fun adicionarProduto(produto: Product) {
        _produtos.add(produto)
    }

    fun salvarLista(nomeDaLista: String) = viewModelScope.launch {
        val novaLista = ShoppingList(name = nomeDaLista)
        repository.insertShoppingListWithProducts(novaLista, _produtos)
        _produtos.clear()
    }
}
