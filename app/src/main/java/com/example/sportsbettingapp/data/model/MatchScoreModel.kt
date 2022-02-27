package com.example.sportsbettingapp.data.model

import com.google.gson.annotations.SerializedName

data class MatchScoreModel(
    @SerializedName("id") val id: String?,
    @SerializedName("sport_key") val sportKey: String?,
    @SerializedName("sport_title") val sportTitle: String?,
    @SerializedName("commence_time") val commenceTime: String?,
    @SerializedName("completed") val completed: Boolean?,
    @SerializedName("home_team") val homeTeam: String?,
    @SerializedName("away_team") val awayTeam: String?,
    @SerializedName("scores") val scores: MutableList<Score>? = null,
    @SerializedName("last_update") val lastUpdate: String? = null
){
    val teams get() = "$homeTeam $awayTeam"
}

data class Score(
    @SerializedName("name") val name: String?,
    @SerializedName("score") val score: String?
)
