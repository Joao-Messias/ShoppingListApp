package com.example.shoppinglistapp.data

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


class ShoppingListRepositoryFirebase @Inject constructor(
    private val shoppingListsRef: CollectionReference,
    private val productsRef: CollectionReference

) {
    private var _shoppingLists = MutableStateFlow(listOf<ShoppingList>())
    val shoppingLists: Flow<List<ShoppingList>>
        get() = _shoppingLists.asStateFlow()

    init {
        shoppingListsRef.addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                val lists = snapshot.documents.mapNotNull { it.toObject<ShoppingList>() }
                _shoppingLists.value = lists
            }
        }
    }

    suspend fun saveProduct(product: Product) {
        if (product.firestoreId.isNullOrEmpty()) {
            val doc = productsRef.document()
            product.firestoreId = doc.id
            doc.set(product)
        } else {
            productsRef.document(product.firestoreId!!).set(product)
        }
    }

//    fun getAllShoppingLists(): Flow<List<ShoppingList>> {
//        // Implemente a lógica para buscar todas as listas de compras do Firestore
//    }
    suspend fun saveShoppingList(shoppingList: ShoppingList) {
        if (shoppingList.firestoreId.isNullOrEmpty()) {
            val doc = shoppingListsRef.document()
            shoppingList.firestoreId = doc.id
            doc.set(shoppingList)
        } else {
            shoppingListsRef.document(shoppingList.firestoreId!!).set(shoppingList)
        }
    }

    suspend fun deleteShoppingList(shoppingList: ShoppingList) {
        if (!shoppingList.firestoreId.isNullOrEmpty()) {
            shoppingListsRef.document(shoppingList.firestoreId!!).delete()
        }
    }

}

