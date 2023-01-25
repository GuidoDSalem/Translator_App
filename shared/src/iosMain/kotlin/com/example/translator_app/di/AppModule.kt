package com.example.translator_app.di

import com.example.translator_app.database.TranslateDatabase
import com.example.translator_app.translate.data.history.SqlDelightHistoryDataSource
import com.example.translator_app.translate.data.local.DatabaseDriverFactory
import com.example.translator_app.translate.data.remote.HttpClientFactory
import com.example.translator_app.translate.data.translate.KtorTranslateClient
import com.example.translator_app.translate.domain.history.HistoryDataSource
import com.example.translator_app.translate.domain.translate.Translate
import com.example.translator_app.translate.domain.translate.TranslateClient

class AppModule {

    val historyDataSource: HistoryDataSource by lazy {
        SqlDelightHistoryDataSource(
                TranslateDatabase(
                        DatabaseDriverFactory().create()
                )
        )
    }

    private val translateClient: TranslateClient by lazy {
        KtorTranslateClient(
                HttpClientFactory().create()
        )
    }

    val translateUseCase: Translate by lazy {
        Translate(translateClient,historyDataSource)
    }
}