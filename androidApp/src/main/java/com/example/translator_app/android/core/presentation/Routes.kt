package com.example.translator_app.android.core.presentation

sealed class Screen(val route: String){
    object TranslateScreen: Screen("translate")
    object VoiceToTextScreen: Screen("voice_to_text/{languageCode}"){
        fun withArgs(langCode: String): String{
            return "voice_to_text" + "/$langCode"

        }
    }
}
