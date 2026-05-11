package com.fourbeat.domain.usecase.auth

import com.fourbeat.domain.repository.AuthRepository
import com.fourbeat.domain.repository.PreferenceRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val preferenceRepository: PreferenceRepository,
) {
    suspend operator fun invoke(email: String): Result<Unit> = runCatching {
        authRepository.login(email)
            .also { uid -> preferenceRepository.saveUid(uid) }
    }
}
