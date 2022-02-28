package com.example.sportsbettingapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportsbettingapp.data.argumentmodel.Bet
import com.example.sportsbettingapp.data.enum.FirebaseEvent
import com.example.sportsbettingapp.presenter.extension.toStringFormatted
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.DecimalFormat
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) : ViewModel() {

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val bets: MutableList<Bet> = mutableListOf()

    private val _betList = MutableLiveData(bets)
    val betList: LiveData<MutableList<Bet>> = _betList

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    fun sendEvent(event: FirebaseEvent, vararg params: Pair<String, String?>) {
        firebaseAnalytics.logEvent(event.title) {

            for (p in params) {
                p.second?.let {
                    param(p.first, it)
                }
            }
            /*
           params.forEach { p ->
               param(p.first, p.second ?: "")
           }
            */
        }
    }

    fun setToolbarTitle(text: String) {
        _title.value = text
    }

    fun addBet(bet: Bet) {
        bets.add(bet)
        _betList.value = bets
        _error.value = false
    }

    fun removeBet(index: Int) {
        bets.removeAt(index)
        _betList.value = bets
        if (bets.size == 0) {
            _error.value = true
        }
    }

    fun calculateBetTotal(): String {
        var total = 1.0
        bets.forEach { bet ->
            bet.oddPrice?.let {
                total *= it
            }
        }
        return total.toStringFormatted()
    }

}