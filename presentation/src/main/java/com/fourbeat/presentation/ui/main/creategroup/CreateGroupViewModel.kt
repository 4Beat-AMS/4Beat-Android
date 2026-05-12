package com.fourbeat.presentation.ui.main.creategroup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourbeat.domain.model.group.CreateGroupRequest
import com.fourbeat.domain.usecase.group.CreateGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGroupViewModel @Inject constructor(
    private val createGroupUseCase: CreateGroupUseCase,
) : ViewModel() {
    var uiState by mutableStateOf(CreateGroupUiState())
        private set

    private val _sideEffect = Channel<CreateGroupSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onEvent(event: CreateGroupEvent) {
        when (event) {
            is CreateGroupEvent.OnNameChanged -> uiState = uiState.copy(name = event.name)
            is CreateGroupEvent.OnMemberCountSelected -> uiState = uiState.copy(selectedMemberCount = event.memberCount)
            is CreateGroupEvent.OnCreateButtonClicked -> createGroup()
            is CreateGroupEvent.OnBackClicked -> viewModelScope.launch {
                _sideEffect.send(CreateGroupSideEffect.NavigateToBack)
            }
        }
    }

    /**
     * 그룹 생성 요청
     * isValid 만족하지 않으면 반환
     * 성공 시, 생성된 그룹 상세 화면으로 이동
     * 실패 시, 에러 메세지 보여주기 (TODO)
     * */
    private fun createGroup() {
        if (uiState.isValid.not()) return

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            createGroupUseCase(
                CreateGroupRequest(
                    name = uiState.name,
                    maxMemberCount = uiState.selectedMemberCount!!,
                )
            )
                .onSuccess { groupId ->
                    _sideEffect.send(CreateGroupSideEffect.NavigateToGroupDetail(groupId))
                }
                .onFailure { }
            uiState = uiState.copy(isLoading = false)
        }
    }
}
