package arc.umbrella.service

import arc.umbrella.domain.auth.TokenData

interface AuthService {
    suspend fun authenticate(): TokenData?
}
