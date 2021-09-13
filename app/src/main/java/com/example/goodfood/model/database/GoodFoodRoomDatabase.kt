package com.example.goodfood.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.goodfood.model.entities.GoodFood


@Database(entities = [GoodFood::class],version = 1,)
abstract class GoodFoodRoomDatabase:RoomDatabase() {

    abstract fun goodFoodDao():GoodFoodDao

    companion object{
        @Volatile
        private var INSTANCE: GoodFoodRoomDatabase? = null

        fun getDatabase(context: Context): GoodFoodRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GoodFoodRoomDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}