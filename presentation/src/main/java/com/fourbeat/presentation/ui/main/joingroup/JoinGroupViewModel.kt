package com.fourbeat.presentation.ui.main.joingroup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourbeat.domain.usecase.group.JoinGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JoinGroupViewModel @Inject constructor(
    private val joinGroupUseCase: JoinGroupUseCase,
) : ViewModel() {
    var uiState by mutableStateOf(JoinGroupUiState())
        private set

    private val _sideEffect = Channel<JoinGroupSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onEvent(event: JoinGroupEvent) {
        when (event) {
            is JoinGroupEvent.OnCodeChanged -> uiState = uiState.copy(code = event.code)
            is JoinGroupEvent.OnJoinButtonClicked -> joinGroup()
            is JoinGroupEvent.OnOutsideClicked -> viewModelScope.launch {
                _sideEffect.send(JoinGroupSideEffect.Dismiss)
            }
        }
    }

    /**
     * 그룹 참여 요청
     * isValid 만족하지 않으면 반환
     * 성공 시, 참여한 그룹 상세 화면으로 이동
     * 실패 시, 에러 메세지 보여주기 (TODO)
     * */
    private fun joinGroup() {
        if (uiState.isValid.not()) return

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            joinGroupUseCase(uiState.code)
                .onSuccess { groupId ->
                    _sideEffect.send(JoinGroupSideEffect.NavigateToGroupDetail(groupId))
                }
                .onFailure { }
            uiState = uiState.copy(isLoading = false)
        }
    }
}
