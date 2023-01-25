package com.example.translator_app.android.di

import android.app.Application
import com.example.translator_app.database.TranslateDatabase
import com.example.translator_app.translate.data.history.SqlDelightHistoryDataSource
import com.example.translator_app.translate.data.local.DatabaseDriverFactory
import com.example.translator_app.translate.data.remote.HttpClientFactory
import com.example.translator_app.translate.data.translate.KtorTranslateClient
import com.example.translator_app.translate.domain.history.HistoryDataSource
import com.example.translator_app.translate.domain.translate.Translate
import com.example.translator_app.translate.domain.translate.TranslateClient
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient{
        return HttpClientFactory().create()

    }

    @Provides
    @Singleton
    fun provideTranslateClient(httpClient: HttpClient): TranslateClient{
        return KtorTranslateClient(httpClient)
    }

    @Provides
    @Singleton
    fun provideDatabaseDriver(app:Application): SqlDriver{
        return DatabaseDriverFactory(app).create()
    }

    @Provides
    @Singleton
    fun provideHistoryDataSource(driver:SqlDriver): HistoryDataSource{
        return SqlDelightHistoryDataSource(TranslateDatabase(driver))
    }

    @Provides
    @Singleton
    fun provideTranslateUseCase(client: TranslateClient,dataSource: HistoryDataSource): Translate{
        return Translate(
                client = client,
                historyDataSource = dataSource

        )
    }
}