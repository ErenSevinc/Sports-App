package com.example.sportsbettingapp.ui.league.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.sportsbettingapp.data.model.MatchScoreModel
import com.example.sportsbettingapp.presenter.adapters.LeagueMatchListAdapter
import com.example.sportsbettingapp.databinding.FragmentLeagueDetailBinding
import com.example.sportsbettingapp.ui.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeagueDetailFragment : Fragment() {
    private lateinit var binding: FragmentLeagueDetailBinding
    private lateinit var adapter: LeagueMatchListAdapter
    private val viewModel by viewModels<LeagueDetailViewModel>()
    private val activityViewModel by activityViewModels<HomeViewModel>()
    private lateinit var matchList: MutableList<MatchScoreModel>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        init()
        binding = FragmentLeagueDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activityViewModel.setToolbarTitle("Selected League")
        binding.group.text = viewModel.detail.value?.group
        binding.title.text = viewModel.detail.value?.title
        viewModel.getMatches()
        setSearchView()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.matchList.observe(viewLifecycleOwner) { list ->
            list?.let {
                binding.rvMatches.visibility = View.VISIBLE
                matchList = it.toMutableList()
                adapter = LeagueMatchListAdapter()
                adapter.setItems(matchList)
                binding.rvMatches.adapter = adapter
                binding.filterLayout.isEnabled = true
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

    private fun setSearchView(){
        binding.filterLayout.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it.isEmpty()) {
                        adapter.setItems(matchList)
                    } else if (it.length >= 3) {
                        val filteredMatches = matchList.filter { score ->
                            score.teams.contains(it, true)
                        }.toMutableList()
                        adapter.setItems(filteredMatches)
                    }
                }
                return true
            }
        })
    }

    private fun init() {
        viewModel.detail.value =
            LeagueDetailFragmentArgs.fromBundle(requireArguments()).leagueDetail
    }

}