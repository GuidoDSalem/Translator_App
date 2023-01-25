package com.example.translator_app.core.domain.util

import kotlinx.coroutines.flow.MutableStateFlow

class IOSMutableStateFlow<T>(
        initialValue: T
): CommonMutableStateFlow<T>(MutableStateFlow(initialValue))