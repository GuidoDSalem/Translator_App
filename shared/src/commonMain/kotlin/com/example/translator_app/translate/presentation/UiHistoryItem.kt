package com.example.translator_app.translate.presentation

import com.example.translator_app.core.presentation.UiLanguage

data class UiHistoryItem(
        val id: Long,
        val fromText: String,
        val toText: String,
        val fromLanguage: UiLanguage,
        val toLanguage: UiLanguage
)