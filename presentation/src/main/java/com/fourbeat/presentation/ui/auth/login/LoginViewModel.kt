package com.fourbeat.presentation.ui.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourbeat.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set

    private val _sideEffect = Channel<LoginSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnLoginButtonClicked -> login(event.email)
        }
    }

    private fun login(email: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            loginUseCase(email)
                .onSuccess { uid ->
                    _sideEffect.send(LoginSideEffect.NavigateToHome(uid))
                }
                .onFailure {
                }
            uiState = uiState.copy(isLoading = false)
        }
    }
}
