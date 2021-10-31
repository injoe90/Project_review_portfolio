package com.example.cafe_user.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafe_user.R
import com.example.cafe_user.ui.menu.MenuItem
import kotlinx.android.synthetic.main.order_smoothie.*

class SmoothieFragment:Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.order_smoothie,container,false)
        return view
    }

    var datalist = ArrayList<MenuItem>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        datalist.add(MenuItem(R.drawable.strawberry, "키위 스무디", "Strawberry"
            , "Fresh", 4200, "G")
        )

        datalist.add(MenuItem(R.drawable.pineapple, "파인애플 코코넛 스무디", "Pineapple&Coconut"
            , "New", 4500, "G"))

        datalist.add(MenuItem(R.drawable.mango, "망고 용과 스무디", "Mango&DragonFruit"
            , "New", 5000, "G"))

        val adapter1 = context?.let { OrderRecyclerAdapter(it, R.layout.cardviewitem,datalist) }
        val adapter2 = context?.let { OrderRecyclerAdapter(it, R.layout.cardviewitem,datalist) }
        val adapter3 = context?.let { OrderRecyclerAdapter(it, R.layout.cardviewitem,datalist) }

        val manager1 = LinearLayoutManager(context)
        manager1.orientation = LinearLayoutManager.HORIZONTAL
        smoothie_recycler1.layoutManager = manager1

        val manager2 = LinearLayoutManager(context)
        manager2.orientation = LinearLayoutManager.HORIZONTAL
        smoothie_recycler2.layoutManager = manager2

        val manager3 = LinearLayoutManager(context)
        manager3.orientation = LinearLayoutManager.HORIZONTAL
        smoothie_recycler3.layoutManager = manager3

        smoothie_recycler1.adapter = adapter1
        smoothie_recycler2.adapter = adapter2
        smoothie_recycler3.adapter = adapter3
    }
}