package com.example.shoppinglistapp.data
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
interface ShoppingListRepository {

    interface ShoppingListRepository {
        suspend fun insertShoppingListWithProducts(shoppingList: ShoppingList, products: List<Product>)
        fun getAllShoppingLists(): Flow<List<ShoppingList>>
        suspend fun deleteAllShoppingLists()
        fun getListById(listId: Long): Flow<ShoppingList>
        fun getProductsByListId(listId: Long): Flow<List<Product>>
        suspend fun updateProduct(product: Product)
        suspend fun updateList(currentList: ShoppingList)
        suspend fun deleteList(currentList: ShoppingList)
        suspend fun deleteProduct(product: Product)
    }
}