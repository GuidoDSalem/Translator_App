package com.example.translator_app.translate.data.remote

import com.example.translator_app.core.domain.laguage.Language
import com.example.translator_app.translate.domain.translate.TranslateClient

class FakeTranslateClient: TranslateClient {

    var translatedText: String = "test translation"

    override suspend fun translate(
            fromLanguage: Language,
            fromText: String,
            toLanguage: Language
    ): String {
        return translatedText
    }

}