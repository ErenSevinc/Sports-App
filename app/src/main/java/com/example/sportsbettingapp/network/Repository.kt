package com.example.sportsbettingapp.network

import com.example.sportsbettingapp.data.model.LeagueResponseModel
import com.example.sportsbettingapp.data.model.MatchBetModel
import com.example.sportsbettingapp.data.model.MatchScoreModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) : ApiService {
    override fun getAllSportsAndLeague(apiKey: String): Single<List<LeagueResponseModel>> {
        return apiService.getAllSportsAndLeague(Constant.API_KEY)
    }

    override fun getMatches(
        sportType: String,
        apiKey: String,
        region: String,
        markets: String
    ): Single<List<MatchBetModel>> {
        return apiService.getMatches(sportType)
    }

    override fun getMatchesLeague(
        leagueKey: String,
        apiKey: String
    ): Single<List<MatchScoreModel>> {
        return apiService.getMatchesLeague(leagueKey)
    }


}