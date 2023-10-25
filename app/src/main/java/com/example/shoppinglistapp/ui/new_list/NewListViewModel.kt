package com.example.shoppinglistapp.ui.new_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglistapp.data.Product
import com.example.shoppinglistapp.data.ShoppingList
import com.example.shoppinglistapp.data.ShoppingListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewListViewModel @Inject constructor(
    private val repository: ShoppingListRepository
) : ViewModel() {
    private val _produtos = MutableStateFlow<List<Product>>(emptyList())

    val produtos = _produtos.asStateFlow()

    private val _listaSalvaComSucesso = MutableLiveData<Boolean>()
    val listaSalvaComSucesso: LiveData<Boolean> = _listaSalvaComSucesso

    fun adicionarProduto(produto: Product) {
        Log.d("NewListViewModel", "adicionarProduto chamado com: $produto")
        val novaLista = _produtos.value.toMutableList().apply {
            add(produto)
        }
        _produtos.value = novaLista
        Log.d("NewListViewModel", "Produtos após adição: ${_produtos.value}")
    }


    fun salvarLista(nomeDaLista: String) = viewModelScope.launch {
        val novaLista = ShoppingList(name = nomeDaLista)
        repository.insertShoppingListWithProducts(novaLista, _produtos.value)
        _produtos.value = emptyList() // Limpa a lista após salvar
        _listaSalvaComSucesso.value = true
    }

    fun resetListaSalvaComSucesso() {
        _listaSalvaComSucesso.value = false
    }

}
