package com.example.sportsbettingapp.ui.league.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsbettingapp.data.model.LeagueResponseModel
import com.example.sportsbettingapp.data.model.MatchScoreModel
import com.example.sportsbettingapp.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeagueDetailViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    var detail = MutableLiveData<LeagueResponseModel>()

    private val _matchList = MutableLiveData<List<MatchScoreModel>>()
    val matchList: LiveData<List<MatchScoreModel>> = _matchList

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading


    fun getMatches() {
        viewModelScope.launch {
            repository.getMatchesLeague(detail.value?.key!!)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<MatchScoreModel>>() {
                    override fun onSuccess(t: List<MatchScoreModel>) {
                        _matchList.value = t
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