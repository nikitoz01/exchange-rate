package kg.sl.rate.feature.rates.ui.change

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import kg.sl.rate.core.model.CurrencyISO
import kg.sl.rate.feature.rates.R
import kg.sl.rate.feature.rates.databinding.FragmentChangeRateBinding
import kg.sl.rate.feature.rates.databinding.FragmentRatesBinding

class ChangeRateFragment : DialogFragment() {

    private var currentCurrencyISO: String? = null
    private var targetRate: String? = null

    private var _viewBinding: FragmentChangeRateBinding? = null
    private val viewBinding
        get() = _viewBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currentCurrencyISO = it.getString("currency_iso")
            targetRate = it.getString("target_rate")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _viewBinding = FragmentChangeRateBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.apply {
            recyclerViewRatesChange.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ChangeRateAdapter(::callback, CurrencyISO.valueOf(currentCurrencyISO!!))
            }
        }
    }

    private fun callback(currencyName: String) {
        setFragmentResult("new_rate", bundleOf(targetRate!! to currencyName))
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String,param2: String) =
            ChangeRateFragment().apply {
                arguments = Bundle().apply {
                    putString("currency_iso", param1)
                    putString("target_rate", param2)
                }
            }
    }
}

