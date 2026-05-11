package com.fourbeat.domain.usecase.auth

import com.fourbeat.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String): Result<Unit> = runCatching {
        val uid = authRepository.login(email)
    }
}
