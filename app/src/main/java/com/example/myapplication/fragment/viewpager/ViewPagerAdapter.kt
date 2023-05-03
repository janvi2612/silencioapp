package com.example.myapplication.fragment.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.fragment.onboarding1.Onboarding1Fragment
import com.example.myapplication.R


private const val NUM_PAGES = 4
class ViewPagerAdapter(fa : FragmentActivity):FragmentStateAdapter(fa) {


    override fun getItemCount(): Int = NUM_PAGES


    override fun createFragment(position: Int): Fragment {
        return if (position == 0){
           Onboarding1Fragment(
              "Welcome to Silencio!",
               "The first free Web3 network that rewards users for measuring noise pollution globally",
               R.drawable.ob1
            )
        } else if (position == 1){
            Onboarding1Fragment(
                "What is noise pollution?",
                "Noise pollution is among the most harmful environmental factors to the health of you and your loved ones.\n" +
                        "It is an invisible danger.",R.drawable.ob2
            )


        } else if (position == 2){
            Onboarding1Fragment(
                "Earn Noise Coins",
                "Earn Noise Coins for every stream you deliver to the network! The whole Silencio community will benefit from data sales. Until Main net lunch, Noise Coins will be exchangeable for goods, services & donations in the in-app store",
                R.drawable.ob3

            )

        } else {
           Onboarding1Fragment(
               "We cannot hear you!","Data uploads are strictly measured in decibels (dBA) which represent the sound level. This excludes the content ",
               R.drawable.ob4
           )
        }


    }
}