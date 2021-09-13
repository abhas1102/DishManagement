package com.example.goodfood.application

import android.app.Application
import com.example.goodfood.model.database.GoodFoodRepository
import com.example.goodfood.model.database.GoodFoodRoomDatabase

class GoodFoodApplication:Application() {

    private val database by lazy { GoodFoodRoomDatabase.getDatabase(this@GoodFoodApplication) }

    val repository by lazy { GoodFoodRepository(database.goodFoodDao()) }
}