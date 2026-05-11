package com.fourbeat.domain.usecase.user

import com.fourbeat.domain.model.user.RegisterRequest
import com.fourbeat.domain.repository.PreferenceRepository
import com.fourbeat.domain.repository.UserRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val preferenceRepository: PreferenceRepository,
) {
    suspend operator fun invoke(request: RegisterRequest): Result<Unit> = runCatching {
        userRepository.register(request)
            .also { uid -> preferenceRepository.saveUid(uid) }
    }
}
