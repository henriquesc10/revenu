package com.example.receitas.model

class Product {
    var tittle: String? = null
    var key: String? = null
    var description: String? = null
    var category: String? = null
    var price: Double? = null

    override fun toString(): String {
        return "Product(tittle=$tittle, key=$key, description=$description, category=$category, price=$price)"
    }


}