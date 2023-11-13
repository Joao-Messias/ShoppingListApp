package com.example.shoppinglistapp.data

import javax.inject.Inject

class ShoppingListRepositoryManager @Inject constructor(
    private val localRepository: ShoppingListRepositorySQL,
    private val firebaseRepository: ShoppingListRepositoryFirebase
) : ShoppingListRepository {

    suspend fun insertShoppingListWithProducts(shoppingList: ShoppingList, products: List<Product>) {
        localRepository.insertShoppingListWithProducts(shoppingList, products)
        if (isNetworkAvailable()) {
            firebaseRepository.saveShoppingList(shoppingList)
            products.forEach { firebaseRepository.saveProduct(it) }
        }
    }

    // Implemente os outros métodos da interface ShoppingListRepository aqui

    private fun isNetworkAvailable(): Boolean {
        // Implemente a lógica para verificar a disponibilidade da rede
        return true
    }
}
