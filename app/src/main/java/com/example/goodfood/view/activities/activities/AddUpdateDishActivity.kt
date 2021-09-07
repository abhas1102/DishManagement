package com.example.goodfood.view.activities.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.goodfood.R
import com.example.goodfood.databinding.ActivityAddUpdateDishBinding
import com.example.goodfood.databinding.DialogCustomImageSelectionBinding
import com.karumi.dexter.Dexter
import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.goodfood.databinding.DialogCustomListBinding
import com.example.goodfood.view.adapters.CustomListItemAdapter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class AddUpdateDishActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var mBinding: ActivityAddUpdateDishBinding
    private var mImagePath:String = ""

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
          //  Toast.makeText(this,"Camera clicked",Toast.LENGTH_SHORT).show()
            Dexter.withContext(this).withPermissions(  // Declaration for taking multiple permissions
                Manifest.permission.READ_EXTERNAL_STORAGE,
              //  Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ).withListener(object:MultiplePermissionsListener{
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()){
                           // Toast.makeText(this@AddUpdateDishActivity,"Camera permission now",Toast.LENGTH_SHORT).show()
                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            startActivityForResult(intent,CAMERA)
                        }
                    }

                }

                override fun onPermissionRationaleShouldBeShown(  // This is for the case when user denied the permission
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    showRationalDialogForPermissions()
                }

            }).onSameThread().check() // These all permissions grant process should be run on same thread

            dialog.dismiss()

        }
        binding.tvGallery.setOnClickListener {
          //  Toast.makeText(this,"Gallery selected",Toast.LENGTH_SHORT).show()
            Dexter.withContext(this).withPermission(  // Declaration for taking multiple permissions
                Manifest.permission.READ_EXTERNAL_STORAGE,
              //  Manifest.permission.WRITE_EXTERNAL_STORAGE,

            ).withListener(object:PermissionListener{
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                  //  Toast.makeText(this@AddUpdateDishActivity,"Gallery permission now",Toast.LENGTH_SHORT).show()
                    val galleryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(galleryIntent, GALLERY)

                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText(this@AddUpdateDishActivity,"Denied Gallery permission",Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    showRationalDialogForPermissions()
                }
                /*   override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                       if (report!!.areAllPermissionsGranted()){
                           Toast.makeText(this@AddUpdateDishActivity,"Gallery permission now",Toast.LENGTH_SHORT).show()

                       }
                   }

                   override fun onPermissionRationaleShouldBeShown(  // This is for the case when user denied the permission
                       permissions: MutableList<PermissionRequest>?,
                       token: PermissionToken?
                   ) {
                       showRationalDialogForPermissions()
                   } */

            }).onSameThread().check() // These all permissions grant process should be run on same thread

            dialog.dismiss()

        }

        dialog.show() // We use this to dialog to appear

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == CAMERA){
                data?.extras?.let {
                    val thumbnail:Bitmap = data.extras!!.get("data") as Bitmap
                   // mBinding.ivDishImage.setImageBitmap(thumbnail)
                    Glide.with(this).load(thumbnail).centerCrop().into(mBinding.ivDishImage)
                    mImagePath = saveImageToInternalStorage(thumbnail) // Saved captured image to this path in device
                    Log.e("ImagePath", mImagePath)

                    mBinding.ivDishImage.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_vector_edit))
                }

            }

           else if (requestCode == GALLERY){
                data?.let {
                    val selectedPhotoUri = data.data // it contains the data property which is a Uri
                   // mBinding.ivDishImage.setImageURI(selectedPhotoUri)
                    Glide.with(this).load(selectedPhotoUri).centerCrop().diskCacheStrategy(
                        DiskCacheStrategy.ALL).listener(object :RequestListener<Drawable>{
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            Log.e("TAG","error loading image",e)
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            resource?.let{
                                val bitmap:Bitmap = resource.toBitmap()
                                mImagePath = saveImageToInternalStorage(bitmap)
                                Log.i("ImagePath",mImagePath)

                            }
                            return false

                        }

                    }).into(mBinding.ivDishImage)

                    mBinding.ivDishImage.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_vector_edit))
                }

            }
        } else if(resultCode==Activity.RESULT_CANCELED){
            Log.e("cancelled","Cancelled image selection")
        }
    }

    private fun showRationalDialogForPermissions(){
        AlertDialog.Builder(this).setMessage("It looks like you have turned off permissions" +
                "required for this feature. It can be enabled under application settings")
            .setPositiveButton("GO TO SETTINGS") // If user wants to give permissions
            {_,_ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package",packageName,null)
                    intent.data = uri
                    startActivity(intent)
                }catch (e:ActivityNotFoundException){
                    e.printStackTrace()
                }

            }
            .setNegativeButton("Cancel"){  // If user doesn't want to give permissions
                dialog,_ ->
                dialog.dismiss()
            }.show()
    }

    private fun saveImageToInternalStorage(bitmap: Bitmap):String{
        val wrapper = ContextWrapper(applicationContext) // Which application the bitmap we are trying to assign is stored

        var file = wrapper.getDir(IMAGE_DIRECTORY,Context.MODE_PRIVATE) // We will assign the directory in which we want to store & This will accessible by our application
        file = File(file,"${UUID.randomUUID()}.jpg")

        try {
            val stream:OutputStream = FileOutputStream(file) // We need output stream because we need to compress our Bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
            stream.flush()
            stream.close()
        }catch (e: IOException){
            e.printStackTrace()
        }
            return file.absolutePath // It will return the directory in which the file exists as well as name of the file
    }

    private fun customItemsDialog(title:String,itemsList:List<String>,selection:String){
        val customListDialog = Dialog(this)
        val binding:DialogCustomListBinding = DialogCustomListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvTitle.text = title
        binding.rvList.layoutManager = LinearLayoutManager(this)

        val adapter = CustomListItemAdapter(this,itemsList,selection)
        binding.rvList.adapter = adapter
        customListDialog.show()
    }

    companion object{
       private const val CAMERA = 1
       private const val GALLERY = 2
        private const val IMAGE_DIRECTORY = "GoodFoodImages"

    }
}