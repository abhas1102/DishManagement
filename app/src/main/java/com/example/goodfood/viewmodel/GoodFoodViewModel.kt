package com.example.goodfood.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.goodfood.model.database.GoodFoodRepository
import com.example.goodfood.model.entities.GoodFood
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

//Provide data to UI and survive configuration changes
class GoodFoodViewModel(private val repository: GoodFoodRepository):ViewModel() {

    fun insert(dish:GoodFood) = viewModelScope.launch {
        repository.insertGoodFoodData(dish)
    }

}

class GoodFoodViewModelFactory(private val repository: GoodFoodRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GoodFoodViewModel::class.java)){
            return GoodFoodViewModel(repository) as T

        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}