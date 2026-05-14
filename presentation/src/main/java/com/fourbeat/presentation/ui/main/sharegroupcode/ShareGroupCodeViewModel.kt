package com.fourbeat.presentation.ui.main.sharegroupcode

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.fourbeat.presentation.navigation.MainScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShareGroupCodeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val code = savedStateHandle.toRoute<MainScreen.ShareGroupCodeDialog>().code

    private val _sideEffect = Channel<ShareGroupCodeSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onEvent(event: ShareGroupCodeEvent) {
        when (event) {
            ShareGroupCodeEvent.OnShareButtonClicked -> viewModelScope.launch {
                _sideEffect.send(ShareGroupCodeSideEffect.ShareCode(code))
            }
        }
    }
}
