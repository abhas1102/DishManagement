package com.example.goodfood.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.goodfood.databinding.ItemDishLayoutBinding
import com.example.goodfood.model.entities.GoodFood

class GoodFoodAdapter(private val fragment:Fragment):RecyclerView.Adapter<GoodFoodAdapter.ViewHolder>() {

    private var dishes:List<GoodFood> = listOf()

    class ViewHolder(view:ItemDishLayoutBinding):RecyclerView.ViewHolder(view.root){
        val ivDishImage = view.ivDishImage
        val tvTitle = view.tvDishTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding:ItemDishLayoutBinding = ItemDishLayoutBinding.inflate(LayoutInflater.from(fragment.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dish = dishes[position]
        Glide.with(fragment).load(dish.image).into(holder.ivDishImage)
        holder.tvTitle.text=dish.title
    }

    override fun getItemCount(): Int {
       return dishes.size
    }

    fun dishesList(list: List<GoodFood>){
        dishes = list
        notifyDataSetChanged()
    }
}