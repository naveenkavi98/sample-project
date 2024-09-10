package com.example.sample.ui.main

import android.app.Dialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.sample.R
import com.example.sample.Utils.AppConstants
import com.example.sample.Utils.MessageUtils
import com.example.sample.Utils.SharedPreferenceManager
import com.example.sample.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    var pressedTime = System.currentTimeMillis()
    val callback: Any = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (findNavController(R.id.nav_host_fragment_content_home).currentDestination?.id == R.id.nav_home) {
                if (pressedTime + 2000 > System.currentTimeMillis()) {
                    finish()
                } else {
                    MessageUtils.showCustomSnackBar(this@MainActivity, binding.root,
                        "Press back again to exit")
                }
                pressedTime = System.currentTimeMillis()
            }
            else {
                findNavController(R.id.nav_host_fragment_content_home).popBackStack()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        setSupportActionBar(binding.appBarHome.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    navController.navigate(R.id.nav_home)
                }
            }
            drawerLayout.closeDrawers()
            true
        }


        navView.setBackgroundColor(getColor(R.color.white))
        val menu: Menu = navView.menu
        //val navLogin: MenuItem = menu.findItem(R.id.nav_login)
        var loginTitle = ""
        if(SharedPreferenceManager.getStringPreferences(this@MainActivity, AppConstants.AUTH_TOKEN).isEmpty()) {
            //loginTitle = getString(R.string.login)
        }
        else {
            //loginTitle = getString(R.string.logout)
        }
        //navLogin.setTitle(loginTitle)

        onBackPressedDispatcher.addCallback(this@MainActivity, callback as OnBackPressedCallback)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun showDialog() {
        val dialog = Dialog(this, R.style.CustomAlertDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_logout)

        val txtLogout = dialog.findViewById<TextView>(R.id.txt_logout)
        val txtCancel = dialog.findViewById<TextView>(R.id.txt_cancel)
        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtLogout.setOnClickListener {
            //closeDrawer()
            SharedPreferenceManager.clearPreference(this)
            finish()
            /*val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)*/
        }

        dialog.show()
    }

}