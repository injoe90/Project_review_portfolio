package com.example.cafe_user.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafe_user.R
import com.example.cafe_user.ui.menu.MenuItem
import kotlinx.android.synthetic.main.order_juice.*

class JuiceFragment:Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.order_juice,container,false)
        return view
    }

    var datalist = ArrayList<MenuItem>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        datalist.add(MenuItem(R.drawable.acai, "청포도 에이드", "Strawberry&Acai"
            , "Aid", 5000, "G")
        )

        datalist.add(MenuItem(R.drawable.kiwi, "딸기 에이드", "Strawberry&Kiwi"
            , "Aid", 5200, "G"))


        datalist.add(MenuItem(R.drawable.guava, "자몽 에이드", "Guava&Passion Fruit"
            , "Aid", 5500, "G"))


        val adapter1 = context?.let { OrderRecyclerAdapter(it, R.layout.cardviewitem,datalist) }
        val adapter2 = context?.let { OrderRecyclerAdapter(it, R.layout.cardviewitem,datalist) }
        val adapter3 = context?.let { OrderRecyclerAdapter(it, R.layout.cardviewitem,datalist) }

        val manager1 = LinearLayoutManager(context)
        manager1.orientation = LinearLayoutManager.HORIZONTAL
        juice_recycler1.layoutManager = manager1

        val manager2 = LinearLayoutManager(context)
        manager2.orientation = LinearLayoutManager.HORIZONTAL
        juice_recycler2.layoutManager = manager2

        val manager3 = LinearLayoutManager(context)
        manager3.orientation = LinearLayoutManager.HORIZONTAL
        juice_recycler3.layoutManager = manager3

        juice_recycler1.adapter = adapter1
        juice_recycler2.adapter = adapter2
        juice_recycler3.adapter = adapter3
    }
}