package com.example.translator_app.translate.domain.translate

import com.example.translator_app.core.domain.laguage.Language

interface TranslateClient {
    suspend fun translate(
            fromLanguage: Language,
            fromText: String,
            toLanguage: Language
    ): String
}