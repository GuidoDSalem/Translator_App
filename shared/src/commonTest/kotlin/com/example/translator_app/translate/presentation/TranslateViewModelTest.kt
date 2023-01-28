package com.example.translator_app.translate.presentation

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNotNull
import assertk.assertions.isTrue
import com.example.translator_app.core.presentation.UiLanguage
import com.example.translator_app.translate.data.local.FakeHistoryDataSource
import com.example.translator_app.translate.data.remote.FakeTranslateClient
import com.example.translator_app.translate.domain.history.HistoryItem
import com.example.translator_app.translate.domain.translate.Translate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test

class TranslateViewModelTest {

    private lateinit var viewModel: TranslateViewModel
    private lateinit var client: FakeTranslateClient
    private  lateinit var dataSource: FakeHistoryDataSource

    @BeforeTest
    fun setUp() {
        client = FakeTranslateClient()
        dataSource = FakeHistoryDataSource()
        val translate = Translate(
                client = client,
                historyDataSource = dataSource
        )
        viewModel = TranslateViewModel(
                translate = translate,
                historyDataSource = dataSource,
                coroutineScope = CoroutineScope(Dispatchers.Default)
        )
    }

    @Test
    fun `State and history items are properly combined`() = runBlocking {
        viewModel.state.test {
            val initalState = awaitItem()
            assertThat(initalState).isEqualTo(TranslateState())

            val item = HistoryItem(0,"en","from","de","to")
            dataSource.insertHistoryItem(item)

            val state = awaitItem()
            
            val expected = UiHistoryItem(
                    id = item.id!!,
                    fromText = item.fromText,
                    toText = item.toText,
                    fromLanguage = UiLanguage.byCode(item.fromLanguageCode),
                    toLanguage = UiLanguage.byCode(item.toLanguageCode)
            )

            assertThat(state.history.first()).isEqualTo(expected)


        }
    }

    @Test
    fun `Translate success - state properly updated`() = runBlocking {
        viewModel.state.test{
            awaitItem()

            viewModel.onEvent(TranslateEvent.ChangeTranslationText("new Text to Translate"))
            awaitItem()

            viewModel.onEvent(TranslateEvent.Translate)
            val loadingState = awaitItem()
            assertThat(loadingState.isTranslating).isTrue()

            val resultState = awaitItem()
            assertThat(resultState.isTranslating).isFalse()
            assertThat(resultState.toText).isEqualTo(client.translatedText)
        }
    }
}