package com.claudineyreis.myapplication.app.model

import org.junit.Assert.*
import org.junit.Test
import java.math.BigDecimal

class ProductTest{

    @Test
    fun `isCandy product camelcase, returns true`(){
        val product = Product(1,"16lb bag of Skittles Candy", 2, BigDecimal.valueOf(16.00))
        assertTrue(product.isCandy())
    }

    @Test
    fun `isCandy product invalid, returns false`(){
        val product = Product("Walkman", 1, BigDecimal.valueOf(99.99))
        assertFalse(product.isCandy())
    }

    @Test
    fun `isPopCorn product camelcase, returns true`(){
        val product = Product("bag of microwave Popcorn", 1, BigDecimal.valueOf(0.99))
        assertTrue(product.isPopCorn())
    }

    @Test
    fun `isPopCorn product invalid, returns false`(){
        val product = Product("Skittles Candy", 1, BigDecimal.valueOf(16.00))
        assertFalse(product.isPopCorn())
    }

    @Test
    fun `isCoffee product camelcase, returns true`(){
        val product = Product("300# bag of Fair-Trade Coffee", 1, BigDecimal.valueOf(997.99))
        assertTrue(product.isCoffee())
    }

    @Test
    fun `isCoffee product invalid, returns false`(){
        val product = Product("Skittles Candy", 1, BigDecimal.valueOf(16.00))
        assertFalse(product.isCoffee())
    }

    @Test
    fun `isImported product camelcase, returns true`(){
        val product = Product("imported bag of Vanilla-Hazelnut Coffee", 1, BigDecimal.valueOf(11.00))
        assertTrue(product.isImported())
    }

    @Test
    fun `isImported product invalid, returns false`(){
        val product = Product("Skittles Candy", 1, BigDecimal.valueOf(16.00))
        assertFalse(product.isImported())
    }

    @Test
    fun `totalWithoutTax of a product`(){
        val product = Product("bag of microwave Popcorn", 2, BigDecimal.valueOf(0.99))
        val expected = BigDecimal.valueOf(1.98)
        assertEquals(expected, product.totalWithoutTax())
    }

    @Test
    fun `total of a popcorn product which should be the actual price since the tax is 0%`(){
        val product = Product("bag of microwave Popcorn", 1, BigDecimal.valueOf(0.99))
        val expected = BigDecimal.valueOf(0.99)
        assertEquals(expected, product.total())
    }

    @Test
    fun `total of a imported product`(){
        val product = Product("Imported Vespa", 1, BigDecimal.valueOf(15001.25))
        val expected = BigDecimal.valueOf(15751.32)
        assertEquals(expected, product.total())
    }

    @Test
    fun `taxProduct of a imported product`(){
        val product = Product("Imported Vespa", 1, BigDecimal.valueOf(15001.25))
        val expected = BigDecimal.valueOf(750.07)
        assertEquals(expected, product.taxProduct())
    }

    @Test
    fun `taxIncludedProduct of an imported product which add %5 tax`(){
        val product = Product("Imported Vespa", 1, BigDecimal.valueOf(15001.25))
        val expected = BigDecimal.valueOf(15751.32)
        assertEquals(expected, product.taxIncludedProduct())
    }

    @Test
    fun `taxIncludedProduct of a candy product which is 0% tax`(){
        val product = Product("Skittles Candy", 1, BigDecimal.valueOf(16.00))
        val expected = BigDecimal.valueOf(16.00)
        assertEquals(expected, product.taxIncludedProduct())
    }

    @Test
    fun `taxIncludedProduct of a popcorn product which is 0% tax`(){
        val product = Product("bag of microwave Popcorn", 1, BigDecimal.valueOf(0.99))
        val expected = BigDecimal.valueOf(0.99)
        assertEquals(expected, product.taxIncludedProduct())
    }

    @Test
    fun `taxIncludedProduct of a coffee product which is 0% tax`(){
        val product = Product("300# bag of Fair-Trade Coffee", 1, BigDecimal.valueOf(997.99))
        val expected = BigDecimal.valueOf(997.99)
        assertEquals(expected, product.taxIncludedProduct())
    }

    @Test
    fun `taxIncludedProduct of any product which is 10% tax`(){
        val product = Product("Walkman", 2, BigDecimal.valueOf(99.99))
        val expected = BigDecimal.valueOf(219.98)
        assertEquals(expected, product.taxIncludedProduct())
    }
}