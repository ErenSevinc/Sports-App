package com.example.sportsbettingapp.presenter

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import com.example.sportsbettingapp.databinding.LayoutBaseBottomDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomDialog : BottomSheetDialogFragment() {
    private var bottomDialogBinding: LayoutBaseBottomDialogBinding? = null

    private var title: String? = null
    private var dismissAction: (() -> Unit)? = null

    fun setOnDismissListener(callback: () -> Unit): BaseBottomDialog {
        dismissAction = callback
        return this
    }

    fun setTitle(value: String): BaseBottomDialog {
        title = value
        return this
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissAction?.invoke()
    }

    abstract fun getCustomView(inflater: LayoutInflater, container: ViewGroup?): View?

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        val binding = LayoutBaseBottomDialogBinding.inflate(inflater, container,false)
        bottomDialogBinding = binding

        binding.tvBottomSheetDialogTitle.visibility = title?.let {
            binding.tvBottomSheetDialogTitle.text = it
            View.VISIBLE
        } ?: View.GONE
        binding.ivCancel.setOnClickListener {
            dismiss()
            dismissAction?.invoke()
        }
        binding.customContent.addView(getCustomView(inflater, container))

        return binding.root
    }

    override fun onDestroyView() {
        bottomDialogBinding = null
        super.onDestroyView()
    }




}