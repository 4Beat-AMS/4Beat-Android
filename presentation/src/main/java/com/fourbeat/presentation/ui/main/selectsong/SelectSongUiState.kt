package com.fourbeat.presentation.ui.main.selectsong

import com.fourbeat.domain.model.post.Song
import com.fourbeat.presentation.model.common.Validatable

data class SelectSongUiState(
    val selectedSong: Song? = null,
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

sealed interface SelectSongEvent {
    data class OnSongItemToggled(val song: Song) : SelectSongEvent
    data object OnNextButtonClicked : SelectSongEvent
    data object OnBackIconClicked : SelectSongEvent
}

sealed interface SelectSongSideEffect {
    data class NavigateToCreatePost(val groupId: Long) : SelectSongSideEffect
    data object NavigateToBack : SelectSongSideEffect
}
