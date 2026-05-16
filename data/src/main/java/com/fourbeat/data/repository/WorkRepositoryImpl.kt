package com.fourbeat.data.repository

import com.fourbeat.domain.model.post.CreatePostRequest
import com.fourbeat.domain.model.post.VideoFileInfo
import com.fourbeat.domain.repository.WorkRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkRepositoryImpl @Inject constructor() : WorkRepository {
    override fun enqueueCreatePost(
        groupId: Long,
        request: CreatePostRequest,
        videoFileInfo: VideoFileInfo?,
    ) {
        TODO("CreatePostWorker enqueue 구현 필요")
    }
}
