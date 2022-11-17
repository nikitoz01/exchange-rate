package kg.sl.rate.core.model

data class PairRate(
    val timeLastUpdate: Long,
    val baseCode: String,
    val targetCode: String,
    val conversionRate: Float,
    val inverseConversionRate: Float = 1/conversionRate,
)
