package com.fourbeat.domain.usecase.post

import com.fourbeat.domain.model.post.FileUploadUrl
import com.fourbeat.domain.model.post.FileUploadUrlRequest
import com.fourbeat.domain.repository.PostRepository
import javax.inject.Inject

class GetFileUploadUrlUseCase @Inject constructor(
    private val postRepository: PostRepository,
) {
    suspend operator fun invoke(request: FileUploadUrlRequest): Result<FileUploadUrl> =
        runCatching {
            postRepository.getFileUploadUrl(request)
        }
}
