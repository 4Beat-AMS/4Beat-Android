package com.fourbeat.domain.usecase.group

import com.fourbeat.domain.model.group.Group
import com.fourbeat.domain.repository.GroupRepository
import javax.inject.Inject

class GetGroupInfoUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
) {
    suspend operator fun invoke(groupId: Long): Result<Group> = runCatching {
        groupRepository.getGroupInfo(groupId)
    }
}
