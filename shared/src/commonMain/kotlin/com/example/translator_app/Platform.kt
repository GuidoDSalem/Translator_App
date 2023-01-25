package com.example.translator_app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform