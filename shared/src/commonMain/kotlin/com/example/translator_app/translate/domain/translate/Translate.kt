package com.example.translator_app.translate.domain.translate

import com.example.translator_app.core.domain.laguage.Language
import com.example.translator_app.core.domain.util.Resource
import com.example.translator_app.translate.domain.history.HistoryDataSource
import com.example.translator_app.translate.domain.history.HistoryItem

class Translate(
        private val client: TranslateClient,
        private val historyDataSource: HistoryDataSource
) {
    suspend fun execute(
            fromLanguage: Language,
            fromText: String,
            toLanguage: Language
    ): Resource<String>{
        return try {
            val translatedText = client.translate(
                    fromLanguage =fromLanguage,
                    fromText = fromText,
                    toLanguage = toLanguage

            )

            historyDataSource.insertHistoryItem(
                    HistoryItem(
                        id = null,
                        fromLanguageCode = fromLanguage.langCode,
                        fromText = fromText,
                        toLanguageCode = toLanguage.langCode,
                        toText = translatedText
                 )
            )

            Resource.Success(translatedText)

        } catch(e: TranslateException){
            e.printStackTrace()
            Resource.Error(e)
        }
    }
}