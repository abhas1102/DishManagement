package com.example.goodfood.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "good_food_table")
data class GoodFood (
    @ColumnInfo val image:String,
    @ColumnInfo val imageSource:String,
    @ColumnInfo val title:String,
    @ColumnInfo val type:String,
    @ColumnInfo val category:String,
    @ColumnInfo val ingredients:String,

    @ColumnInfo(name = "cooking_time") val cookingTime:String,
    @ColumnInfo(name = "instructions") val directionToCook:String,
    @ColumnInfo(name = "favorite_dish") var favoriteDish:Boolean=false,

    @PrimaryKey(autoGenerate = true) val id:Int = 0


        )
