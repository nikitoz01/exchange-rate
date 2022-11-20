package kg.sl.rate.feature.rates.ui

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kg.sl.rate.core.model.PairRate
import kg.sl.rate.core.model.ResourceResult
import kg.sl.rate.feature.rates.databinding.FragmentRatesBinding
import kg.sl.rate.feature.rates.ui.change.ChangeRateFragment
import kg.sl.rate.feature.rates.util.RouteConversion
import kg.sl.rate.feature.rates.util.asEditable
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RatesFragment : Fragment() {

    private val viewModel: RatesViewModel by viewModels()

    private var _viewBinding: FragmentRatesBinding? = null
    private val viewBinding
        get() = _viewBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        childFragmentManager.setFragmentResultListener("new_rate", this) { key, bundle ->
            val newFirstRate = bundle.getString("new_first_rate")
            val newSecondRate = bundle.getString("new_second_rate")

            if (newFirstRate != null) {
                viewModel.updateRate(newFirstRate = newFirstRate)
            } else if (newSecondRate != null) {
                viewModel.updateRate(newSecondRate = newSecondRate)
            }

        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("first_rate", viewModel.firstRate)
        outState.putString("second_rate", viewModel.secondRate)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = FragmentRatesBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewBinding.apply {

            rateBase.apply {
                setOnClickListener {
                    ChangeRateFragment.newInstance(text.toString(), "new_first_rate")
                        .show(childFragmentManager, "ChangeRateFragment")
                }
            }

            rateTarget.apply {
                setOnClickListener {
                    ChangeRateFragment.newInstance(text.toString(), "new_second_rate")
                        .show(childFragmentManager, "ChangeRateFragment")
                }
            }

            editTextFirstAmountCurrency.apply {
                doOnTextChanged { text, _, _, _ ->
                    if (isFocused)
                        convertCurrency(
                            editTextSecondAmountCurrency,
                            text.toString(),
                            RouteConversion.TARGET
                        )
                }
            }

            editTextSecondAmountCurrency.apply {
                doOnTextChanged { text, _, _, _ ->
                    if (isFocused)
                        convertCurrency(
                            editTextFirstAmountCurrency,
                            text.toString(),
                            RouteConversion.BASE
                        )
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                savedInstanceState?.let {
                    val firstRate = it.getString("first_rate", "USD")
                    val secondRate = it.getString("second_rate", "RUB")
                    viewModel.updateRate(firstRate, secondRate)
                }?: viewModel.updateRate("USD", "RUB")

                launch {
                    viewModel.uiState.collect() {
                        when (it) {
                            is ResourceResult.Loading -> {
                                showProgressBar()
                            }
                            is ResourceResult.Success -> {
                                hideProgressBar()
                                viewBinding.apply {
                                    convertCurrency(editTextSecondAmountCurrency,
                                    editTextFirstAmountCurrency.text.toString(),
                                    RouteConversion.TARGET)
                                }
                                showRate(it.data)
                            }
                            is ResourceResult.Error -> {
                                Log.e("RatesFragment:", it.exception.toString())
                            }
                        }
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    private fun convertCurrency(
        anotherEditText: EditText,
        inputValue: String,
        routeConversion: RouteConversion
    ) {
        if (validateInput(inputValue)) {
            val outputValue = viewModel.convertCurrency(inputValue, routeConversion)
            anotherEditText.text = outputValue.asEditable()
        } else {
            anotherEditText.text = "".asEditable()
        }
    }

    private fun validateInput(inputValue: String) =
        !(inputValue.isBlank() || inputValue == "," || inputValue == ".")


    private fun showRate(pairRate: PairRate) {
        viewBinding.apply {
            println(pairRate.toString())
            textViewConversionRate.text = pairRate.conversionRate.toString()
            rateBase.text = pairRate.baseCode
            rateTarget.text = pairRate.targetCode
        }
    }

    private fun showProgressBar() {
        viewBinding.apply {
            includeProgressBar.root.visibility = View.VISIBLE
            constraintLayoutRates.visibility = View.GONE
        }
    }

    private fun hideProgressBar() {
        viewBinding.apply {
            includeProgressBar.root.visibility = View.GONE
            constraintLayoutRates.visibility = View.VISIBLE
        }
    }
}
