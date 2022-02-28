package com.example.sportsbettingapp.ui.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsbettingapp.data.argumentmodel.MatchBetArgument
import com.example.sportsbettingapp.data.model.MatchBetModel
import com.example.sportsbettingapp.data.model.MatchScoreModel
import com.example.sportsbettingapp.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingMatchViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _matchList = MutableLiveData<List<MatchBetModel>>()
    val matchList: LiveData<List<MatchBetModel>> = _matchList

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getMatches(title: String = "upcoming") {
        _loading.value = true
        viewModelScope.launch {
            repository.getMatches(title)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<MatchBetModel>>() {
                    override fun onSuccess(t: List<MatchBetModel>) {
                        _matchList.value = t
                        _loading.value = false
                        _error.value = false
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