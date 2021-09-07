package com.example.goodfood.view.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.goodfood.databinding.ItemCustomListBinding

class CustomListItemAdapter(private val activity:Activity,
                            private val listItems:List<String>,
                            private val seletion:String)
    : RecyclerView.Adapter<CustomListItemAdapter.ViewHolder>(){

        class ViewHolder(view: ItemCustomListBinding):RecyclerView.ViewHolder(view.root){ // viewholder describes an item view and metadata about it's place in recycler view
            val tvText = view.tvText
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { // Created binding object so that now we can use it in activity and inflate the whole view
       val binding:ItemCustomListBinding = ItemCustomListBinding.inflate(LayoutInflater.from(activity),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { //How does every item is going to look like, bind views with position
        val item = listItems[position]
        holder.tvText.text = item

    }

    override fun getItemCount(): Int {
        return listItems.size
    }


}