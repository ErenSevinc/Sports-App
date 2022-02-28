package com.example.sportsbettingapp.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsbettingapp.data.model.MatchBetModel
import com.example.sportsbettingapp.databinding.FragmentUpcomingMatchBinding
import com.example.sportsbettingapp.presenter.adapters.MatchListAdapter
import com.example.sportsbettingapp.presenter.adapters.SportListAdapter
import com.example.sportsbettingapp.ui.HomeViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UpcomingMatchFragment : Fragment() {
    private lateinit var binding: FragmentUpcomingMatchBinding
    private lateinit var adapter: MatchListAdapter
    private lateinit var sportsAdapter: SportListAdapter
    private val viewModel by viewModels<UpcomingMatchViewModel>()
    private val activityViewModel by activityViewModels<HomeViewModel>()
    private lateinit var matchList: MutableList<MatchBetModel>

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

        setSearchView()
        observeLiveData()

    }

    private fun setSearchView() {
        binding.filterLayout.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.getMatches(it)
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it.isEmpty()) {
                        adapter.setItems(matchList)
                    } else if (it.length >= 3) {
                        val filteredMatches = matchList.filter { model ->
                            model.filteredByMatchFactor.contains(it, true)
                        }.toMutableList()
                        adapter.setItems(filteredMatches)
                    }
                }
                return true
            }
        })
    }

    fun setSportsAdapter() {

    }

    private fun observeLiveData() {
        viewModel.matchList.observe(viewLifecycleOwner) { list ->
            list?.let { it ->
                binding.rvMatches.visibility = View.VISIBLE
                binding.tvError.visibility = View.GONE
                matchList = it.toMutableList()
                adapter = MatchListAdapter()
                adapter.setItems(matchList)
                binding.rvMatches.adapter = adapter
                binding.filterLayout.isEnabled = true
                /*
                binding.rvSports.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                sportsAdapter = SportListAdapter(
                    mutableListOf("Upcoming", "Soccer", "Basketball", "Ice Hockey", "American Football", "Cricket", "Aussie Rules", "Baseball", "Golf", "Mixed Martial Arts", "Rugby League")
                ) { item ->

                *** working but service call limited ***
                    if (item == "Upcoming") {
                        viewModel.getMatches()
                    } else {
                        viewModel.getMatches(item.trim())
                    }

                }
                binding.rvSports.adapter = sportsAdapter
                */
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
                    binding.rvMatches.visibility = View.GONE

                } else {
                    binding.loading.visibility = View.GONE
                }
            }
        }
    }
}