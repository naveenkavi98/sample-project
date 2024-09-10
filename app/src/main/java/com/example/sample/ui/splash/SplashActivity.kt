package com.example.sample.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sample.BaseActivity
import com.example.sample.R
import com.example.sample.Utils.AppConstants
import com.example.sample.Utils.MessageUtils
import com.example.sample.Utils.SharedPreferenceManager
import com.example.sample.api.ParamAPI
import com.example.sample.api.RestApi
import com.example.sample.databinding.ActivityMainBinding
import com.example.sample.databinding.ActivitySplashBinding
import com.example.sample.ui.main.MainActivity
import com.example.sample.ui.splash.bottomSheet.DynamicIpBottomSheetDialogFragment

class SplashActivity : BaseActivity() {

    lateinit var binding: ActivitySplashBinding
    lateinit var dynamicIpBottomSheetDialogFragment: DynamicIpBottomSheetDialogFragment

    /*var pressedTime = System.currentTimeMillis()
    val callback: Any = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (pressedTime + 2000 > System.currentTimeMillis()) {
                finish()
            } else {
                MessageUtils.showCustomSnackBar(binding.root,
                    "Press back again to exit", this@SplashActivity)
            }
            pressedTime = System.currentTimeMillis()
        }
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
    }

    private fun initView() {
        dynamicIpBottomSheetDialogFragment = DynamicIpBottomSheetDialogFragment()
        dynamicIpBottomSheetDialogFragment.setOnItemClicked(object : DynamicIpBottomSheetDialogFragment.OnItemClicked{
            override fun itemClicked(ip: String) {
                RestApi.BASE_URL = ip
                startScreen()
                dynamicIpBottomSheetDialogFragment.dismiss()
            }
        })
        if(ParamAPI.DEVELOPER_MODE) {
            dynamicIpBottomSheetDialogFragment.show(supportFragmentManager, AppConstants.QWERTY)
            if (dynamicIpBottomSheetDialogFragment.dialog?.isShowing == false) {
                dynamicIpBottomSheetDialogFragment.show(supportFragmentManager,AppConstants.QWERTY)
            }
        }
        else {
            startScreen()
        }
        /*onBackPressedDispatcher.addCallback(this@SplashActivity, callback as OnBackPressedCallback)*/
    }

    private fun startScreen() {
        Handler(Looper.getMainLooper()) .postDelayed({
            /*if(SharedPreferenceManager.getStringPreferences(this@SplashActivity, AppConstants.AUTH_TOKEN).isEmpty()) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }*/
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }

}