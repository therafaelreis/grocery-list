package com.claudineyreis.myapplication.app.adapter

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.claudineyreis.myapplication.R
import com.claudineyreis.myapplication.app.helper.HelperUI
import com.claudineyreis.myapplication.app.model.Product
import com.claudineyreis.myapplication.app.presenter.ProductPresenter
import kotlinx.android.synthetic.main.add_product.view.*

class ProductListAdapter(private val products: ArrayList<Product>, private val context: Context) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    val presenter = ProductPresenter()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.product_row, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(products[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val productName: TextView = itemView.findViewById(R.id.tv_product_description)
        private val productPrice: TextView = itemView.findViewById(R.id.tv_product_price)
        private val productQuantity: TextView = itemView.findViewById(R.id.tv_product_quantity)
        private val editButton: Button = itemView.findViewById(R.id.btn_edit)
        private val deleteButton: Button = itemView.findViewById(R.id.btn_delete)

        override fun onClick(v: View?) {
            val position = adapterPosition
            val product = products[position]
            when (v?.id) {
                deleteButton.id -> {
                    AlertDialog.Builder(context)
                        .setTitle(
                            String.format(
                                context.getString(R.string.msg_popup_delete_confirmation),
                                product.name
                            )
                        )
                        .setPositiveButton(context.getString(R.string.msg_yes)) { _, _ ->
                            presenter.delete(product.id!!)
                            products.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                        }
                        .setNegativeButton(context.getString(R.string.msg_no), null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show()
                }
                editButton.id -> {
                    edit(product)
                }
            }
        }

        private fun edit(product: Product) {
            val view = LayoutInflater.from(context).inflate(R.layout.add_product, null)
            val productName: TextView = view.tie_product_name
            val productPrice: TextView = view.tie_product_price
            val productQuantity: TextView = view.tie_product_quantity
            val saveButton: Button = view.btn_save
            val dialog = HelperUI.createDialog(context, view)
            val aProduct = presenter.findById(product.id!!)

            dialog.show()

            productName.text = aProduct.name
            productPrice.text = aProduct.formattedPrice()
            productQuantity.text = aProduct.quantity.toString()

            saveButton.setOnClickListener {
                val isNameValid = presenter.isValid(productName.text.toString())
                val isPriceValid = presenter.isValid(productPrice.text.toString())
                val isQuantityValid = presenter.isValid(productQuantity.text.toString())

                if (isNameValid && isPriceValid && isQuantityValid) {
                    product.name = productName.text.toString()
                    product.price = productPrice.text.toString().toBigDecimal()
                    product.quantity = productQuantity.text.toString().toInt()

                    presenter.update(product)
                    notifyItemChanged(adapterPosition, product)
                    dialog.dismiss()
                }else{
                    if (!isNameValid) {
                        view.til_product_name.error =
                            String.format(context.getString(R.string.error_required_field), context.getString(R.string.product))
                    }else{
                        view.til_product_name.isErrorEnabled = false
                    }
                    if (!isQuantityValid) {
                        view.til_product_quantity.error =
                            String.format(context.getString(R.string.error_required_field), context.getString(R.string.quantity))
                    }else{
                        view.til_product_quantity.isErrorEnabled = false
                    }

                    if (!isPriceValid) {
                        view.til_product_price.error =
                            String.format(context.getString(R.string.error_required_field), context.getString(R.string.price))
                    }else{
                        view.til_product_price.isErrorEnabled = false
                    }
                }
            }
        }

        fun bindViews(product: Product) {
            val quantity = "qty ${product.quantity.toString()}"
            val price = "$${product.formattedPrice()} ea"

            productName.text = product.name
            productPrice.text = price
            productQuantity.text = quantity

            deleteButton.setOnClickListener(this)
            editButton.setOnClickListener(this)
        }
    }
}