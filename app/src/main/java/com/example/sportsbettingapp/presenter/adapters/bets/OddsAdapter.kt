package com.example.sportsbettingapp.presenter.adapters.bets

import android.annotation.SuppressLint
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
        val binding = ItemOddNewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item, listener)
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(val binding: ItemOddNewBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(
            market: Market,
            listener: (market: Market, position: Int, bookmakerPosition: Int) -> Unit
        ) {
            binding.marketKey.text = market.key.uppercase()

            if (market.outcomes.size == 3) {
                binding.bt1.text = "1\n${market.outcomes[0].price} $"
                binding.bt2.text = "2\n${market.outcomes[1].price} $"
                binding.btX.text = "X\n${market.outcomes[2].price} $"
                binding.btX.visibility = View.VISIBLE
            } else {
                binding.bt1.text =
                    if (market.outcomes[0].point != null)
                        "${market.outcomes[0].point}\n${market.outcomes[0].name}\n${market.outcomes[0].price} $"
                    else
                        "${market.outcomes[0].name}\n${market.outcomes[0].price} $"
                binding.bt2.text =
                    if (market.outcomes[1].point != null)
                        "${market.outcomes[1].point}\n${market.outcomes[1].name}\n${market.outcomes[1].price} $"
                    else
                        "${market.outcomes[1].name}\n${market.outcomes[1].price} $"
                binding.btX.visibility = View.GONE
            }

            binding.bt1.setOnClickListener {
                disableButton()
                listener.invoke(market, 0, rootPosition)
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

        private fun disableButton() {
            binding.bt1.isEnabled = false
            binding.bt2.isEnabled = false
            binding.btX.isEnabled = false
        }
    }
}