package com.fourbeat.presentation.ui.main.groupdetail.header

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.fourbeat.domain.model.group.Group
import com.fourbeat.domain.usecase.group.GetGroupInfoUseCase
import com.fourbeat.presentation.mapper.toUiModel
import com.fourbeat.presentation.model.group.GroupUiModel
import com.fourbeat.presentation.navigation.MainScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailHeaderViewModel @Inject constructor(
    private val getGroupInfoUseCase: GetGroupInfoUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val groupId = savedStateHandle.toRoute<MainScreen.GroupDetail>().groupId

    var uiState by mutableStateOf(GroupDetailHeaderUiState(group = GroupUiModel.getEmpty(groupId)))
        private set

    private val _sideEffect = Channel<GroupDetailHeaderSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        loadGroupInfo()
    }

    fun onEvent(event: GroupDetailHeaderEvent) {
        when (event) {
            is GroupDetailHeaderEvent.OnPlusIconClicked -> viewModelScope.launch {
                _sideEffect.send(GroupDetailHeaderSideEffect.NavigateToCreatePost)
            }
            is GroupDetailHeaderEvent.OnHashIconClicked -> viewModelScope.launch {
                _sideEffect.send(GroupDetailHeaderSideEffect.ShowGroupCodeDialog)
            }
        }
    }

    /**
     * 그룹 정보 조회
     * 성공 시, uiState 업데이트
     * 실패 시, 에러 메세지 보여주기 (TODO)
     * */
    private fun loadGroupInfo() {
        viewModelScope.launch {
            getGroupInfoUseCase(groupId)
                .onSuccess { group ->
                    uiState = uiState.copy(group = group.let(Group::toUiModel))
                }
                .onFailure { }
        }
    }
}
