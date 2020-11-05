package com.example.receitas.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import com.theartofdev.edmodo.cropper.CropImageView

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentAddProduct.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentAddProduct : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    lateinit var imageViewPicture: CropImageView

    val database = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val view: View = inflater.inflate(R.layout.fragment_add_product, container, false)
//        imageViewPicture = view.findViewById(R.id.imageViewAddProduct)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        view.progressBar.visibility = View.INVISIBLE
//
//        view.addProductButtonPicture.setOnClickListener {
//            CropImage.activity()
//                .setGuidelines(CropImageView.Guidelines.OFF)
//                .start(requireActivity())
//            //.start(activity!!)
//        }
//
//        view.buttonAddProduct.setOnClickListener {
//            var product = Product()
//            product.tittle = view.editTextTittle.text.toString()
//            product.description = view.editTextAddProductDescription.text.toString()
//            product.category = view.spinnerAddProduct.selectedItem.toString()
//            product.price = view.editTextPrice.text.toString().toDouble()
//
//            //ENVIA PARA O STORAGE
//            val storage = FirebaseStorage.getInstance()
//            val storageReference = storage.reference
//
//            val bitmap = imageViewPicture.getCroppedImage()
//            val baos = ByteArrayOutputStream()
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//            val data = baos.toByteArray()
//
//            val filename = "images/" + product.tittle
//            val fileReference = storageReference!!.child(filename + ".jpg")
//            val uploadTask = fileReference.putBytes(data)
//
//            uploadTask.addOnFailureListener { exception ->
//                toast("Erro ao salvar imagem ")
//            }.addOnCompleteListener { task ->
//                toast("Imagem salva com sucesso ")
//            }
//
//            database.collection("products")
//                .add(product)
//                .addOnSuccessListener { documentReference ->
//                    toast(R.string.product_successful)
//                }
//                .addOnFailureListener { error ->
//                    toast(R.string.product_fail)
//                    toast("$error")
//                }
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            val result = CropImage.getActivityResult(data)
//            if (resultCode == Activity.RESULT_OK) {
//                val resultURI = result.uri
//                requireView().imageViewAddProduct.setImageUriAsync(resultURI)
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                toast("Algum erro ocorreu ao cortar a imagem ")
//            } else {
//                toast("não deu")
//            }
//
//        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentAddProduct.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentAddProduct().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}