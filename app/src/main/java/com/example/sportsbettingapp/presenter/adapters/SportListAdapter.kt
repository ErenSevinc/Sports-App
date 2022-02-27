package com.example.sportsbettingapp.presenter.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsbettingapp.R
import com.example.sportsbettingapp.databinding.ItemSportBinding

class SportListAdapter(
    val list: MutableList<String>,
    val callback: (group: String) -> Unit
) :
    RecyclerView.Adapter<SportListAdapter.SportViewHolder>() {

    class SportViewHolder(val binding: ItemSportBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportViewHolder {
        val binding = ItemSportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SportViewHolder(binding)

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: SportViewHolder, position: Int) {
        val str = list[position].replace(" ", "\n")
        holder.binding.sport.text = str
        holder.binding.sportImage.background = null
        when (list[position]) {
            "All" -> {
                holder.binding.sportImage.setImageResource(R.drawable.all)
            }
            "American Football" -> {
                holder.binding.sportImage.setImageResource(R.drawable.american_football)
            }
            "Aussie Rules" -> {
                holder.binding.sportImage.setImageResource(R.drawable.aussie)

            }
            "Baseball" -> {
                holder.binding.sportImage.setImageResource(R.drawable.baseball)
            }
            "Basketball" -> {
                holder.binding.sportImage.setImageResource(R.drawable.basketball)
            }
            "Cricket" -> {
                holder.binding.sportImage.setImageResource(R.drawable.cricket)
            }
            "Golf" -> {
                holder.binding.sportImage.setImageResource(R.drawable.golf)
            }
            "Ice Hockey" -> {
                holder.binding.sportImage.setImageResource(R.drawable.hockey)
            }
            "Mixed Martial Arts" -> {
                holder.binding.sportImage.setImageResource(R.drawable.mma)
            }
            "Rugby League" -> {
                holder.binding.sportImage.setImageResource(R.drawable.rugby)
            }
            "Soccer" -> {
                holder.binding.sportImage.setImageResource(R.drawable.football_empty)
            }
            else -> {
                holder.binding.sportImage.setImageResource(R.drawable.more)
            }
        }

        holder.binding.root.setOnClickListener {
            callback(list[position])
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refleshList(newList: List<String>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}
