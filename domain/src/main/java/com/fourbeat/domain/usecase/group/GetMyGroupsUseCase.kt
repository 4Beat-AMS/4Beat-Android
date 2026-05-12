package com.fourbeat.domain.usecase.group

import com.fourbeat.domain.model.group.Group
import com.fourbeat.domain.repository.GroupRepository
import javax.inject.Inject

class GetMyGroupsUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
) {
    suspend operator fun invoke(): Result<List<Group>> = runCatching {
        groupRepository.getMyGroups()
    }
}
