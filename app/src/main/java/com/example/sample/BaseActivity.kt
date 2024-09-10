package com.example.sample

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.sample.Utils.MessageUtils
import com.example.sample.Utils.UIUtils
import com.example.sample.Utils.Utils
import com.example.sample.api.FailureResponseFromAPI

open class BaseActivity : AppCompatActivity(), UIUtils, FailureResponseFromAPI {

    lateinit var rootView : View
    lateinit var failureResponseFromAPI: FailureResponseFromAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        failureResponseFromAPI = this@BaseActivity
        rootView = window.decorView.rootView
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        /*binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)*/
        //APIConnector.apiCall(this@BaseActivity, this@BaseActivity, ParamAPI.QUOTE, ParamKey.GET_METHOD)
        window.statusBarColor = ContextCompat.getColor(this, R.color.primary_color_light)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        try {
            val view = currentFocus
            if (view != null && (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) && view is EditText && !view.javaClass.name.startsWith(
                    "android.webkit."
                )
            ) {
                val scrcoords = IntArray(2)
                view.getLocationOnScreen(scrcoords)
                val x = ev.rawX + view.left - scrcoords[0]
                val y = ev.rawY + view.top - scrcoords[1]
                if (x < view.left || x > view.right || y < view.top || y > view.bottom)
                    (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                        this.window.decorView.applicationWindowToken,
                        0
                    )
            }
        } catch (e: RuntimeException) {
            e.printStackTrace()
        }

        return super.dispatchTouchEvent(ev)
    }


    override fun showSnackView(message: String, isShowErrorView: Boolean) {

    }

    override fun hideSnackView() {

    }

    override fun enableBackArrowInToolBar(title: String, isShowToolbar: Boolean) {

    }

    override fun disableBackArrowInToolBar() {

    }

    fun showMessage(message: String) {
        MessageUtils.showSnackBar(rootView, message)
    }

    override fun onResponseErrorCode(message: String, api: String, responseCode: Int) {
        if (responseCode == 401) {
            Utils.logOut(this, this)
        }
        else {
            MessageUtils.showCustomSnackBar(this, window.decorView.rootView, message)
        }
    }

    override fun onResponseFailure(message: String, api: String) {
        MessageUtils.showCustomSnackBar(this, window.decorView.rootView, message)
    }
}