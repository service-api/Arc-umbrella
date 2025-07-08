package arc.umbrella.domain.auth

data class TokenData(
    val accessToken: String,
    val refreshToken: String,
    val createdAt: Long = System.currentTimeMillis(),
    val expiresIn: Long = 20 * 60 * 1000L
) {
    val isExpired: Boolean
        get() = System.currentTimeMillis() - createdAt >= expiresIn
}
