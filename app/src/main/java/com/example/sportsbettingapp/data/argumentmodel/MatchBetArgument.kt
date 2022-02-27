package com.example.sportsbettingapp.data.argumentmodel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MatchBetArgument  (
    val id: String? = null,
    val sportKey: String? = null,
) : Parcelable