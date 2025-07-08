package arc.umbrella

import arc.umbrella.api.AuthApi
import arc.umbrella.api.PromocodeApi
import arc.umbrella.domain.auth.Credentials
import arc.umbrella.interceptor.AuthInterceptor
import arc.umbrella.repository.TokenRepository
import arc.umbrella.repository.impl.CaffeineTokenRepository
import arc.umbrella.service.AuthService
import arc.umbrella.service.impl.DefaultAuthService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UcClient(
    val promocodeApi: PromocodeApi
) {
    class Builder(val credentials: Credentials) {
        var tokenRepository: TokenRepository? = null
        var authService: AuthService? = null

        fun withTokenRepository(repository: TokenRepository) = apply {
            this.tokenRepository = repository
        }

        fun withAuthService(service: AuthService) = apply {
            this.authService = service
        }

        fun build(): UcClient {
            val repo = tokenRepository ?: CaffeineTokenRepository()

            val basicClient = OkHttpClient.Builder().build()

            val retrofitForAuth = Retrofit.Builder()
                .baseUrl("https://uc.zone/")
                .client(basicClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val authApi = retrofitForAuth.create(AuthApi::class.java)
            val authSvc = authService ?: DefaultAuthService(authApi, credentials)

            val authClient = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(repo, authSvc))
                .build()

            val mainRetrofit = Retrofit.Builder()
                .baseUrl("https://uc.zone/")
                .client(authClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val promocodeApi = mainRetrofit.create(PromocodeApi::class.java)

            return UcClient(promocodeApi)
        }
    }

    companion object {
        fun builder(credentials: Credentials) = Builder(credentials)
    }
}