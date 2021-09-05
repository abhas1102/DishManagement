package com.example.goodfood.view.activities.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.goodfood.R
import com.example.goodfood.databinding.ActivityAddUpdateDishBinding

class AddUpdateDishActivity : AppCompatActivity() {
    private lateinit var mBinding:ActivityAddUpdateDishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAddUpdateDishBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setUpActionBar()
    }

    private fun setUpActionBar(){
        setSupportActionBar(mBinding.toolbarAddDishActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mBinding.toolbarAddDishActivity.setNavigationOnClickListener {
            onBackPressed()
        }

    }
}