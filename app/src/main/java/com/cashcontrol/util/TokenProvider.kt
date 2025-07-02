package com.cashcontrol.util

fun interface TokenProvider {
    fun getToken(): String?
}