package com.example.sportsbettingapp.presenter.adapters.bets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsbettingapp.data.model.Bookmaker
import com.example.sportsbettingapp.data.model.Market
import com.example.sportsbettingapp.databinding.ItemBookmakerNewBinding

class BookmakersAdapter(
    val list: List<Bookmaker>,
    private val listener: (market: Market, position: Int, bookmakerPosition: Int) -> Unit
) : RecyclerView.Adapter<BookmakersAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBookmakerNewBinding.inflate(inflater, null, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item, listener)
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(val binding: ItemBookmakerNewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(bookmaker: Bookmaker, listener: (market: Market, position: Int, bookmakerPosition: Int) -> Unit) {
            binding.bookmakerTitle.text = bookmaker.title
            val adapter = OddsAdapter(bookmaker.markets, list.indexOf(bookmaker) , listener)
            binding.marketList.adapter = adapter
        }

    }
}