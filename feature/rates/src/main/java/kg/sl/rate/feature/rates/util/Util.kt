package kg.sl.rate.feature.rates.util

import android.text.Editable

fun String.asEditable() = Editable.Factory.getInstance().newEditable(this)

enum class RouteConversion(){
    BASE,
    TARGET
}