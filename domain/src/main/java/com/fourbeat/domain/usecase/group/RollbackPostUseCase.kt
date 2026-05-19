package com.fourbeat.domain.usecase.group

import com.fourbeat.domain.repository.GroupRepository
import javax.inject.Inject

class RollbackPostUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
) {
    suspend operator fun invoke(tempId: Long): Result<Unit> = runCatching {
        groupRepository.rollbackPost(tempId)
    }
}
