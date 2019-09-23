package com.claudineyreis.myapplication.app.dao

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.claudineyreis.myapplication.app.BaseApplication
import com.claudineyreis.myapplication.app.constants.*
import com.claudineyreis.myapplication.app.model.Product


object ProductDBHandler : SQLiteOpenHelper(BaseApplication.instance, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE $TABLE_NAME (" +
                "$KEY_ID INTEGER PRIMARY KEY, " +
                "$KEY_PRODUCT_NAME TEXT, " +
                "$KEY_PRODUCT_QUANTITY INTEGER, " +
                "$KEY_PRODUCT_PRICE FLOAT" +
                ")"

        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")

        onCreate(db)
    }

    fun save(product: Product) {
        val db = writableDatabase
        val values = ContentValues()

        values.put(KEY_PRODUCT_NAME, product.name)
        values.put(KEY_PRODUCT_QUANTITY, product.quantity)
        values.put(KEY_PRODUCT_PRICE, product.price?.toFloat())

        db.insert(TABLE_NAME, null, values)
        Log.d("Data Inserted", "Success")
        db.close()
    }

    fun update(product: Product): Int {
        val db = writableDatabase
        val values = ContentValues()

        values.put(KEY_PRODUCT_NAME, product.name)
        values.put(KEY_PRODUCT_PRICE, product.price?.toDouble())
        values.put(KEY_PRODUCT_QUANTITY, product.quantity)

        //update row
        return db.update(TABLE_NAME, values, "$KEY_ID=?", arrayOf(product.id.toString()))
    }

    fun all(): ArrayList<Product> {

        val db = readableDatabase
        val products = arrayListOf<Product>()
        val sql = "SELECT * FROM $TABLE_NAME"

        val cursor = db.rawQuery(sql, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val name = cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_NAME))
                val quantity = cursor.getInt(cursor.getColumnIndex(KEY_PRODUCT_QUANTITY))
                val price = cursor.getFloat(cursor.getColumnIndex(KEY_PRODUCT_PRICE))

                products.add(Product(id, name, quantity, price.toBigDecimal()))
            } while (cursor.moveToNext())
        }

        cursor.close()

        return products
    }

    fun findById(id: Int): Product {
        val db = writableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(KEY_ID, KEY_PRODUCT_NAME, KEY_PRODUCT_QUANTITY, KEY_PRODUCT_PRICE),
            "$KEY_ID=?",
            arrayOf(id.toString()),
            null,
            null,
            null,
            null
        )

        cursor.moveToFirst()
        val name = cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_NAME))
        val quantity = cursor.getInt(cursor.getColumnIndex(KEY_PRODUCT_QUANTITY))
        val price = cursor.getFloat(cursor.getColumnIndex(KEY_PRODUCT_PRICE))
        val product = Product(id, name, quantity, price.toBigDecimal())

        cursor.close()
        return product
    }

    fun delete(id: Int){
        val db = writableDatabase
        db.delete(TABLE_NAME, "$KEY_ID=?", arrayOf(id.toString()))
        db.close()
    }

    fun deleteAll(){
        val db = writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }


}