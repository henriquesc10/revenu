package com.example.receitas.extensions

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(message: Int) {
    Toast.makeText(activity!!.applicationContext, getText(message), Toast.LENGTH_LONG).show()
}
fun Fragment.toast(message: String){
    Toast.makeText(activity!!.applicationContext, message, Toast.LENGTH_LONG).show()
}
fun Activity.toast(message: String){
    Toast.makeText(baseContext, message, Toast.LENGTH_LONG).show()
}
fun Activity.toast(message: Int){
    Toast.makeText(baseContext, message, Toast.LENGTH_LONG).show()
}