package com.example.sportsbettingapp.presenter.adapters.bets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsbettingapp.data.model.Bookmaker
import com.example.sportsbettingapp.data.model.Market
import com.example.sportsbettingapp.databinding.ItemBookmakerNewBinding
import com.example.sportsbettingapp.databinding.ItemOddNewBinding

class OddsAdapter(
    val list: List<Market>,
    val rootPosition: Int,
    private val listener: (market: Market, position: Int, bookmakerPosition: Int) -> Unit
) : RecyclerView.Adapter<OddsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOddNewBinding.inflate(inflater, null, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item, listener)
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(val binding: ItemOddNewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(market: Market, listener: (market: Market, position: Int, bookmakerPosition: Int) -> Unit) {
            binding.marketKey.text = market.key.uppercase()

            if (market.outcomes.size == 3) {
                binding.bt1.text = market.outcomes[0].price.toString()
                binding.bt2.text = market.outcomes[1].price.toString()
                binding.btX.text = market.outcomes[2].price.toString()
                binding.btX.visibility = View.VISIBLE
            } else {
                binding.bt1.text = market.outcomes[0].price.toString()
                binding.bt2.text = market.outcomes[1].price.toString()
                binding.btX.visibility = View.GONE
            }

            binding.bt1.setOnClickListener {
                disableButton()
                listener.invoke(market, 0, rootPosition )
            }
            binding.bt2.setOnClickListener {
                disableButton()
                listener.invoke(market, 1, rootPosition)
            }
            binding.btX.setOnClickListener {
                disableButton()
                listener.invoke(market, 2, rootPosition)
            }
        }

        private fun disableButton(){
            binding.bt1.isEnabled = false
            binding.bt2.isEnabled = false
            binding.btX.isEnabled = false
        }
    }
}