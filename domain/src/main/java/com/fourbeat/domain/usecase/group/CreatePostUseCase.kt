package com.fourbeat.domain.usecase.group

import com.fourbeat.domain.exception.PostException
import com.fourbeat.domain.model.post.CreatePostRequest
import com.fourbeat.domain.model.post.FileUploadUrlRequest
import com.fourbeat.domain.model.post.Post
import com.fourbeat.domain.model.post.VideoFileInfo
import com.fourbeat.domain.repository.GroupRepository
import com.fourbeat.domain.usecase.post.GetFileUploadUrlUseCase
import com.fourbeat.domain.usecase.post.UploadVideoFileUseCase
import javax.inject.Inject

class CreatePostUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
    private val getFileUploadUrlUseCase: GetFileUploadUrlUseCase,
    private val uploadVideoFileUseCase: UploadVideoFileUseCase,
) {
    suspend operator fun invoke(
        groupId: Long,
        request: CreatePostRequest,
        videoFileInfo: VideoFileInfo? = null,
    ): Result<Post> = runCatching {
        val videoUrl = videoFileInfo?.let { (file, mimeType) ->
            getFileUploadUrlUseCase(FileUploadUrlRequest(file.name, mimeType))
                .getOrThrow()
                .also { uploadVideoFileUseCase(it.uploadUrl, file, mimeType).getOrThrow() }
                .videoUrl
        }

        runCatching {
            groupRepository.createPost(groupId, request.copy(videoUrl = videoUrl))
        }.recoverCatching {
            throw PostException.CreatePostFailed(it)
        }.getOrThrow()
    }
}
