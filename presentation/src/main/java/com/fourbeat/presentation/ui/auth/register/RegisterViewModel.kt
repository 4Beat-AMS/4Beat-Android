package com.fourbeat.presentation.ui.auth.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.fourbeat.domain.model.user.RegisterRequest
import com.fourbeat.domain.usecase.user.RegisterUseCase
import com.fourbeat.presentation.navigation.AuthScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val email = savedStateHandle.toRoute<AuthScreen.Register>().email

    var uiState by mutableStateOf(RegisterUiState())
        private set

    private val _sideEffect = Channel<RegisterSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnNameChanged -> uiState = uiState.copy(name = event.name)
            is RegisterEvent.OnNicknameChanged -> uiState = uiState.copy(nickname = event.nickname)
            RegisterEvent.OnRegisterButtonClicked -> register()
            RegisterEvent.OnBackClicked -> viewModelScope.launch {
                _sideEffect.send(RegisterSideEffect.NavigateToBack)
            }
        }
    }

    private fun register() {
        if (uiState.isValid.not()) return

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            registerUseCase(
                RegisterRequest(
                    email = email,
                    name = uiState.name,
                    nickname = uiState.nickname,
                )
            )
                .onSuccess {
                    _sideEffect.send(RegisterSideEffect.NavigateToHome)
                }
                .onFailure {

                }
            uiState = uiState.copy(isLoading = false)
        }
    }
}
