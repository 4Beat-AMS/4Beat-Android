package com.fourbeat.domain.usecase.group

import com.fourbeat.domain.model.group.GroupFeed
import com.fourbeat.domain.repository.GroupRepository
import javax.inject.Inject

class GetGroupFeedUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
) {
    suspend operator fun invoke(groupId: Long, date: String): Result<GroupFeed> = runCatching {
        groupRepository.getGroupFeed(groupId = groupId, date = date)
    }
}
