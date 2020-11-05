package com.example.receitas.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.receitas.R
import com.example.receitas.extensions.toast
import com.example.receitas.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_add_product.*
import java.io.ByteArrayOutputStream

class AddProductActivity : AppCompatActivity() {

    val database = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        progressBar.visibility = View.INVISIBLE

        addProductButtonPicture.setOnClickListener {
            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.OFF)
                .start(this@AddProductActivity)
        }

        buttonAddProduct.setOnClickListener {

            progressBar.visibility = View.INVISIBLE

            var product = Product()
            product.tittle = editTextTittle.text.toString()
            product.description = editTextAddProductDescription.text.toString()
            product.category = spinnerAddProduct.selectedItem.toString()

            //ENVIA PARA O STORAGE
            val storage = FirebaseStorage.getInstance()
            val storageReference = storage.reference

            val bitmap = imageViewAddProduct.getCroppedImage()
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            val filename = "images/" + product.tittle
            val fileReference = storageReference!!.child(filename + ".jpg")
            val uploadTask = fileReference.putBytes(data)

            uploadTask.addOnFailureListener { exception ->

            }.addOnCompleteListener { task ->

            }

            database.collection("products")
                .add(product)
                .addOnSuccessListener { documentReference ->
                    progressBar.visibility = View.INVISIBLE
                    toast(R.string.product_successful)
                }
                .addOnFailureListener { error ->
                    progressBar.visibility = View.INVISIBLE
                    toast(R.string.product_fail)
                    toast("$error")
                }

            finish()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultURI = result.uri
                imageViewAddProduct.setImageUriAsync(resultURI)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                toast(R.string.image_fail)
            } else {
                toast("FATAL ERROR!")
            }

        }
    }
}