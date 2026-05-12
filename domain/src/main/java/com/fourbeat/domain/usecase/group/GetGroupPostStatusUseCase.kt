package com.fourbeat.domain.usecase.group

import com.fourbeat.domain.model.group.MyPostStatus
import com.fourbeat.domain.repository.GroupRepository
import javax.inject.Inject

class GetGroupPostStatusUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
) {
    suspend operator fun invoke(groupId: Long): Result<MyPostStatus> = runCatching {
        groupRepository.getGroupPostStatus(groupId)
    }
}
