package com.mobiria.bnft.ui.auth

interface LoginInterface {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}