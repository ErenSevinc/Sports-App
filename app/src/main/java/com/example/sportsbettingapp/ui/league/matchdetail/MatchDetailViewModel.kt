package com.example.sportsbettingapp.ui.league.matchdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsbettingapp.data.argumentmodel.MatchBetArgument
import com.example.sportsbettingapp.data.model.Bookmaker
import com.example.sportsbettingapp.data.model.Market
import com.example.sportsbettingapp.data.model.MatchBetModel
import com.example.sportsbettingapp.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchDetailViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _matchBet = MutableLiveData<MatchBetModel>()
    val matchBet: LiveData<MatchBetModel> = _matchBet

    private val _bookmakerList = MutableLiveData<MutableList<String>>()
    val bookmakerList: LiveData<MutableList<String>> = _bookmakerList

    private val _marketList = MutableLiveData<List<String>>()
    val marketList: LiveData<List<String>> = _marketList

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading


    fun getMatches(item: MatchBetArgument) {
        viewModelScope.launch {
            repository.getMatches(item.sportKey!!)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<MatchBetModel>>() {
                    override fun onSuccess(t: List<MatchBetModel>) {
                        _matchBet.value = t.find {
                            it.id == item.id
                        }

                        _error.value = false
                        _loading.value = false

                    }

                    override fun onError(e: Throwable) {
                        _loading.value = false
                        _error.value = true
                        e.printStackTrace()
                    }
                })
        }
    }

}