package kg.sl.rate.core.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kg.sl.rate.core.model.PairRate

@JsonClass(generateAdapter = true)
data class NetworkPairRate(
    @Json(name = "time_last_update_unix")
    val timeLastUpdate: Long,
    @Json(name = "base_code")
    val baseCode: String,
    @Json(name = "target_code")
    val targetCode: String,
    @Json(name = "conversion_rate")
    val conversionRate: Float
)

fun NetworkPairRate.asUiModel()=PairRate(
    timeLastUpdate = timeLastUpdate,
    baseCode = baseCode,
    targetCode = targetCode,
    conversionRate = conversionRate
)