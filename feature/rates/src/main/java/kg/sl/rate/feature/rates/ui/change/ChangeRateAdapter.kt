package kg.sl.rate.feature.rates.ui.change

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kg.sl.rate.core.model.CurrencyISO
import kg.sl.rate.feature.rates.R
import kg.sl.rate.feature.rates.databinding.ItemChangeRateBinding
import kg.sl.rate.feature.rates.databinding.ItemTitleChangeRateBinding
import java.util.*

class ChangeRateAdapter(
    private val callback: (String) -> Unit,
    private val currentCurrencyISO: CurrencyISO): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = CurrencyISO.values()

    class RecyclerViewHolder(val itemRecyclerBinding: ItemChangeRateBinding):
        RecyclerView.ViewHolder(itemRecyclerBinding.root)
    class RecyclerTitleViewHolder(val itemRecyclerTitleBinding: ItemTitleChangeRateBinding) :
        RecyclerView.ViewHolder(itemRecyclerTitleBinding.root)


    override fun getItemViewType(position: Int): Int {
        return when(position){
            0 -> R.layout.item_title_change_rate
            else -> R.layout.item_change_rate
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            R.layout.item_title_change_rate -> {
                RecyclerTitleViewHolder(ItemTitleChangeRateBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false))
            }
            R.layout.item_change_rate -> {
                RecyclerViewHolder(ItemChangeRateBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false))
            }
            else -> throw IllegalStateException("")
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder){
            is RecyclerTitleViewHolder -> holder.itemRecyclerTitleBinding.apply {
                textViewCurrentCurrencyName.text = currentCurrencyISO.fullName
                textViewCurrentCurrencyISO.text =  currentCurrencyISO.name
            }
            is RecyclerViewHolder -> holder.itemRecyclerBinding.apply {
                textViewCurrencyName.text = items[position].fullName
                textViewCurrencyISO.text = items[position].name

                constraintLayoutItemRate.setOnClickListener{
                    callback(items[position].name)
                }
            }
        }

    }


}