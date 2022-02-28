package com.example.sportsbettingapp.ui.league.matchdetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.sportsbettingapp.data.argumentmodel.Bet
import com.example.sportsbettingapp.data.enum.FirebaseEvent
import com.example.sportsbettingapp.databinding.FragmentMatchDetailNewBinding
import com.example.sportsbettingapp.presenter.adapters.bets.BookmakersAdapter
import com.example.sportsbettingapp.presenter.extension.toDate
import com.example.sportsbettingapp.presenter.selectionbottomdialog.SelectionBottomSheetDialog
import com.example.sportsbettingapp.ui.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchDetailFragment : Fragment() {
    private lateinit var binding: FragmentMatchDetailNewBinding
    private val viewModel by viewModels<MatchDetailViewModel>()
    private val activityViewModel by activityViewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMatchDetailNewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val matchInformation = MatchDetailFragmentArgs.fromBundle(requireArguments()).matchBetArgs
        viewModel.getMatches(matchInformation)
        activityViewModel.setToolbarTitle("Match")
        activityViewModel.sendEvent(FirebaseEvent.MatchDetail, Pair("MATCH_ID", matchInformation.id))
        observeLiveData()
    }

    private fun showDialog() {
        SelectionBottomSheetDialog({
            findNavController().navigate(MatchDetailFragmentDirections.navigateToCoupon())
        }, {
            findNavController().popBackStack()
        })
            .setTitle("Warning")
            .show(childFragmentManager, null)
    }

    private fun observeLiveData() {
        viewModel.matchBet.observe(viewLifecycleOwner) { match ->
            match?.let {
                binding.homeAway.text = match.teams
                binding.date.text = match.commenceTime.toDate()

                val adapter =
                    BookmakersAdapter(match.bookmakers) { market, position, bookmakerPosition ->
                        val bet = Bet(
                            id = match.id,
                            date = match.commenceTime.toDate(),
                            home = match.homeTeam,
                            away = match.awayTeam,
                            marked = market.key,
                            bookmaker = match.bookmakers[bookmakerPosition].title,
                            oddName = market.outcomes[position].name,
                            oddPoint = market.outcomes[position].point,
                            oddPrice = market.outcomes[position].price,
                        )
                        Log.d("MarketSelect", bet.toString())
                        activityViewModel.addBet(bet)
                        activityViewModel.sendEvent(
                            FirebaseEvent.AddCart,
                            Pair("BET_ID", bet.id),
                            Pair("BET_ITEM", bet.oddName)
                        )
                        showDialog()
                    }
                binding.bookmakerList.adapter = adapter
            }
        }
        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                if (it) {
                    binding.tvError.visibility = View.VISIBLE
                } else {
                    binding.tvError.visibility = View.GONE
                }
            }
        }
        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            loading?.let {
                if (it) {
                    binding.loading.visibility = View.VISIBLE
                    binding.tvError.visibility = View.GONE
                    binding.bookmakerList.visibility = View.GONE
                } else {
                    binding.loading.visibility = View.GONE
                }
            }
        }
    }
}