package com.fourbeat.domain.usecase.media

import com.fourbeat.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLiveSongPermissionFlowUseCase @Inject constructor(
    private val mediaRepository: MediaRepository
) {
    operator fun invoke(): Flow<Boolean> = mediaRepository.getLiveSongPermissionFlow()
}
