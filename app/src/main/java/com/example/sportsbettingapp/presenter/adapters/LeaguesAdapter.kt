package com.example.sportsbettingapp.presenter.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsbettingapp.R
import com.example.sportsbettingapp.data.model.LeagueResponseModel
import com.example.sportsbettingapp.data.model.MatchScoreModel

import com.example.sportsbettingapp.databinding.ItemLeaguesBinding
import com.example.sportsbettingapp.ui.league.LeagueFragmentDirections


class LeaguesAdapter(
    private val list: MutableList<LeagueResponseModel>
) :
    RecyclerView.Adapter<LeaguesAdapter.LeaguesViewHolder>() {

    inner class LeaguesViewHolder(val binding: ItemLeaguesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LeagueResponseModel) {
            binding.tournament.text = item.title
            binding.outrights.text = if (item.hasOutrights == true) "Not Active" else "Active"
            binding.root.isEnabled = item.hasOutrights != true
            binding.cardView.setCardBackgroundColor(
                if (item.hasOutrights == true) binding.root.context.getColor(R.color.red_200)
                else binding.root.context.getColor(R.color.base_card)
            )
            binding.root.setOnClickListener {
                val action = LeagueFragmentDirections.navigateToLeagueDetail(item)
                Navigation.findNavController(it).navigate(action)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaguesViewHolder {
        val binding = ItemLeaguesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LeaguesViewHolder(binding)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: LeaguesViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refleshList(newList: List<LeagueResponseModel>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}
