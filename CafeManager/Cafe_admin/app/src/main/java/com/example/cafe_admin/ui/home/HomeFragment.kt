package com.example.cafe_admin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cafe_admin.MainActivity
import com.example.cafe_admin.R
import kotlinx.android.synthetic.main.admin_seatinfo.*

class HomeFragment : Fragment() {

    var datalist = ArrayList<HashMap<String,Any>>()


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.admin_seatinfo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var item = HashMap<String,Any>()
        item["번호"] = R.drawable.number1
        item["품목1"] = "아메리카노"
        item["품목2"] = "아메리카노"
        datalist.add(item)

        item = HashMap<String,Any>()
        item["번호"] = R.drawable.number3
        item["품목1"] = "아이스 라떼"
        item["품목2"] = "딸기 마카롱"
        datalist.add(item)

        item = HashMap<String,Any>()
        item["번호"] = R.drawable.number2
        item["품목1"] = "아메리카노"
        datalist.add(item)

        val adapter = SimpleAdapter(context, datalist,
                R.layout.custrow,
                arrayOf("번호","품목1", "품목2"),
                intArrayOf(R.id.myimg,R.id.txtcust1,R.id.txtcust2)
        )

        admin_order_list.adapter = adapter


    }



}