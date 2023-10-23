package com.example.shoppinglistapp.ui.new_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Essa é a tela de Criar Lista"
    }
    val text: LiveData<String> = _text
}