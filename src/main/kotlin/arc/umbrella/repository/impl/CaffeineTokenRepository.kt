package arc.umbrella.repository.impl

import arc.umbrella.domain.auth.TokenData
import arc.umbrella.repository.TokenRepository
import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import java.util.concurrent.TimeUnit

class CaffeineTokenRepository : TokenRepository {
    val tokenCache: Cache<String, TokenData> = Caffeine.newBuilder()
        .expireAfterWrite(20, TimeUnit.MINUTES)
        .build()

    override fun getToken(): TokenData? = tokenCache.getIfPresent("token")

    override fun saveToken(tokenData: TokenData) {
        tokenCache.put("token", tokenData)
    }

    override fun clearToken() {
        tokenCache.invalidate("token")
    }
}
