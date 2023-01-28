package com.example.translator_app.testing

import com.example.translator_app.core.domain.util.CommonStateFlow
import com.example.translator_app.core.domain.util.toCommonStateFlow
import com.example.translator_app.voice_to_text.domain.VoiceToTextParser
import com.example.translator_app.voice_to_text.domain.VoiceToTextParserState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeVoiceToTextParser: VoiceToTextParser {

    private val _state = MutableStateFlow(VoiceToTextParserState())

    override val state: CommonStateFlow<VoiceToTextParserState>
        get() = _state.toCommonStateFlow()

    override fun startListening(languageCode: String) {
        _state.update { it.copy(
                result =  "",
                isSpeaking = true
        ) }
    }

    var voiceResult = "test result"

    override fun stopListening() {
        _state.update { it.copy(
                result = voiceResult,
                isSpeaking = false
        ) }
    }

    override fun cancel() = Unit

    override fun reset() {
        _state.update { VoiceToTextParserState() }
    }
}