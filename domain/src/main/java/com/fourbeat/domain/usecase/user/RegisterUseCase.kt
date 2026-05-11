package com.fourbeat.domain.usecase.user

import com.fourbeat.domain.model.user.RegisterRequest
import com.fourbeat.domain.repository.UserRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(request: RegisterRequest): Result<Unit> = runCatching {
        val uid = userRepository.register(request)
    }
}
