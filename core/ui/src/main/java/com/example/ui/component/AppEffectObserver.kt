package com.example.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.ui.base.BaseEffect
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun AppEffectObserver(
    effectFlow: SharedFlow<BaseEffect>,
    onEffectReceived: suspend (BaseEffect) -> Unit
) {
    LaunchedEffect(Unit) {
        effectFlow.collect { effect ->
            onEffectReceived(effect)
        }
    }
}