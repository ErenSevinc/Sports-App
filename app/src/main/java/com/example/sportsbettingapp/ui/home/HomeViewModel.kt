package com.example.sportsbettingapp.ui.home

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportsbettingapp.data.argumentmodel.Bet
import com.example.sportsbettingapp.data.enum.FirebaseEvent
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.ParametersBuilder
import com.google.firebase.analytics.ktx.logEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val firebaseAnalytics: FirebaseAnalytics
) : ViewModel() {

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val bets: MutableList<Bet> = mutableListOf()

    private val _betList = MutableLiveData(bets)
    val betList: LiveData<MutableList<Bet>> = _betList

    fun sendEvent(event: FirebaseEvent, vararg params: Pair<String, String?>) {
        firebaseAnalytics.logEvent(event.title) {
            for (p in params) {
                p.second?.let {
                    param(p.first, it)
                }
            }
        }
    }

    fun setToolbarTitle(text: String) {
        _title.value = text
    }

    fun addBet(bet: Bet) {
        bets.add(bet)
        _betList.value = bets
    }

    fun removeBet(index: Int) {
        bets.removeAt(index)
        _betList.value = bets
    }

}