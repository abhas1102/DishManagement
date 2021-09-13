package com.example.goodfood.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.GridLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.goodfood.R
import com.example.goodfood.application.GoodFoodApplication
import com.example.goodfood.databinding.FragmentAllDishesBinding
import com.example.goodfood.view.activities.activities.AddUpdateDishActivity
import com.example.goodfood.view.adapters.GoodFoodAdapter
import com.example.goodfood.viewmodel.GoodFoodViewModel
import com.example.goodfood.viewmodel.GoodFoodViewModelFactory
import com.example.goodfood.viewmodel.HomeViewModel
import kotlin.time.measureTimedValue

class FragmentAllDishes : Fragment() {

    private lateinit var mBinding: FragmentAllDishesBinding

    private val mGoodFoodViewModel:GoodFoodViewModel by viewModels {
        GoodFoodViewModelFactory((requireActivity().application as GoodFoodApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAllDishesBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.rvDishesList.layoutManager=GridLayoutManager(requireActivity(),2)
        val goodFoodAdapter = GoodFoodAdapter(this@FragmentAllDishes)
        mBinding.rvDishesList.adapter = goodFoodAdapter
        mGoodFoodViewModel.allDishesList.observe(viewLifecycleOwner){
            dishes ->
                dishes.let {
                    if(it.isNotEmpty()){
                        mBinding.rvDishesList.visibility = View.VISIBLE
                        mBinding.tvNoDishesAddedYet.visibility = View.GONE

                        goodFoodAdapter.dishesList(it)

                    }else{
                        mBinding.rvDishesList.visibility = View.GONE
                        mBinding.tvNoDishesAddedYet.visibility = View.VISIBLE

                    }
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_all_dishes,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_add_dish ->{
                startActivity(Intent(requireActivity(),AddUpdateDishActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}