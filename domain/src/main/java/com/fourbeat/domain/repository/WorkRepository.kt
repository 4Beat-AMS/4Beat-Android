package com.fourbeat.domain.repository

import com.fourbeat.domain.model.post.CreatePostRequest
import com.fourbeat.domain.model.post.VideoFileInfo

interface WorkRepository {
    fun enqueueCreatePost(
        groupId: Long,
        tempId: Long,
        request: CreatePostRequest,
        videoFileInfo: VideoFileInfo?,
    )
}
