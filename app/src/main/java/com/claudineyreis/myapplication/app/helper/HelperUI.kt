package com.claudineyreis.myapplication.app.helper

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import com.claudineyreis.myapplication.R

object HelperUI {
    fun createDialog(context: Context?, view: View): Dialog {
        val dialogBuilder: AlertDialog.Builder? = AlertDialog.Builder(context).setView(view)
        return dialogBuilder?.create()!!
    }

    fun addProductInflater(context: Context?): View {
        return LayoutInflater.from(context).inflate(R.layout.add_product, null)
    }

    fun createSnackBar(context: Context?, v: View, message: String) {
        val snackbar = Snackbar.make(v, message, Snackbar.LENGTH_SHORT)
        val snackbarView = snackbar.view
        if (context != null) {
            snackbarView.setBackgroundColor(ContextCompat.getColor(context!!, R.color.green))
            snackbar.show()
        }
    }
}