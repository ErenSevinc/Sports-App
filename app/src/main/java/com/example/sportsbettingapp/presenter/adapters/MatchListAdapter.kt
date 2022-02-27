package com.example.sportsbettingapp.presenter.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsbettingapp.data.argumentmodel.MatchBetArgument
import com.example.sportsbettingapp.data.model.MatchBetModel
import com.example.sportsbettingapp.databinding.ItemMatchBodyBinding
import com.example.sportsbettingapp.presenter.adapters.bets.BookmakersAdapter
import com.example.sportsbettingapp.presenter.extension.toDate
import com.example.sportsbettingapp.ui.league.detail.LeagueDetailFragmentDirections
import com.example.sportsbettingapp.ui.upcoming.UpcomingMatchFragmentDirections

class MatchListAdapter(private val list: List<MatchBetModel>) : RecyclerView.Adapter<MatchListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemMatchBodyBinding.inflate(inflater, parent, false))

    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun refleshList(list: List<Pair<MatchBetModel, String>>) {

    }

    inner class ViewHolder(val binding: ItemMatchBodyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: MatchBetModel) {
            binding.date.text = item.commenceTime.toDate()
            binding.sportTitle.text = item.sportTitle
            binding.home.text = item.homeTeam
            binding.away.text = item.awayTeam
            binding.bet.setOnClickListener {
                val action = UpcomingMatchFragmentDirections.navigateToBetDetail(
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