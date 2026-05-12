package com.fourbeat.presentation.ui.main.creategroup

import com.fourbeat.domain.model.group.GroupMemberCount
import com.fourbeat.presentation.model.common.Validatable

data class CreateGroupUiState(
    val name: String = "",
    val selectedMemberCount: GroupMemberCount? = null,
    val isLoading: Boolean = false,
) : Validatable {
    override val isValid: Boolean
        get() = isLoading.not() &&
                name.length in 1..20 &&
                selectedMemberCount != null
}

sealed interface CreateGroupEvent {
    data class OnNameChanged(val name: String) : CreateGroupEvent
    data class OnMemberCountSelected(val memberCount: GroupMemberCount) : CreateGroupEvent
    data object OnCreateButtonClicked : CreateGroupEvent
    data object OnBackClicked : CreateGroupEvent
}

sealed interface CreateGroupSideEffect {
    data class NavigateToGroupDetail(val groupId: Long) : CreateGroupSideEffect
    data object NavigateToBack : CreateGroupSideEffect
}
