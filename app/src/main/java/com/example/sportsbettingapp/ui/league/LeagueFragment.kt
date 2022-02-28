package com.example.sportsbettingapp.ui.league

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsbettingapp.presenter.adapters.LeaguesAdapter
import com.example.sportsbettingapp.presenter.adapters.SportListAdapter
import com.example.sportsbettingapp.databinding.FragmentLeagueBinding
import com.example.sportsbettingapp.ui.HomeViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LeagueFragment : Fragment() {
    private lateinit var binding: FragmentLeagueBinding

    //    private val adapter = BetListAdapter(listOf())
    private lateinit var leaguesAdapter: LeaguesAdapter
    private lateinit var sportsAdapter: SportListAdapter
    private val viewModel by viewModels<LeagueViewModel>()
    private val activityViewModel by activityViewModels<HomeViewModel>()

    @Inject
    lateinit var analytics: FirebaseAnalytics

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentLeagueBinding.inflate(inflater, container, false)
        viewModel.getAllSportsAndLeagues()
        activityViewModel.setToolbarTitle("Leagues")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        leaguesAdapter = LeaguesAdapter(mutableListOf())
        sportsAdapter = SportListAdapter(
            mutableListOf(
                "All",
                "Soccer",
                "Basketball",
                "Ice Hockey",
                "American Football",
                "Cricket",
                "Aussie Rules",
                "Baseball",
                "Golf",
                "Mixed Martial Arts",
                "Rugby League",
            )
        ) {
            if (it == "All") {
                viewModel.tournametList.value?.let { item -> leaguesAdapter.refleshList(item) }
            } else {
                leaguesAdapter.refleshList(viewModel.getSelectedTournament(it))
            }
        }
        binding.rvSports.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvSports.adapter = sportsAdapter
        binding.rvTournament.layoutManager = LinearLayoutManager(context)
        binding.rvTournament.adapter = leaguesAdapter
        observeLiveData()
    }


    private fun observeLiveData() {
        viewModel.tournametList.observe(viewLifecycleOwner) {
            binding.rvTournament.visibility = View.VISIBLE
            leaguesAdapter.refleshList(it.toMutableList())
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
                    binding.rvTournament.visibility = View.GONE
                } else {
                    binding.loading.visibility = View.GONE
                }
            }
        }
    }
}


