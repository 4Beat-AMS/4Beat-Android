package com.fourbeat.presentation.model.auth

import android.content.Context
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

object KakaoClient {
    suspend fun loginWithTalk(context: Context): OAuthUser = suspendCancellableCoroutine { continuation ->
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                when {
                    error != null -> if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        continuation.cancel(error)
                        return@loginWithKakaoTalk
                    } else {
                        loginWithAccount(context, continuation)
                    }
                    token != null -> getEmail(continuation)
                }
            }
        } else {
            loginWithAccount(context, continuation)
        }
    }

    private fun loginWithAccount(context: Context, continuation: CancellableContinuation<OAuthUser>) {
        UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
            when {
                error != null -> continuation.resumeWithException(error)
                token != null -> getEmail(continuation)
            }
        }
    }

    private fun getEmail(continuation: CancellableContinuation<OAuthUser>) {
        UserApiClient.instance.me { user, error ->
            when {
                error != null -> continuation.resumeWithException(error)
                user != null -> user.kakaoAccount?.email?.let { email ->
                    continuation.resume(
                        OAuthUser(
                            email = email,
                            nickname = user.kakaoAccount?.profile?.nickname
                        )
                    )
                } ?: continuation
                    .resumeWithException(IllegalStateException("카카오 이메일을 가져오는데 실패했어요"))
            }
        }
    }
}
