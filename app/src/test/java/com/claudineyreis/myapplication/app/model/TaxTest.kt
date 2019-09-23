package com.claudineyreis.myapplication.app.model

import org.junit.Assert.*
import org.junit.Test
import java.math.BigDecimal

class TaxTest {

    @Test
    fun `applyTax for imported products which is 5%`() {
        val product = Product("imported bag of Vanilla-Hazelnut Coffee", 1, BigDecimal.valueOf(11.00))
        val tax = Tax(product)

        val expected = BigDecimal.valueOf(11.55)
        assertEquals(expected, tax.taxIncludedProduct())
    }

    @Test
    fun `applyTax for candy products which is none`() {
        val product = Product("16lb bag of Skittles Candy", 1, BigDecimal.valueOf(16.00))
        val tax = Tax(product)

        val expected = BigDecimal.valueOf(16.00)
        assertEquals(expected, tax.taxIncludedProduct())
    }

    @Test
    fun `applyTax for coffee products which is none`() {
        val product = Product("300# bag of Fair-Trade Coffee", 1, BigDecimal.valueOf(997.99))
        val tax = Tax(product)

        val expected = BigDecimal.valueOf(997.99)
        assertEquals(expected, tax.taxIncludedProduct())
    }

    @Test
    fun `applyTax for popcorn products which is none`() {
        val product = Product("bag of microwave Popcorn", 1, BigDecimal.valueOf(0.99))
        val tax = Tax(product)

        val expected = BigDecimal.valueOf(0.99)
        assertEquals(expected, tax.taxIncludedProduct())
    }

    @Test
    fun `applyTax for any products that isn't imported, candy, popcorn, or coffee which is 10%`() {
        val product = Product("Walkman", 1, BigDecimal.valueOf(109.99))
        val tax = Tax(product)

        val expected = BigDecimal.valueOf(120.99)
        assertEquals(expected, tax.taxIncludedProduct())
    }

    @Test
    fun `applyTax for a large product price that isn't imported, candy, popcorn, or coffee which is 10%`() {
        val product = Product("Some product that is very expensive", 1, BigDecimal.valueOf(1000000.99))
        val tax = Tax(product)

        val expected = BigDecimal.valueOf(1100001.090)
        assertEquals(expected, tax.taxIncludedProduct())
    }

    @Test
    fun `onlyTax for imported product should be 5% tax`() {
        val product = Product("imported bag of Vanilla-Hazelnut Coffee", 1, BigDecimal.valueOf(16.00))
        val tax = Tax(product)

        val expected = BigDecimal.valueOf(0.80).setScale(2, BigDecimal.ROUND_UP)
        assertEquals(expected, tax.onlyTax())
    }


    @Test
    fun `onlyTax for candy products which is 0%`() {
        val product = Product("16lb bag of Skittles Candy", 1, BigDecimal.valueOf(16.00))
        val tax = Tax(product)

        val expected = BigDecimal.ZERO
        assertEquals(expected, tax.onlyTax())
    }

    @Test
    fun `onlyTax for popcorn products which is 0%`() {
        val product = Product("bag of microwave Popcorn", 1, BigDecimal.valueOf(0.99))
        val tax = Tax(product)

        val expected = BigDecimal.ZERO
        assertEquals(expected, tax.onlyTax())
    }

    @Test
    fun `onlyTax for coffee products which is 0%`() {
        val product = Product("300# bag of Fair-Trade Coffee", 1, BigDecimal.valueOf(997.99))
        val tax = Tax(product)

        val expected = BigDecimal.ZERO
        assertEquals(expected, tax.onlyTax())
    }

    @Test
    fun `onlyTax for imported products which is 5%`() {
        val product = Product("imported bag of Vanilla-Hazelnut Coffee", 1, BigDecimal.valueOf(11.00))
        val tax = Tax(product)

        val expected = BigDecimal.valueOf(0.55).setScale(2)
        assertEquals(expected, tax.onlyTax())
    }

    @Test
    fun `onlyTax for imported vespa products which is 5%`() {
        val product = Product("Imported Vespa", 1, BigDecimal.valueOf(15001.25))
        val tax = Tax(product)

        val expected = BigDecimal.valueOf(750.07).setScale(2, BigDecimal.ROUND_UP)
        assertEquals(expected, tax.onlyTax())
    }

    @Test
    fun `onlyTax for any products that isn't imported, candy, popcorn, or coffee which is 10%`() {
        val product = Product("Walkman", 1, BigDecimal.valueOf(99.99))
        val tax = Tax(product)

        val expected = BigDecimal.valueOf(10.00).setScale(2)
        assertEquals(expected, tax.onlyTax())
    }
}