package com.example.sample.ui.splash.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sample.api.RestApi
import com.example.sample.databinding.BottomSheetDynamicIpBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DynamicIpBottomSheetDialogFragment() : BottomSheetDialogFragment() {

    lateinit var binding: BottomSheetDynamicIpBinding
    private var onItemClicked : OnItemClicked? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetDynamicIpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        binding.txtTitle.text = "Dynamic Ip"
        binding.edtIp.setText(RestApi.BASE_URL)
        binding.imgArrowForward.setOnClickListener {
            onItemClicked?.itemClicked(binding.edtIp.text.toString())
        }
    }

    fun setOnItemClicked(listener: OnItemClicked) {
        onItemClicked = listener
    }

    interface OnItemClicked {
        fun itemClicked(ip: String)
    }

}