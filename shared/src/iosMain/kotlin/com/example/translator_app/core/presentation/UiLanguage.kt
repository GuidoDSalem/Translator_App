package com.example.translator_app.core.presentation

import com.example.translator_app.core.domain.laguage.Language

actual class UiLanguage(
        actual val language: Language,
        val imageName: String
){
    actual companion object{
        actual fun byCode(langCode: String): UiLanguage{
            return allLanguages.find{ it.language.langCode == langCode}
                ?: throw IllegalArgumentException("Invalid or unsupported language code")
        }

        actual val allLanguages: List<UiLanguage>
            get() = Language.values().map { language ->
                UiLanguage(
                        language =language,
                        imageName = language.langName.lowercase()
                )
            }

    }
}