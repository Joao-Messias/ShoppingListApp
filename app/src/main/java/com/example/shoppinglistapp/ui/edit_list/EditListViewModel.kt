package com.example.shoppinglistapp.ui.edit_list

import android.util.Log
import androidx.lifecycle.*
import com.example.shoppinglistapp.data.Product
import com.example.shoppinglistapp.data.ShoppingList
import com.example.shoppinglistapp.data.ShoppingListRepositorySQL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditListViewModel @Inject constructor(
    private val repository: ShoppingListRepositorySQL
) : ViewModel() {

    private val _listId = MutableLiveData<Long>()
    private val _listName = MutableLiveData<String>()
    private val _currentProducts = MutableLiveData<List<Product>>()

    val listName: LiveData<String> = _listName
    private val TAG = "EditListViewModel"

    fun updateListName(newName: String) {
        _listName.value = newName
    }

    fun updateProduct(index: Int, product: Product) {
        viewModelScope.launch {
            val currentProducts = _currentProducts.value.orEmpty().toMutableList()
            if (index >= 0 && index < currentProducts.size) {
                currentProducts[index] = product
                _currentProducts.value = currentProducts
            }
        }
    }


    fun saveListChanges() {
        viewModelScope.launch {
            // Obtenha a lista atual
            val currentList = shoppingList.value
            // Obter a lista de produtos atual
            val currentProducts = _currentProducts.value
            if (currentList != null) {
                // Atualize o nome da lista
                currentList.name = _listName.value ?: ""
                // Atualize a lista no repositório
                repository.updateList(currentList)

                currentProducts?.forEach { product ->
                    // Atualize os valores do produto com os valores dos MutableLiveData
                    repository.updateProduct(product)
                    Log.d(TAG, "Atualizando produto NOME - ${product.name}")
                    Log.d(TAG, "Atualizando produto CHECK - ${product.checked}")
                    Log.d(TAG, "Atualizando produto QNTD - ${product.quantity}")
                }
            }
        }
    }

    val shoppingList: LiveData<ShoppingList> = _listId.switchMap { id ->
        repository.getListById(id).asLiveData()
    }

    val products: LiveData<List<Product>> = _listId.switchMap { id ->
        liveData {
            emitSource(repository.getProductsByListId(id).asLiveData())
        }
    }

    fun start(listId: Long) {
        _listId.value = listId
        viewModelScope.launch {
            val products = repository.getProductsByListId(listId).firstOrNull()
            _currentProducts.value = products
        }
    }

    fun deleteList() {
        viewModelScope.launch {
            val currentList = shoppingList.value
            if (currentList != null) {
                repository.deleteList(currentList)
            }
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            repository.deleteProduct(product)

            val currentProducts = _currentProducts.value.orEmpty().toMutableList()
            currentProducts.remove(product)
            _currentProducts.value = currentProducts
        }
    }

}
