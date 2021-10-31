package com.example.cafe_user.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafe_user.R
import com.example.cafe_user.ui.menu.MenuItem
import kotlinx.android.synthetic.main.order_bread.*



class BreadFragment:Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.order_bread,container,false)
        return view
    }

    var datalist = ArrayList<MenuItem>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        datalist.add(MenuItem(R.drawable.brownies, "초코 브라우니", "Choco Brownie"
            , "BEST", 4100, "G")
        )

        datalist.add(MenuItem(R.drawable.raspberrybread, "라즈베리 월넛", "Raspberry Walnut"
            , "NEW", 4500, "G"))

        datalist.add(MenuItem(R.drawable.cinnamon, "시나몬케이크", "Cinnamon Cake"
            , "NEW", 4200, "G"))

        val adapter1 = context?.let { OrderRecyclerAdapter(it, R.layout.cardviewitem,datalist) }
        val adapter2 = context?.let { OrderRecyclerAdapter(it, R.layout.cardviewitem,datalist) }
        val adapter3 = context?.let { OrderRecyclerAdapter(it, R.layout.cardviewitem,datalist) }

        val manager1 = LinearLayoutManager(context)
        manager1.orientation = LinearLayoutManager.HORIZONTAL
        bread_recycler1.layoutManager = manager1

        val manager2 = LinearLayoutManager(context)
        manager2.orientation = LinearLayoutManager.HORIZONTAL
        bread_recycler2.layoutManager = manager2

        val manager3 = LinearLayoutManager(context)
        manager3.orientation = LinearLayoutManager.HORIZONTAL
        bread_recycler3.layoutManager = manager3

        bread_recycler1.adapter = adapter1
        bread_recycler2.adapter = adapter2
        bread_recycler3.adapter = adapter3
    }
}