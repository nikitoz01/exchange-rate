package kg.sl.rate.feature.rates.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.sl.rate.core.data.NetworkRepository
import kg.sl.rate.core.model.PairRate
import kg.sl.rate.core.model.ResourceResult
import kg.sl.rate.feature.rates.util.RouteConversion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatesViewModel @Inject constructor(private val repository: NetworkRepository,
                                         private val savedStateHandle: SavedStateHandle,): ViewModel() {

    private val _uiState: MutableStateFlow<ResourceResult<PairRate>> = MutableStateFlow(ResourceResult.Loading)
    // The UI collects from this StateFlow to get its state updates
    val uiState = _uiState.asStateFlow()


    var firstRate: String = "USD"
    var secondRate: String = "RUB"

    fun convertCurrency(inputValue: String, routeConversion: RouteConversion): String {
        try {
            val castInputValue = inputValue.toFloat()
            return when(routeConversion) {
                RouteConversion.TARGET -> (castInputValue * (_uiState.value as ResourceResult.Success).data.conversionRate)
                    .toString()
                RouteConversion.BASE -> (castInputValue / (_uiState.value as ResourceResult.Success).data.conversionRate)
                    .toString()
            }
        } catch (e: NumberFormatException){
            throw IllegalStateException()
        } catch (e:ArithmeticException){
            throw IllegalStateException()
        }
    }

    private fun getPairRate(){
        println("viewmodel $firstRate $secondRate")
        viewModelScope.launch {
            repository.getPairRate(firstRate, secondRate)
                .collect(){
                    _uiState.emit(it)
                }
        }
    }

    fun updateRate(newFirstRate: String = firstRate, newSecondRate: String = secondRate) {
        firstRate = newFirstRate
        secondRate = newSecondRate
        getPairRate()
    }

}
