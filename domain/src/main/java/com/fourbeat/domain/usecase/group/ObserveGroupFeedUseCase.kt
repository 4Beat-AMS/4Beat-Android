package com.fourbeat.domain.usecase.group

import com.fourbeat.domain.model.group.GroupFeed
import com.fourbeat.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveGroupFeedUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
) {
    operator fun invoke(groupId: Long, date: String): Flow<GroupFeed> =
        groupRepository.observeGroupFeed(groupId, date)
}
