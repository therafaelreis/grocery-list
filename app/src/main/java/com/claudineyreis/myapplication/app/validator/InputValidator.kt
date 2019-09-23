package com.claudineyreis.myapplication.app.validator

import android.text.TextUtils

object InputValidator {

    fun isNotEmpty(input: String): Boolean{
        return !TextUtils.isEmpty(input)
    }

    fun isNotBlank(input: String): Boolean{
        return input.isNotBlank()
    }
}