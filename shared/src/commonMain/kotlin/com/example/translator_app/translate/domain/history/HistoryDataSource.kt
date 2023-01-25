package com.example.translator_app.translate.domain.history

import com.example.translator_app.core.domain.util.CommonFlow

interface HistoryDataSource {

    fun getHistory(): CommonFlow<List<HistoryItem>>

    suspend fun insertHistoryItem(item: HistoryItem): Unit

}