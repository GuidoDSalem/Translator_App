package com.example.translator_app.translate.data.remote

import io.ktor.client.*
import io.ktor.client.engine.darwin.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

actual class HttpClientFactory {
    actual fun create(): HttpClient {
        val httpClient = HttpClient(Darwin){
            install(ContentNegotiation){
                json()
            }
        }
        return httpClient
    }
}