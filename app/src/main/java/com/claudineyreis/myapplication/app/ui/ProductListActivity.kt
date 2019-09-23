package com.claudineyreis.myapplication.app.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.claudineyreis.myapplication.R

class ProductListActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        supportFragmentManager.beginTransaction().replace(R.id.fl_container, CartListFragment()).commit()
    }
}