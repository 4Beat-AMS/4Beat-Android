package com.fourbeat.domain.usecase.group

import com.fourbeat.domain.model.post.CreatePostRequest
import com.fourbeat.domain.model.user.User
import com.fourbeat.domain.repository.GroupRepository
import javax.inject.Inject

class InsertOptimisticPostUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
) {
    suspend operator fun invoke(
        groupId: Long,
        date: String,
        member: User,
        request: CreatePostRequest,
        filePath: String,
    ): Result<Long> = runCatching {
        groupRepository.insertOptimisticPost(
            groupId = groupId,
            date = date,
            member = member,
            request = request,
            filePath = filePath,
        )
    }
}
