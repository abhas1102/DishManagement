package com.example.goodfood.model.database

import androidx.annotation.WorkerThread
import com.example.goodfood.model.entities.GoodFood
import kotlinx.coroutines.flow.Flow

class GoodFoodRepository(private val goodFoodDao: GoodFoodDao) {

    @WorkerThread
    suspend fun insertGoodFoodData(goodFood:GoodFood){
        goodFoodDao.insertGoodFoodDetails(goodFood)
    }

    val allDishesList: Flow<List<GoodFood>> = goodFoodDao.getAllDishesList()
}