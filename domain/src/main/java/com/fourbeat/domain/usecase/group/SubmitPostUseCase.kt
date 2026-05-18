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
        date: String,
        request: CreatePostRequest,
        filePath: String,
        videoFileInfo: VideoFileInfo?,
    ): Result<Unit> = runCatching {
        val member = preferenceRepository.getUser()

        val tempId = groupRepository.insertOptimisticPost(
            groupId = groupId,
            date = date,
            member = member,
            request = request,
            filePath = filePath,
        )

        workRepository.enqueueCreatePost(
            groupId = groupId,
            tempId = tempId,
            request = request,
            videoFileInfo = videoFileInfo,
        )
    }
}
