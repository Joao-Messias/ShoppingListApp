package com.example.shoppinglistapp.ui.lists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Tela de Listagem de Lista de Compras"
    }
    val text: LiveData<String> = _text
}