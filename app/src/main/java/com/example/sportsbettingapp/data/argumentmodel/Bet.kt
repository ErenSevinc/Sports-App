package com.example.sportsbettingapp.data.argumentmodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Bet(
    val id: String? = null,
    val date: String? = null,
    val home: String? = null,
    val away: String? = null,
    val bookmaker: String? = null,
    val marked: String? = null,
    val oddName: String? = null,
    val oddPoint: Double? = null,
    val oddPrice: Double? = null,
) : Parcelable