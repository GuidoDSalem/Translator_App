package com.example.translator_app.android

import android.icu.text.MessagePattern.ArgType
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.translator_app.Greeting
import com.example.translator_app.android.core.presentation.Screen
import com.example.translator_app.android.translate.presentation.AndroidTranslateViewModel
import com.example.translator_app.android.translate.presentation.TranslateScreen
import com.example.translator_app.android.voice_to_text.presentation.AndroidVoiceToTextViewModel
import com.example.translator_app.android.voice_to_text.presentation.VoiceToTextScreen
import com.example.translator_app.translate.presentation.TranslateEvent
import com.example.translator_app.voice_to_text.presentation.VoiceToTextEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TranslatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TranslateRoot()
                }
            }
        }
    }
}

@Composable
fun TranslateRoot() {
    val navController = rememberNavController()
    NavHost(
            navController = navController,
            startDestination = Screen.TranslateScreen.route
    ){

        composable(route = Screen.TranslateScreen.route){
            val viewModel = hiltViewModel<AndroidTranslateViewModel>()
            val state by viewModel.state.collectAsState()

            val voiceResult by it
                .savedStateHandle
                .getStateFlow<String?>("voiceResult",null)
                .collectAsState()

            LaunchedEffect(key1 = voiceResult) {
                viewModel.onEvent(TranslateEvent.SubmitVoiceResult(voiceResult))
                it.savedStateHandle["voiceResult"] = null
            }
            TranslateScreen(
                    state = state,
                    onEvent = { event ->
                        when(event){
                            is TranslateEvent.RecordAudio -> {
                                navController.navigate(
                                        Screen.VoiceToTextScreen.withArgs(state.fromLanguage.language
                                                                         .langCode)
                                )
                            }
                            else -> {
                                viewModel.onEvent(event)
                            }
                        }
                    }
            )
        }

        composable(
                route = Screen.VoiceToTextScreen.route,
                arguments = listOf(
                        navArgument("languageCode"){
                            type = NavType.StringType
                            defaultValue = "en"
                        }
                )
        ){ backStackEntry ->
            val languageCode = backStackEntry.arguments?.getString("languageCode") ?: "en"
            val viewModel = hiltViewModel<AndroidVoiceToTextViewModel>()
            val state by viewModel.state.collectAsState()

            VoiceToTextScreen(
                    state = state,
                    languageCode = languageCode,
                    onResult = { spokenText ->
                        navController.previousBackStackEntry?.savedStateHandle?.set(
                                "voiceResult",spokenText
                        )
                        navController.popBackStack()

                    },
                    onEvent = { event ->
                        when(event) {
                            is VoiceToTextEvent.Close -> {
                                navController.popBackStack()
                            }
                            else -> viewModel.onEvent(event)
                        }

                    }
            )

        }
    }
    
}

