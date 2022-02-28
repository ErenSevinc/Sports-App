package com.example.sportsbettingapp.presenter.selectionbottomdialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sportsbettingapp.databinding.SelectionBottomSheetBinding
import com.example.sportsbettingapp.presenter.BaseBottomDialog

class SelectionBottomSheetDialog(private val onCLickCoupon: () -> Unit, private val onClickOther: () -> Unit) :
    BaseBottomDialog() {
    override fun getCustomView(inflater: LayoutInflater, container: ViewGroup?): View? {
        val binding: SelectionBottomSheetBinding =
            SelectionBottomSheetBinding.inflate(inflater, container, false)

        binding.otherMatches.setOnClickListener {
            onClickOther.invoke()
        }
        binding.otherBet.setOnClickListener {
            dismiss()
        }
        binding.coupon.setOnClickListener {
            onCLickCoupon.invoke()
        }

        return binding.root
    }

}