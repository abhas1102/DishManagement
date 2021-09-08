package com.example.goodfood.model.database

import androidx.annotation.WorkerThread
import com.example.goodfood.model.entities.GoodFood

class GoodFoodRepository(private val goodFoodDao: GoodFoodDao) {

    @WorkerThread
    suspend fun insertGoodFoodData(goodFood:GoodFood){
        goodFoodDao.insertGoodFoodDetails(goodFood)
    }
}