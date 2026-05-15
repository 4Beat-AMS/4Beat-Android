package com.fourbeat.domain.exception

sealed class PostException(cause: Throwable) : Exception(cause) {
    class GetUploadUrlFailed(cause: Throwable) : PostException(cause)
    class VideoUploadFailed(cause: Throwable) : PostException(cause)
    class CreatePostFailed(cause: Throwable) : PostException(cause)
}
