package com.furkanhrmnc.filmscape.data.network.service

import com.furkanhrmnc.filmscape.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders
import io.ktor.http.path
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

abstract class KtorApi {

    companion object {
        const val API_KEY = BuildConfig.API_KEY
        const val BASE_URL = BuildConfig.BASE_URL
    }

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                    prettyPrint = true
                    isLenient = true
                }
            )
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }

    }

    fun HttpRequestBuilder.pathUrl(path: String) {
        url {
            takeFrom(BASE_URL)
            path("3", path)
            parameter("api_key", API_KEY)
        }
    }
}