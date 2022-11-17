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

    fun getPairRate(baseRate: String, targetRate: String){
        println("viewmodel $baseRate $targetRate")
        viewModelScope.launch {
            repository.getPairRate(baseRate, targetRate)
                .collect(){
                    _uiState.emit(it)
                }
        }
    }
}
