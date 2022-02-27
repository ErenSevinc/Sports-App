package com.example.sportsbettingapp.ui.upcoming

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.sportsbettingapp.data.argumentmodel.Bet
import com.example.sportsbettingapp.databinding.FragmentUpcomingMatchBinding
import com.example.sportsbettingapp.presenter.adapters.LeagueMatchListAdapter
import com.example.sportsbettingapp.presenter.adapters.MatchListAdapter
import com.example.sportsbettingapp.presenter.adapters.bets.BookmakersAdapter
import com.example.sportsbettingapp.presenter.extension.toDate
import com.example.sportsbettingapp.ui.home.HomeViewModel
import com.example.sportsbettingapp.ui.league.matchdetail.MatchDetailFragmentArgs
import com.example.sportsbettingapp.ui.league.matchdetail.MatchDetailFragmentDirections
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UpcomingMatchFragment : Fragment() {
    private lateinit var binding: FragmentUpcomingMatchBinding
    private val viewModel by viewModels<UpcomingMatchViewModel>()
    private val activityViewModel by activityViewModels<HomeViewModel>()

    @Inject
    lateinit var analytics: FirebaseAnalytics

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentUpcomingMatchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getMatches()
        activityViewModel.setToolbarTitle("Upcoming Matches")
        observeLiveData()
    }

    private fun observeLiveData() {
//        activityViewModel.cartLiveData.observe(viewLifecycleOwner) {
//            val adapter = CardAdapter(it){ item ->
//                activityViewModel.removeCart(item)
//            }
//            binding.cartList.adapter = adapter
//        }

        viewModel.matchList.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()){
                binding.loading.visibility = View.GONE
                binding.tvError.visibility = View.GONE
            }
            binding.rvMatches.visibility = View.VISIBLE
            val adapter = MatchListAdapter(it.toMutableList())
            binding.rvMatches.adapter = adapter
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
                    binding.rvMatches.visibility = View.GONE

                } else {
                    binding.loading.visibility = View.GONE
                }
            }
        }
    }
}