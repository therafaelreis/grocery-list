package com.claudineyreis.myapplication.app.presenter

import com.claudineyreis.myapplication.app.dao.ProductDBHandler
import com.claudineyreis.myapplication.app.model.Product
import com.claudineyreis.myapplication.app.validator.InputValidator

class ProductPresenter {
    private val dbHandler = ProductDBHandler

    fun isValid(input: String): Boolean {
        return InputValidator.isNotEmpty(input) && InputValidator.isNotBlank(input)
    }

    fun save(product: Product) {
        dbHandler.save(product)
    }

    fun retrieveAll(): ArrayList<Product> {
        return dbHandler.all()
    }

    fun delete(id: Int) {
        dbHandler.delete(id)
    }

    fun update(product: Product) {
        dbHandler.update(product)
    }

    fun findById(id: Int): Product {
        return dbHandler.findById(id)
    }

    fun deleteAll() {
        return dbHandler.deleteAll()
    }

    fun createProduct(name: String, quantity: String, price: String): Product {
        return Product(
            name,
            Integer.parseInt(quantity),
            price.toBigDecimal()
        )
    }

}