package com.fourbeat.presentation.ui.main.sharegroupcode

sealed interface ShareGroupCodeEvent {
    data object OnShareButtonClicked : ShareGroupCodeEvent
}

sealed interface ShareGroupCodeSideEffect {
    data class ShareCode(val code: String) : ShareGroupCodeSideEffect
}
