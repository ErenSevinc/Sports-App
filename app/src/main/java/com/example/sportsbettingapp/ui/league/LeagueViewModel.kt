package com.example.sportsbettingapp.ui.league


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsbettingapp.data.model.LeagueResponseModel
import com.example.sportsbettingapp.data.model.MatchBetModel
import com.example.sportsbettingapp.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeagueViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _tournametList = MutableLiveData<List<LeagueResponseModel>>()
    val tournametList: LiveData<List<LeagueResponseModel>> = _tournametList

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getAllSportsAndLeagues() {
        _loading.value = true
        viewModelScope.launch {
            repository.getAllSportsAndLeague()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<LeagueResponseModel>>() {
                    override fun onSuccess(t: List<LeagueResponseModel>) {
                        _tournametList.value = t
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

    fun getSelectedTournament(group: String): List<LeagueResponseModel> {
        val temp: List<LeagueResponseModel> = _tournametList.value?.filter {
            it.group == group
        } ?: emptyList()
        return temp
    }

}