package com.fourbeat.domain.usecase.group

import com.fourbeat.domain.repository.GroupRepository
import javax.inject.Inject

class JoinGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
) {
    suspend operator fun invoke(code: String): Result<Long> = runCatching {
        groupRepository.joinGroup(code).id
    }
}
