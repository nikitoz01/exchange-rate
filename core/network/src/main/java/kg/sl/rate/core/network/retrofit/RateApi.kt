package kg.sl.rate.core.network.retrofit

import kg.sl.rate.core.network.model.NetworkPairRate
import retrofit2.http.GET
import retrofit2.http.Path

interface RateApi {

    @GET("pair/{base}/{target}")
    suspend fun getPairRate(@Path("base") base: String, @Path("target") target: String): NetworkPairRate

}