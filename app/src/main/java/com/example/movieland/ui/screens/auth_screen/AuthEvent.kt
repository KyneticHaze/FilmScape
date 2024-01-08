package com.example.movieland.ui.screens.auth_screen

open class AuthEvent<T>(private val content : T) {

    private var hasBeenHandled = false

    fun getContentOrNull() : T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}