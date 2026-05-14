package com.fourbeat.presentation.ui.main.selectsong

import com.fourbeat.domain.model.post.Song
import com.fourbeat.presentation.model.common.Validatable

data class SelectSongUiState(
    val selectedSong: Song? = null,
    val searchQuery: String = "",
) : Validatable {
    override val isValid: Boolean
        get() = selectedSong != null

    val buttonText: String
        get() = if (isValid) {
            val title = selectedSong!!.title
            val titleEllipsis = if (title.length >= 10) {
                "${title.take(8)}.."
            } else {
                title
            }
            "'${titleEllipsis}'로 계속하기"
        } else {
            "다음으로"
        }
}

sealed class LiveSongUiState {
    data object Loading : LiveSongUiState()
    data object PermissionRequired : LiveSongUiState()
    data object None : LiveSongUiState()
    data class Live(val song: Song) : LiveSongUiState()
}

sealed interface SelectSongEvent {
    data class OnSongItemToggled(val song: Song) : SelectSongEvent
    data class OnSearchQueryChanged(val query: String) : SelectSongEvent
    data object OnNextButtonClicked : SelectSongEvent
    data object OnBackIconClicked : SelectSongEvent
    data object OnRequestPermissionClicked : SelectSongEvent
}

sealed interface SelectSongSideEffect {
    data class NavigateToCreatePost(val groupId: Long) : SelectSongSideEffect
    data object NavigateToBack : SelectSongSideEffect
    data object OpenNotificationListenerSettings : SelectSongSideEffect
}
