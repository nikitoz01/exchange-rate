package kg.sl.rate.core.data

import kg.sl.rate.core.data.di.Dispatcher
import kg.sl.rate.core.data.di.Dispatchers
import kg.sl.rate.core.model.PairRate
import kg.sl.rate.core.model.ResourceResult
import kg.sl.rate.core.network.model.asUiModel
import kg.sl.rate.core.network.retrofit.RateApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val rateApi: RateApi,
    @Dispatcher(Dispatchers.IO)
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getPairRate(base: String, target: String): Flow<ResourceResult<PairRate>> = flow {
        emit(ResourceResult.Loading)
        emit(ResourceResult.Success(rateApi.getPairRate(base, target).asUiModel()))
    }
        .flowOn(ioDispatcher)
        .catch { emit(ResourceResult.Error(it)) }


}