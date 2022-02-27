package com.example.sportsbettingapp.presenter.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsbettingapp.data.model.LeagueResponseModel
import com.example.sportsbettingapp.data.model.MatchBetModel


class MatchBetAdapter
// (private val list: List<MatchBetModel>) :
//    RecyclerView.Adapter<MatchBetAdapter.BetViewHolder>() {
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BetViewHolder {
//        val binding = ItemBetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return BetViewHolder(binding)
//
//    }
//
//    @SuppressLint("SimpleDateFormat")
//    override fun onBindViewHolder(holder: BetViewHolder, position: Int) {
//        holder.bind(list[position])
//    }
//
//    override fun getItemCount(): Int {
//        return list.size
//    }
//
//    fun refleshList(type: List<LeagueResponseModel>) {
//
//    }
//
//    inner class BetViewHolder(val binding: ItemBetBinding) : RecyclerView.ViewHolder(binding.root) {
//        val tempBookmaker = mutableListOf<String>()
//        val tempMarket = mutableListOf<String>()
//        fun bind(item: MatchBetModel) {
//
//            item.bookmakers?.forEachIndexed { index, bookmaker ->
//                tempBookmaker.add(bookmaker.title!!)
//            }
//        }
//    }
//}
