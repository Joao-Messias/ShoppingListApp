package com.example.shoppinglistapp

import android.app.Application
import android.content.Context
import com.example.shoppinglistapp.data.Banco
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@HiltAndroidApp
@Module
@InstallIn(SingletonComponent::class)
class ShoppingListApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}

