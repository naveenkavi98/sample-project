package com.example.sample.ui.main.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sample.Model.response.ApiResponse
import com.example.sample.Model.response.EventsResponse
import com.example.sample.Utils.MessageUtils
import com.example.sample.api.APIConnector
import com.example.sample.api.ParamAPI
import com.example.sample.api.ParamKey
import com.example.sample.api.RestApi
import com.example.sample.api.SuccessResponseFromAPI
import com.example.sample.databinding.FragmentMainHomeBinding
import com.example.sample.ui.BaseFragment
import com.google.gson.Gson

class MainHomeFragment : BaseFragment(), SuccessResponseFromAPI {

    lateinit var binding: FragmentMainHomeBinding
    lateinit var eventsList: ArrayList<EventsResponse>
    lateinit var zooEventListAdapter: ZooEventListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =  FragmentMainHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        eventsList = ArrayList()
        zooEventListAdapter = ZooEventListAdapter(eventsList)
        zooEventListAdapter.setOnItemClicked(object : ZooEventListAdapter.OnItemClicked{
            override fun itemClicked(data: EventsResponse, position: Int) {

            }
        })
        binding.rcvZooEvents.adapter = zooEventListAdapter

        RestApi.BASE_URL = ParamAPI.BASE_URL_EVENTS
        APIConnector.apiCall(requireActivity(), this, failureResponseFromAPI, ParamAPI.API_EVENTS_LIST, ParamKey.POST_METHOD, true)

    }

    override fun onResponseSuccess(responseString: String, apiResponse: ApiResponse, api: String) {
        when(api) {
            ParamAPI.API_EVENTS_LIST -> {
                Log.e( "onResponseTime: ", System.currentTimeMillis().toString())
                val response: ArrayList<EventsResponse> = ParamAPI.convertResponseToArrayList(responseString)
                eventsList.clear()
                eventsList.addAll(response)
                zooEventListAdapter.notifyItemRangeInserted(0, eventsList.size)
                Log.e( "onResponseTime: ", System.currentTimeMillis().toString())
            }
        }
    }

}