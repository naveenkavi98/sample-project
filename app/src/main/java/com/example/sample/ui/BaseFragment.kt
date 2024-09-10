package com.example.sample.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.sample.Utils.UIUtils
import com.example.sample.api.FailureResponseFromAPI

open class BaseFragment: Fragment(), FailureResponseFromAPI, UIUtils {

    companion object {
        lateinit var failureResponseFromAPI: FailureResponseFromAPI
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        failureResponseFromAPI = this
    }

    override fun showSnackView(message: String, isShowErrorView: Boolean) {

    }

    override fun hideSnackView() {

    }

    override fun enableBackArrowInToolBar(title: String, isShowToolbar: Boolean) {

    }

    override fun disableBackArrowInToolBar() {

    }

    override fun onResponseErrorCode(message: String, api: String, responseCode: Int) {

    }

    override fun onResponseFailure(message: String, api: String) {

    }
}