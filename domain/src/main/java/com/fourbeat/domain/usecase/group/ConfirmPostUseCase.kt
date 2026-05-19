package com.fourbeat.domain.usecase.group

import com.fourbeat.domain.model.post.Post
import com.fourbeat.domain.repository.GroupRepository
import javax.inject.Inject

class ConfirmPostUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
) {
    suspend operator fun invoke(tempId: Long, post: Post): Result<Unit> = runCatching {
        groupRepository.confirmPost(tempId, post)
    }
}
