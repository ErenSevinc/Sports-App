package com.example.sportsbettingapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LeagueResponseModel(
    @SerializedName("key") val key: String? = null,
    @SerializedName("group") val group: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("active") val active: Boolean? = null,
    @SerializedName("has_outrights") val hasOutrights: Boolean? = null
) : Parcelable


