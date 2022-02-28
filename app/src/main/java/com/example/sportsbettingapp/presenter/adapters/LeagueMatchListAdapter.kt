package com.example.sportsbettingapp.presenter.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsbettingapp.data.argumentmodel.MatchBetArgument
import com.example.sportsbettingapp.data.model.MatchScoreModel
import com.example.sportsbettingapp.databinding.ItemMatchBodyBinding
import com.example.sportsbettingapp.presenter.extension.toDate
import com.example.sportsbettingapp.ui.league.detail.LeagueDetailFragmentDirections

class LeagueMatchListAdapter :
    RecyclerView.Adapter<LeagueMatchListAdapter.LeagueMatchListViewHolder>() {

    private var list: MutableList<MatchScoreModel> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newList: MutableList<MatchScoreModel>) {
        list = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueMatchListViewHolder {
        val binding =
            ItemMatchBodyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LeagueMatchListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LeagueMatchListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class LeagueMatchListViewHolder(val binding: ItemMatchBodyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MatchScoreModel) {
            item.commenceTime?.let {
                binding.date.text = it.toDate()
            }
            binding.home.text = item.homeTeam
            binding.away.text = item.awayTeam
            binding.root.setOnClickListener {
                val action = LeagueDetailFragmentDirections.navigateToMatchDetail(
                    MatchBetArgument(
                        id = item.id,
                        sportKey = item.sportKey
                    )
                )
                Navigation.findNavController(it).navigate(action)
            }
            binding.bet.setOnClickListener {
                val action = LeagueDetailFragmentDirections.navigateToMatchDetail(
                    MatchBetArgument(
                        id = item.id,
                        sportKey = item.sportKey
                    )
                )
                Navigation.findNavController(it).navigate(action)
            }

        }
    }

}