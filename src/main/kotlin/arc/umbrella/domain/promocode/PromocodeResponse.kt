package arc.umbrella.domain.promocode

import com.google.gson.annotations.SerializedName

data class PromocodeResponse(
    val code: Int,
    @SerializedName("error_code")
    val errorCode: String,
    val message: String
)