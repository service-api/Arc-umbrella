package arc.umbrella.api

import arc.umbrella.domain.promocode.Promocode
import arc.umbrella.domain.promocode.PromocodeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PromocodeApi {
    @POST("api/promocode")
    suspend fun activate(@Body request: Promocode): Response<PromocodeResponse>
}
