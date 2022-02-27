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
import com.example.sportsbettingapp.ui.home.HomeViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CouponFragment : Fragment() {
    private lateinit var binding: FragmentCouponBinding
    private val activityViewModel by activityViewModels<HomeViewModel>()
    private lateinit var adapter: CouponAdapter

    @Inject
    lateinit var analytics: FirebaseAnalytics

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCouponBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activityViewModel.betList.observe(viewLifecycleOwner) {
            adapter = CouponAdapter(it)
            binding.rvCoupon.adapter = adapter
        }

        val swipeCouponDelete = object : SwipeCouponDelete(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                activityViewModel.removeBet(viewHolder.adapterPosition)
                activityViewModel.sendEvent(FirebaseEvent.RemoveCart)
            }
        }

        val touchHelper = ItemTouchHelper(swipeCouponDelete)
        touchHelper.attachToRecyclerView(binding.rvCoupon)
    }

}