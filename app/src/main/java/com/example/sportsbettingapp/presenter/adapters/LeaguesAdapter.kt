package com.example.sportsbettingapp.presenter.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsbettingapp.data.model.LeagueResponseModel

import com.example.sportsbettingapp.databinding.ItemLeaguesBinding
import com.example.sportsbettingapp.ui.league.LeagueFragmentDirections


class LeaguesAdapter(
    private val list: MutableList<LeagueResponseModel>
) :
    RecyclerView.Adapter<LeaguesAdapter.LeaguesViewHolder>() {

    class LeaguesViewHolder(val binding: ItemLeaguesBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaguesViewHolder {
        val binding = ItemLeaguesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LeaguesViewHolder(binding)

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: LeaguesViewHolder, position: Int) {
        holder.binding.tournament.text = list.get(position).title
        holder.binding.outrights.text = list.get(position).hasOutrights.toString()
        holder.binding.root.setOnClickListener {
            val action = LeagueFragmentDirections.navigateToLeagueDetail(list!![position])
            Navigation.findNavController(it).navigate(action)
        }

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
