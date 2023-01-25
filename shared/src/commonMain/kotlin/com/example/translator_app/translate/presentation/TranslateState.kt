package com.example.translator_app.translate.presentation

import com.example.translator_app.core.presentation.UiLanguage
import com.example.translator_app.translate.domain.translate.TranslateError

data class TranslateState(
        val fromText: String = "",
        val toText: String? = null,
        val isTranslating: Boolean = false,
        val fromLanguage: UiLanguage = UiLanguage.byCode("es"),
        val toLanguage: UiLanguage = UiLanguage.byCode("en"),
        val isChoosingFromLanguage: Boolean = false,
        val isChoosingToLanguage:  Boolean = false,
        val error: TranslateError? = null,
        val history: List<UiHistoryItem> = emptyList<UiHistoryItem>()
)
