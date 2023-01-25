package com.example.translator_app.core.presentation

import com.example.translator_app.core.domain.laguage.Language

expect class UiLanguage {
    val language: Language
    companion object{
        fun byCode(langCode: String): UiLanguage
        val allLanguages: List<UiLanguage>
    }
}