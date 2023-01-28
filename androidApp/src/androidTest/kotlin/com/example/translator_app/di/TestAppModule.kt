package com.example.translator_app.di

import com.example.translator_app.translate.data.local.FakeHistoryDataSource
import com.example.translator_app.translate.data.remote.FakeTranslateClient
import com.example.translator_app.translate.domain.history.HistoryDataSource
import com.example.translator_app.translate.domain.translate.Translate
import com.example.translator_app.translate.domain.translate.TranslateClient
import com.example.translator_app.voice_to_text.data.FakeVoiceToTextParser
import com.example.translator_app.voice_to_text.domain.VoiceToTextParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class
)
class TestAppModule {

    @Provides
    @Singleton
    fun provideFakeTranslateClient(): TranslateClient{
        return FakeTranslateClient()
    }


    @Provides
    @Singleton
    fun provideFakeHistoryDataSource(): HistoryDataSource{
        return FakeHistoryDataSource()
    }

    @Provides
    @Singleton
    fun provideTranslateUseCase(
            client: TranslateClient,
            dataSource: HistoryDataSource
    ): Translate{
        return Translate(
                client = client,
                historyDataSource = dataSource
        )
    }

    @Provides
    @Singleton
    fun provideFakeVoiceToTextParser(): VoiceToTextParser{
        return FakeVoiceToTextParser()
    }

}