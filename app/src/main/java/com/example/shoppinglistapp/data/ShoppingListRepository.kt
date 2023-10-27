package com.example.shoppinglistapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class ShoppingListRepository @Inject constructor(private val shoppingListDao: ShoppingListDao, private val productDao: ProductDao) {
    suspend fun insertShoppingListWithProducts(
        shoppingList: ShoppingList,
        products: List<Product>
    ) {
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

    fun getListById(listId: Long): Flow<ShoppingList> = flow {
        emit(shoppingListDao.getById(listId))
    }

    fun getProductsByListId(listId: Long): Flow<List<Product>> {
        return productDao.getProductsByListId(listId)
    }

    suspend fun updateProduct(product: Product) {
        productDao.update(product)
    }

    suspend fun updateList(currentList: ShoppingList) {
        shoppingListDao.update(currentList)
    }

    suspend fun deleteList(currentList: ShoppingList) {
        shoppingListDao.delete(currentList.id)
    }

    suspend fun deleteProduct(product: Product) {
        productDao.delete(product.id)
    }

}

