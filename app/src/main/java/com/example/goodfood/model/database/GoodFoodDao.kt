package com.example.goodfood.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.goodfood.model.entities.GoodFood
import java.util.concurrent.Flow


@Dao
interface GoodFoodDao {

    @Insert
    suspend fun insertGoodFoodDetails(goodFood:GoodFood)

    @Query("SELECT * FROM GOOD_FOOD_TABLE ORDER BY ID")
    fun getAllDishesList():kotlinx.coroutines.flow.Flow<List<GoodFood>> // To observe the data changes we use flow, flow is an async sequence of values



}