package com.example.sportsbettingapp.network

import com.example.sportsbettingapp.data.model.LeagueResponseModel
import com.example.sportsbettingapp.data.model.MatchBetModel
import com.example.sportsbettingapp.data.model.MatchScoreModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("sports/")
    fun getAllSportsAndLeague(@Query("apiKey") apiKey: String = Constant.API_KEY): Single<List<LeagueResponseModel>>

    @GET("sports/{sportType}/odds/")
    fun getMatches(
        @Path("sportType") sportType: String,
        @Query("apiKey") apiKey: String = Constant.API_KEY,
        @Query("regions") region: String = Constant.REGION,
        @Query("markets") markets: String = Constant.MARKETS
    ): Single<List<MatchBetModel>>

    @GET("sports/{leagueKey}/scores/")
    fun getMatchesLeague(
        @Path("leagueKey") leagueKey: String,
        @Query("apiKey") apiKey: String = Constant.API_KEY,
    ) : Single<List<MatchScoreModel>>

    //https://api.the-odds-api.com/v4/sports/upcoming/odds/?apiKey=1b5d935ce8a722b9e49ae87706a6cdbf&regions=eu&markets=h2h,spreads,totals
}
