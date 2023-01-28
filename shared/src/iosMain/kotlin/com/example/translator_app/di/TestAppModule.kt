package com.example.translator_app.di

import com.example.translator_app.testing.FakeHistoryDataSource
import com.example.translator_app.testing.FakeTranslateClient
import com.example.translator_app.testing.FakeVoiceToTextParser
import com.example.translator_app.translate.domain.history.HistoryDataSource
import com.example.translator_app.translate.domain.translate.Translate
import com.example.translator_app.translate.domain.translate.TranslateClient
import com.example.translator_app.voice_to_text.domain.VoiceToTextParser

class TestAppModule: AppModule{

    override val historyDataSource: HistoryDataSource = FakeHistoryDataSource()
    override val client: TranslateClient = FakeTranslateClient()
    override val translateUseCase: Translate = Translate(client,historyDataSource)
    override val voiceParser = FakeVoiceToTextParser()
}