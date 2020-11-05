package com.example.receitas.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.receitas.Adapter.ProductAdapter
import com.example.receitas.R
import com.example.receitas.extensions.toast
import com.example.receitas.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_list.view.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentList.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentList : Fragment() {

    val database = FirebaseFirestore.getInstance()
    private lateinit var adapter: ProductAdapter
    private var products = arrayListOf<Product>()

    private var param1: String? = null
    private var param2: String? = null

//    lateinit var recyclerView: RecyclerView

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
        var view: View = inflater.inflate(R.layout.fragment_list, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById(R.id.listProductRecyclerView) as RecyclerView

        adapter = ProductAdapter(
            products,
            requireActivity().applicationContext,
            { product: Product -> itemClicked(product) })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity().applicationContext)

        database.collection("products")
            .get()
            .addOnSuccessListener { result ->
                view.listProductProgressBar.visibility = View.INVISIBLE
                if (result.isEmpty) {
                    toast(R.string.no_products)
                    return@addOnSuccessListener
                }
                for (document in result) {
                    val product = document.toObject(Product::class.java)
                    product.key = document.id
                    products.add(product)
                }
                adapter.updateList()
            }
            .addOnFailureListener { error ->
                view.listProductProgressBar.visibility = View.INVISIBLE
                toast("erro ao consultar Produtos ")
            }
    }

    fun itemClicked(product: Product) {
        toast(R.string.click)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentList.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentList().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}