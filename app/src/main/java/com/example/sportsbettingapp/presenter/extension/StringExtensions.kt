package com.example.sportsbettingapp.presenter.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(): String {
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val date = parser.parse(this)
    return dateFormat.format(date)
}
