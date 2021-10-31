package com.example.cafe_kitchen.Home

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafe_kitchen.MainActivity
import com.example.cafe_kitchen.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment:Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    var homeVoiceState:Int = 0
    var homeNetworkMsg:String = ""

    val menuDataList = ArrayList<HomeMenuItem>()
    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuDataList.add(HomeMenuItem(1, R.drawable.cafe_menu))
        menuDataList.add(HomeMenuItem(2, R.drawable.food_storage))

        val menuAdapter = context?.let { HomeMenuAdapter(it, R.layout.recycler_item_home, menuDataList) }

        val menuManager = LinearLayoutManager(context)
        menuManager.orientation = LinearLayoutManager.HORIZONTAL

        home_menu_recycler.adapter = menuAdapter
        home_menu_recycler.layoutManager = menuManager

        // 음성 인식을 사용하는 기능
        homeVoice.setOnClickListener {
            if (homeVoiceState == 0) {
                homeVoice.setColorFilter(R.color.skyBlue)
                homeVoiceText.text = "음성 인식 기능을 사용합니다"
                homeVoiceText.setTextColor(R.color.skyBlue)
                homeVoiceState = 1
            } else {
                homeVoice.setColorFilter(R.color.black)
                homeVoiceText.text = "음성 인식을 사용해보세요"
                homeVoiceText.setTextColor(R.color.black)
                homeVoiceState = 0
            }

        }

        // Cafe_Admin 어플리케이션에서 보낸 메시지를 확인하는 기능
        homeFab.setOnClickListener{
            homeNetworkDialog(homeNetworkMsg)
        }

        if (homeNetworkMsg != "") {
            homeFab.setImageResource(R.drawable.ic_message)
        }
    }

    private fun homeNetworkDialog(msg:String) {
        val homeDialog = context?.let { HomeDialog(it) }
        homeDialog?.start(msg)
    }
}