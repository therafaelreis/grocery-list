package com.claudineyreis.myapplication.app.ui


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.*
import android.widget.Button
import com.claudineyreis.myapplication.R
import com.claudineyreis.myapplication.app.adapter.ProductListAdapter
import com.claudineyreis.myapplication.app.helper.HelperUI
import com.claudineyreis.myapplication.app.presenter.ProductPresenter
import kotlinx.android.synthetic.main.add_product.view.*
import kotlinx.android.synthetic.main.fragment_cart_list.*

class CartListFragment : Fragment() {

    private val presenter = ProductPresenter()
    private var products = presenter.retrieveAll()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_cart_list, container, false)

        setHasOptionsMenu(true)

        v.findViewById<Button>(R.id.btn_remove_products).setOnClickListener {
            if (context != null && products.size != 0) {
                AlertDialog.Builder(context!!)
                    .setTitle(
                        context!!.getString(R.string.msg_popup_delete_all_confirmation)
                    )
                    .setPositiveButton(context!!.getString(R.string.msg_yes)) { _, _ ->
                        presenter.deleteAll()
                        products = presenter.retrieveAll()
                        setAdapter()
                        HelperUI.createSnackBar(context, v, getString(R.string.msg_all_product_delete_success))
                    }
                    .setNegativeButton(context!!.getString(R.string.msg_no), null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()
            }
        }

        return v
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_cart, menu)
    }

    override fun onResume() {
        super.onResume()
        activity?.title = getString(R.string.toolbar_title_cart_list)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_add) {
            createPopupDialog()
        } else if (item?.itemId == R.id.action_done) {
            fragmentManager?.beginTransaction()?.setCustomAnimations(
                R.animator.from_right,
                R.animator.exit_left,
                R.animator.from_left,
                R.animator.exit_right
            )
                ?.replace(R.id.fl_container, ProductReviewFragment())
                ?.addToBackStack(ProductReviewFragment::class.java.simpleName)?.commit()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun createPopupDialog() {
        val view = HelperUI.addProductInflater(context)
        val name = view.tie_product_name
        val price = view.tie_product_price
        val quantity = view.tie_product_quantity
        val save = view.btn_save

        val dialog = HelperUI.createDialog(context, view)
        dialog.show()


        save.setOnClickListener {
            val isNameValid = presenter.isValid(name.text.toString())
            val isPriceValid = presenter.isValid(price.text.toString())
            val isQuantityValid = presenter.isValid(quantity.text.toString())

            if (isNameValid && isPriceValid && isQuantityValid) {
                    val product =
                    presenter.createProduct(name.text.toString(), quantity.text.toString(), price.text.toString())

                presenter.save(product)
                products = presenter.retrieveAll()

                setAdapter()

                dialog.dismiss()
            } else {
                if (!isNameValid) {
                    view.til_product_name.error =
                        String.format(getString(R.string.error_required_field), getString(R.string.product))
                } else {
                    view.til_product_name.isErrorEnabled = false
                }
                if (!isQuantityValid) {
                    view.til_product_quantity.error =
                        String.format(getString(R.string.error_required_field), getString(R.string.quantity))
                } else {
                    view.til_product_quantity.isErrorEnabled = false
                }

                if (!isPriceValid) {
                    view.til_product_price.error =
                        String.format(getString(R.string.error_required_field), getString(R.string.price))
                } else {
                    view.til_product_price.isErrorEnabled = false
                }
            }
        }

    }

    private fun setAdapter() {
        rv_products.apply {
            adapter = ProductListAdapter(products, context)
            adapter?.notifyDataSetChanged()
        }
    }

}
