package arc.umbrella.api

import arc.umbrella.domain.auth.Credentials
import arc.umbrella.domain.auth.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/auth/login")
    suspend fun login(@Body request: Credentials): Response<LoginResponse>
}
