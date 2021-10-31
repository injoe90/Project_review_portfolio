package com.example.cafe_user.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafe_user.R
import com.example.cafe_user.ui.menu.MenuItem
import kotlinx.android.synthetic.main.order_dessert.*



class DessertFragment:Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.order_dessert,container,false)
        return view
    }

    var datalist = ArrayList<MenuItem>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        datalist.add(MenuItem(R.drawable.chocolate, "솔티드 카라멜 초콜렛", "Salted Caramel Cholate"
            , "BEST", 4400, "G")
        )

        datalist.add(MenuItem(R.drawable.madeleine, "마들렌", "Madeleine"
            , "NEW", 4200, "G"))


        datalist.add(MenuItem(R.drawable.snack, "아몬드", "Almond"
            , "BEST", 4500, "G"))

        val adapter1 = context?.let { OrderRecyclerAdapter(it, R.layout.cardviewitem,datalist) }
        val adapter2 = context?.let { OrderRecyclerAdapter(it, R.layout.cardviewitem,datalist) }
        val adapter3 = context?.let { OrderRecyclerAdapter(it, R.layout.cardviewitem,datalist) }

        val manager1 = LinearLayoutManager(context)
        manager1.orientation = LinearLayoutManager.HORIZONTAL
        dessert_recycler1.layoutManager = manager1

        val manager2 = LinearLayoutManager(context)
        manager2.orientation = LinearLayoutManager.HORIZONTAL
        dessert_recycler2.layoutManager = manager2

        val manager3 = LinearLayoutManager(context)
        manager3.orientation = LinearLayoutManager.HORIZONTAL
        dessert_recycler3.layoutManager = manager3

        dessert_recycler1.adapter = adapter1
        dessert_recycler2.adapter = adapter2
        dessert_recycler3.adapter = adapter3
    }
}