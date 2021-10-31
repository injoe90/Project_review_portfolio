package com.example.cafe_kitchen.Home

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.example.cafe_kitchen.R

class HomeDialog(context: Context) {
    private val homeDlg = Dialog(context)
    private lateinit var homeText: TextView
    private lateinit var homeButton: Button

    fun start(networkMessage:String) {
        homeDlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        homeDlg.setContentView(R.layout.dialog_home)
        homeDlg.setCancelable(false)

        homeText = homeDlg.findViewById(R.id.dialog_hom_message)
        homeButton = homeDlg.findViewById(R.id.dialog_hom_close)

        if (networkMessage != "") {
            homeText.text = networkMessage
        }

        homeButton.setOnClickListener {
            homeDlg.dismiss()
        }

        homeDlg.show()
    }
}