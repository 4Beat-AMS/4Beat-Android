package com.fourbeat.domain.usecase.work

import com.fourbeat.domain.model.post.CreatePostRequest
import com.fourbeat.domain.model.post.VideoFileInfo
import com.fourbeat.domain.repository.WorkRepository
import javax.inject.Inject

class EnqueueCreatePostUseCase @Inject constructor(
    private val workRepository: WorkRepository,
) {
    operator fun invoke(
        groupId: Long,
        tempId: Long,
        request: CreatePostRequest,
        videoFileInfo: VideoFileInfo?,
    ) = workRepository.enqueueCreatePost(groupId, tempId, request, videoFileInfo)
}
