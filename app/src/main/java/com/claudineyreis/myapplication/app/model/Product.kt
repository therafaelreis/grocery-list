package com.claudineyreis.myapplication.app.model

import java.math.BigDecimal

class Product() {

    var id: Int? = null
    var name: String? = null
    var quantity: Int? = null
    var price: BigDecimal? = null
    private val tax = Tax(this)


    constructor(name: String, quantity: Int, price: BigDecimal) : this() {
        this.name = name
        this.quantity = quantity
        this.price = price
    }

    constructor(id: Int, name: String, quantity: Int, price: BigDecimal) : this() {
        this.id = id
        this.name = name
        this.price = price
        this.quantity = quantity
    }

    fun formattedPrice(): String{
        return price?.setScale(2).toString()
    }

    fun total(): BigDecimal {
        return totalWithoutTax().add(taxProduct())
    }

    fun taxIncludedProduct(): BigDecimal {
        return quantity?.toBigDecimal()?.multiply(tax.taxIncludedProduct())!!
    }

    fun taxProduct(): BigDecimal {
        return tax.onlyTax()
    }

    fun totalWithoutTax(): BigDecimal {
        return quantity?.toBigDecimal()?.multiply(price)!!
    }

    fun isImported(): Boolean {
        return name?.contains("imported", true)!!
    }

    fun isCandy(): Boolean {
        return name?.contains("candy", true)!!
    }

    fun isPopCorn(): Boolean {
        return name?.contains("popcorn", true)!!
    }

    fun isCoffee(): Boolean {
        return name?.contains("coffee", true)!! && !isImported()
    }
}