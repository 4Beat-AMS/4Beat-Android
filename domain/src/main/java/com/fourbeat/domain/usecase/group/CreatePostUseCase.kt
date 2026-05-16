package com.fourbeat.domain.usecase.group

import com.fourbeat.domain.exception.PostException
import com.fourbeat.domain.model.post.CreatePostRequest
import com.fourbeat.domain.model.post.Post
import com.fourbeat.domain.repository.GroupRepository
import javax.inject.Inject

class CreatePostUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
) {
    suspend operator fun invoke(
        groupId: Long,
        request: CreatePostRequest,
    ): Result<Post> = runCatching {
        groupRepository.createPost(groupId, request)
    }.recoverCatching {
        throw PostException.CreatePostFailed(it)
    }
}
