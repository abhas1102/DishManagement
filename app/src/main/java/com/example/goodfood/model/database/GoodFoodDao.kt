package com.example.goodfood.model.database

import androidx.room.Dao
import androidx.room.Insert
import com.example.goodfood.model.entities.GoodFood


@Dao
interface GoodFoodDao {

    @Insert
    suspend fun insertGoodFoodDetails(goodFood:GoodFood)
}