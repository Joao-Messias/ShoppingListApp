package com.example.shoppinglistapp.data

import kotlinx.coroutines.flow.Flow


class ShoppingListRepository(private val shoppingListDao: ShoppingListDao, private val productDao: ProductDao) {
    suspend fun insertShoppingListWithProducts(shoppingList: ShoppingList, products: List<Product>) {
        val listId = shoppingListDao.insert(shoppingList)
        products.forEach { product ->
            product.listId = listId
            productDao.insert(product)
        }
    }
    fun getAllShoppingLists(): Flow<List<ShoppingList>> = shoppingListDao.getAll()

    suspend fun deleteAllShoppingLists() {
        shoppingListDao.deleteAll()
    }
}

