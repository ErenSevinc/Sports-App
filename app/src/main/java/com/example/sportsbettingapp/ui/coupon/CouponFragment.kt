package com.example.sportsbettingapp.ui.coupon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsbettingapp.data.enum.FirebaseEvent
import com.example.sportsbettingapp.databinding.FragmentCouponBinding
import com.example.sportsbettingapp.presenter.SwipeCouponDelete
import com.example.sportsbettingapp.presenter.adapters.coupon.CouponAdapter
import com.example.sportsbettingapp.ui.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CouponFragment : Fragment() {
    private lateinit var binding: FragmentCouponBinding
    private val activityViewModel by activityViewModels<HomeViewModel>()
    private lateinit var adapter: CouponAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCouponBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activityViewModel.setToolbarTitle("Coupon")
        setSwipeDelete()
        observeLiveData()
    }

    private fun setSwipeDelete() {
        val swipeCouponDelete = object : SwipeCouponDelete(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                activityViewModel.sendEvent(
                    FirebaseEvent.RemoveCart, Pair(
                        "BET_ID",
                        activityViewModel.betList.value?.get(viewHolder.adapterPosition)?.id ?: ""
                    )
                )
                activityViewModel.removeBet(viewHolder.adapterPosition)
            }
        }
        val touchHelper = ItemTouchHelper(swipeCouponDelete)
        touchHelper.attachToRecyclerView(binding.rvCoupon)
    }

    private fun observeLiveData() {
        activityViewModel.betList.observe(viewLifecycleOwner) { bet ->
            bet?.let {
                binding.tvError.visibility = View.GONE
                adapter = CouponAdapter(bet)
                binding.rvCoupon.adapter = adapter
                if (activityViewModel.calculateBetTotal() == "1"){
                    binding.title.text = ""
                }
                else {
                    binding.title.text = "Total Price: ${activityViewModel.calculateBetTotal()} $"
                }
            }.run {
                binding.tvError.visibility = View.VISIBLE
            }
        }
        activityViewModel.error.observe(viewLifecycleOwner) {
            binding.tvError.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

}