package arc.umbrella.interceptor

import arc.umbrella.repository.TokenRepository
import arc.umbrella.service.AuthService
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(
    val tokenRepository: TokenRepository,
    val authService: AuthService
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        var tokenData = tokenRepository.getToken()

        if (tokenData == null) {
            tokenData = runBlocking {
                authService.authenticate()
            } ?: throw IllegalStateException("Не удалось получить токен")
            tokenRepository.saveToken(tokenData)
        }

        val modifiedRequest = addAuthHeader(originalRequest, tokenData.accessToken)
        val response = chain.proceed(modifiedRequest)

        if (response.code == 401) {
            response.close()
            tokenRepository.clearToken()

            tokenData = runBlocking {
                authService.authenticate()
            } ?: throw IllegalStateException("Не удалось обновить токен")

            tokenRepository.saveToken(tokenData)
            return chain.proceed(addAuthHeader(originalRequest, tokenData.accessToken))
        }

        return response
    }

    fun addAuthHeader(request: Request, token: String): Request {
        return request.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
    }
}
