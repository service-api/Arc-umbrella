package arc.umbrella.repository

import arc.umbrella.domain.auth.TokenData

interface TokenRepository {
    fun getToken(): TokenData?
    fun saveToken(tokenData: TokenData)
    fun clearToken()
}
