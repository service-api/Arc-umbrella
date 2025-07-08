package arc.umbrella.service.impl

import arc.umbrella.api.AuthApi
import arc.umbrella.domain.auth.Credentials
import arc.umbrella.domain.auth.LoginResponse
import arc.umbrella.domain.auth.TokenData
import arc.umbrella.service.AuthService

class DefaultAuthService(
    val authApi: AuthApi,
    val credentials: Credentials
) : AuthService {
    override suspend fun authenticate(): TokenData? {
        val response = authApi.login(credentials)
        return if (response.isSuccessful) {
            response.body()?.toTokenData()
        } else {
            null
        }
    }


    fun LoginResponse.toTokenData() = TokenData(accessToken, refreshToken)
}