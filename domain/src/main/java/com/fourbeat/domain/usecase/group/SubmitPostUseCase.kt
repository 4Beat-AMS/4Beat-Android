package com.fourbeat.domain.usecase.group

import com.fourbeat.domain.model.post.CreatePostRequest
import com.fourbeat.domain.model.post.VideoFileInfo
import com.fourbeat.domain.repository.GroupRepository
import com.fourbeat.domain.repository.PreferenceRepository
import com.fourbeat.domain.repository.WorkRepository
import javax.inject.Inject

class SubmitPostUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
    private val workRepository: WorkRepository,
    private val preferenceRepository: PreferenceRepository,
) {
    suspend operator fun invoke(
        groupId: Long,
        request: CreatePostRequest,
        videoFileInfo: VideoFileInfo?,
    ): Result<Unit> = runCatching {
        val member = preferenceRepository.getUser()

        val tempId = groupRepository.insertOptimisticPost(
            groupId = groupId,
            member = member,
            request = request,
            filePath = videoFileInfo?.file?.absolutePath,
        )

        workRepository.enqueueCreatePost(
            groupId = groupId,
            tempId = tempId,
            request = request,
            videoFileInfo = videoFileInfo,
        )
    }
}
