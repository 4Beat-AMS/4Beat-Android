package com.fourbeat.domain.usecase.group

import com.fourbeat.domain.model.group.CreateGroupRequest
import com.fourbeat.domain.repository.GroupRepository
import javax.inject.Inject

class CreateGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
) {
    suspend operator fun invoke(request: CreateGroupRequest): Result<Long> = runCatching {
        groupRepository.createGroup(request).id
    }
}
