package com.claudineyreis.myapplication.app.model

import java.math.BigDecimal

class Tax(private val product: Product) {

    fun taxIncludedProduct(): BigDecimal {
        return when {
            product.isImported() -> product.price!! + (product.price?.multiply(BigDecimal.valueOf(5))?.divide(
                BigDecimal.valueOf(
                    100
                )
            ))?.setScale(2, BigDecimal.ROUND_UP)!!
            product.isCandy() || product.isCoffee() || product.isPopCorn() -> product.price!!
            else -> product.price!! + (product.price?.multiply(BigDecimal.TEN)?.divide(BigDecimal.valueOf(100)))?.setScale(
                2,
                BigDecimal.ROUND_UP
            )!!
        }
    }

    fun onlyTax(): BigDecimal {
        return when {
            product.isImported() -> product.quantity?.toBigDecimal()?.multiply(product.price?.multiply(BigDecimal.valueOf(.05))?.setScale(2, BigDecimal.ROUND_UP))!!
            product.isCandy() || product.isCoffee() || product.isPopCorn() -> BigDecimal.ZERO
            else -> product.price?.multiply(BigDecimal.valueOf(0.10))?.setScale(2, BigDecimal.ROUND_UP)!!
        }
    }


}