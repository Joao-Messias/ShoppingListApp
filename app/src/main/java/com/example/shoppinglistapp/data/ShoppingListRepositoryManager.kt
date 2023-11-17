package com.example.shoppinglistapp.data

import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class ShoppingListRepositoryManager @Inject constructor(
    private val localRepository: ShoppingListRepositorySQL,
    val firebaseRepository: ShoppingListRepositoryFirebase,
) : ShoppingListRepository {

    fun getAllShoppingLists(): Flow<List<ShoppingList>> {
        val localLists = localRepository.getAllShoppingLists()
        val firebaseLists = firebaseRepository.getAllShoppingLists()
        return localLists.combine(firebaseLists) { local, firebase ->
            val firebaseIds = firebase.map { it.firestoreId }
            local.filter { it.firestoreId !in firebaseIds } + firebase
        }
    }

    suspend fun deleteAllShoppingLists() {
        localRepository.deleteAllShoppingLists()
        firebaseRepository.deleteAllShoppingLists()
    }

    suspend fun insertShoppingListWithProducts(
        shoppingList: ShoppingList,
        products: List<Product>
    ) {
        val id = localRepository.insertShoppingListWithProducts(shoppingList, products)
        firebaseRepository.insertShoppingListWithProducts(shoppingList, products, id)
    }

    suspend fun updateProduct(product: Product) {
        localRepository.updateProduct(product)
        firebaseRepository.updateProduct(product)
    }

    suspend fun updateList(currentList: ShoppingList) {
        localRepository.updateList(currentList)
        firebaseRepository.update(currentList)
    }

    suspend fun deleteList(currentList: ShoppingList) {
        localRepository.deleteList(currentList)
        firebaseRepository.deleteList(currentList)
    }

    suspend fun deleteProduct(product: Product) {
        localRepository.deleteProduct(product)
        firebaseRepository.deleteProduct(product)
    }

    fun getListById(listId: Long): Flow<ShoppingList?> {
        val localList = localRepository.getListById(listId)
        val firebaseList = firebaseRepository.getListById(ShoppingList(firestoreId = listId.toString()))
        return localList.combine(firebaseList) { local, firebase ->
            firebase ?: local
        }
    }

    fun getProductsByListId(listId: Long): Flow<List<Product>> {
        val localProducts = localRepository.getProductsByListId(listId)
        val firebaseProducts = firebaseRepository.getProductsByListId(ShoppingList(firestoreId = listId.toString()))
        return localProducts.combine(firebaseProducts) { local, firebase ->
            (local + firebase).distinctBy { it.id }
        }
    }


}
