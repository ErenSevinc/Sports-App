package com.example.sportsbettingapp.data.enum

enum class FirebaseEvent(private val eventName: String) {
    MatchDetail("MatchDetail"),
    AddCart("AddCart"),
    RemoveCart("RemoveCart");

    val title get() = this.eventName
}