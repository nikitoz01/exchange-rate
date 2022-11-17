package kg.sl.rate.feature.rates.ui.change

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kg.sl.rate.core.model.CurrencyISO
import kg.sl.rate.feature.rates.R
import java.util.*

class ChangeRateAdapter(private val callback: (String) -> Unit) : RecyclerView.Adapter<ChangeRateAdapter.ViewHolder>() {

    private val items = CurrencyISO.values()


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewCurrencyName: TextView
        val textViewCurrencyISO: TextView
        val constraintLayoutItemRate: ConstraintLayout
        init {
            textViewCurrencyName = view.findViewById(R.id.textViewCurrencyName)
            textViewCurrencyISO = view.findViewById(R.id.textViewCurrencyISO)
            constraintLayoutItemRate = view.findViewById(R.id.constraintLayoutItemRate)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_change_rate, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.apply {

            textViewCurrencyName.text = items[position].name
            textViewCurrencyISO.text = items[position].fullName

            constraintLayoutItemRate.setOnClickListener{
                callback(items[position].name)
            }
        }


    }


}