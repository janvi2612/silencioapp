package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.databinding.DrawerLayoutBinding
import com.example.myapplication.util.Constant1
import com.example.myapplication.util.PrefManager
import com.example.myapplication.util.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private  var _binding : DrawerLayoutBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController:NavController

    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        _binding = DrawerLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)


        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.custom_bottom_nav_fcv
        )as NavHostFragment

        navController = navHostFragment.navController


      val navGraph = navController.navInflater.inflate(R.navigation.my_nav)

        val isLoggedIn = PrefManager.getBoolean(Constant1.RESPONSE_CODE.IS_LOGIN,false)

        val isIntroComplete = PrefManager.getBoolean(Constant1.RESPONSE_CODE.IS_INTRO_COMPLETE,false)


        if(isLoggedIn && isIntroComplete){
            navGraph.setStartDestination(R.id.homeFragment)
        }

        if(!isLoggedIn){
            navGraph.setStartDestination(R.id.homeFragment)
        }

        if(!isIntroComplete){
            navGraph.setStartDestination(R.id.homeFragment)
        }


        navController.graph = navGraph
        binding.activityMain.customBottomNavBottomNav.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.walletFragment,
                R.id.mapFragment,

            )
        )
        setOnClicks()
        setCurrentDestinationListener()
       // binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)


    }

    override fun onBackPressed() {
        if(binding.drawer.isOpen){
            binding.drawer.close()
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navController.navigateUp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun setOnClicks(){
        binding.activityMain.customNavHome.setOnClickListener {
            binding.activityMain.customBottomNavBottomNav.selectedItemId = R.id.homeFragment
        }
        binding.activityMain.customNavWallet.setOnClickListener {
            binding.activityMain.customBottomNavBottomNav.selectedItemId = R.id.walletFragment
        }
        binding.activityMain.customNavMap.setOnClickListener {
            binding.activityMain.customBottomNavBottomNav.selectedItemId = R.id.mapFragment
        }
        binding.activityMain.customNavFavouritesFab.setOnClickListener {
            binding.activityMain.customBottomNavBottomNav.selectedItemId = R.id.measuresFragment
        }
        binding.activityMain.customNavShop.setOnClickListener{
            binding.activityMain.customBottomNavBottomNav.selectedItemId = R.id.shopFragment
        }
        binding.navHeader.txtlogout.setOnClickListener {
            Utils.logout(this)
        }
        binding.navHeader.img1.setOnClickListener {
            openProfileFragment()
        }
        binding.navHeader.textname.setOnClickListener {
            openProfileFragment()
        }

    }

    private fun openProfileFragment(){
        navController.navigate(R.id.profileFragment)
        binding.drawer.close()
    }


    private val bottomNavBroadcast: BroadcastReceiver =
        object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when(intent.getStringExtra(Constant1.RESPONSE_CODE.BOTTOM_NAV_BROADCAST_NAME)){
                    "wallet" -> {
                        binding.activityMain.customBottomNavBottomNav.selectedItemId = R.id.walletFragment
                    }
                    else -> Unit
                }
            }
        }



    private val openSideMenuDrawer: BroadcastReceiver =
        object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                binding.drawer.open()
            }
        }



    private fun setCurrentDestinationListener(){
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment,
                R.id.walletFragment,
                R.id.mapFragment ,
                R.id.shopFragment,
                -> {
                    binding.activityMain.customBottomNavLinearLayout.isVisible = true
                    binding.activityMain.customNavFavouritesFab.isVisible = true
                }
                else -> {
                    binding.activityMain.customBottomNavLinearLayout.isVisible = false
                    binding.activityMain.customNavFavouritesFab.isVisible = false
                }
            }
            deselectAllTabs()
            when (destination.id) {
                R.id.homeFragment -> {
                    binding.activityMain.customNavHome.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.homefilledfinsl,0,0)
                }
                R.id.walletFragment -> {
                    binding.activityMain.customNavWallet.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.walletfilledfinal,0,0)
                }
                R.id.mapFragment -> {
                    binding.activityMain.customNavMap.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.mapfilledfinal,0,0)
                }
                R.id.shopFragment -> {
                    binding.activityMain.customNavShop.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.shopfilledfinal,0,0)
                }

            }
        }
    }


    private fun deselectAllTabs() {
        binding.activityMain.customNavHome.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.homefinal,0,0)
        binding.activityMain.customNavWallet.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.wallet,0,0)
        binding.activityMain.customNavMap.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.map,0,0)
        binding.activityMain.customNavShop.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.shop,0,0)
    }


    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver((openSideMenuDrawer))
        LocalBroadcastManager.getInstance(this).unregisterReceiver((bottomNavBroadcast))
    }




}