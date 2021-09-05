package com.example.goodfood.view.activities.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.goodfood.R
import com.example.goodfood.databinding.ActivityAddUpdateDishBinding
import com.example.goodfood.databinding.DialogCustomImageSelectionBinding

class AddUpdateDishActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var mBinding: ActivityAddUpdateDishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAddUpdateDishBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setUpActionBar()

        mBinding.ivAddDishImage.setOnClickListener(this)
    }

    private fun setUpActionBar() {
        setSupportActionBar(mBinding.toolbarAddDishActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mBinding.toolbarAddDishActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.iv_add_dish_image -> {
                  customImageSelectionDialog()
                    return
                }
            }
        }


    }

    private fun customImageSelectionDialog(){
        val dialog = Dialog(this)
        val binding:DialogCustomImageSelectionBinding = DialogCustomImageSelectionBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        binding.tvCamera.setOnClickListener {
            Toast.makeText(this,"Camera clicked",Toast.LENGTH_SHORT).show()
            dialog.dismiss()

        }
        binding.tvGallery.setOnClickListener {
            Toast.makeText(this,"Gallery selected",Toast.LENGTH_SHORT).show()
            dialog.dismiss()

        }

        dialog.show() // We use this to dialog to appear

    }
}