package com.claudineyreis.myapplication.app.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.claudineyreis.myapplication.R
import com.claudineyreis.myapplication.app.presenter.ProductPresenter
import java.math.BigDecimal


class ProductReviewFragment : Fragment() {

    private val presenter = ProductPresenter()
    private var salesTax: BigDecimal = BigDecimal.ZERO
    private var total: BigDecimal = BigDecimal.ZERO

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.activity_product_review, container, false)
        setHasOptionsMenu(true)
        activity?.title = getString(R.string.toolbar_title_review)

        populateProductReview(container, v)
        v.findViewById<TextView>(R.id.tv_sales_taxes).text = String.format("$$salesTax")
        v.findViewById<TextView>(R.id.tv_total).text = String.format("$$total")

        return v
    }

    private fun populateProductReview(container: ViewGroup?, v: View) {
        for (product in presenter.retrieveAll()) {
            val subView = LayoutInflater.from(context).inflate(R.layout.product_review_item, container, false)
            subView.findViewById<TextView>(R.id.tv_description).text = product.name
            subView.findViewById<TextView>(R.id.tv_price).text = String.format("$${product.formattedPrice()} ea")
            subView.findViewById<TextView>(R.id.tv_sub_total).text = String.format("$${product.total()}")
            subView.findViewById<TextView>(R.id.tv_quantity).text = String.format("qty ${product.quantity}")

            salesTax += product.taxProduct()
            total += product.total()

            v.findViewById<LinearLayout>(R.id.ll_container).addView(subView)
        }
    }
}
