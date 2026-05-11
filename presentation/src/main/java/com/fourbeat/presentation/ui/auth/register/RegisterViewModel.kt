package com.fourbeat.presentation.ui.auth.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourbeat.domain.model.user.RegisterRequest
import com.fourbeat.domain.usecase.user.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    // private val savedStateHandle: SavedStateHandle, // email from route
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {

    // private val email: String = savedStateHandle["email"] ?: ""

    var uiState by mutableStateOf(RegisterUiState())
        private set

    private val _sideEffect = Channel<RegisterSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnNameChanged -> uiState = uiState.copy(name = event.name)
            is RegisterEvent.OnNicknameChanged -> uiState = uiState.copy(nickname = event.nickname)
            RegisterEvent.OnRegisterButtonClicked -> register()
        }
    }

    private fun register() {
        if (uiState.isValid.not()) return

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            registerUseCase(
                RegisterRequest(
                    email = "", // email,
                    name = uiState.name,
                    nickname = uiState.nickname,
                )
            )
                .onSuccess { uid ->
                    _sideEffect.send(RegisterSideEffect.NavigateToHome(uid))
                }
                .onFailure {
                }
            uiState = uiState.copy(isLoading = false)
        }
    }
}
