package com.example.sportsbettingapp.presenter.adapters.coupon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsbettingapp.data.argumentmodel.Bet
import com.example.sportsbettingapp.databinding.ItemCouponBinding

class CouponAdapter(
    val list: MutableList<Bet>,
    //private val listener: (market: Market, position: Int) -> Unit
) : RecyclerView.Adapter<CouponAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCouponBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CouponAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(val binding: ItemCouponBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Bet) {
            binding.tvMatchId.text = item.id
            binding.tvMatchHomeName.text = item.home
            binding.tvMatchAwayName.text = item.away
            binding.tvMatchDate.text = item.date
            binding.tvBetBookmaker.text = item.bookmaker
            binding.tvBetMarket.text = item.marked
            binding.tvBetName.text = item.oddName
            binding.tvBetOdd.text = "${item.oddPrice} $"
            binding.tvBetPoint.text = item.oddPoint.toString()
            if (item.oddPoint == null){
                binding.tvBetPoint.visibility = View.GONE
            }
        }
    }
}