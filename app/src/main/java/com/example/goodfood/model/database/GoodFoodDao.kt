package com.example.goodfood.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.goodfood.model.entities.GoodFood
import kotlinx.coroutines.flow.Flow


@Dao
interface GoodFoodDao {

    @Insert
    suspend fun insertGoodFoodDetails(goodFood: GoodFood)

    @Query("SELECT * FROM good_food_table ORDER BY ID")
    fun getAllDishesList(): Flow<List<GoodFood>> // To observe the data changes we use flow, flow is an async sequence of values



}