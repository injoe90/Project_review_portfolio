package com.example.cafe_user.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafe_user.R
import com.example.cafe_user.ui.menu.MenuItem
import kotlinx.android.synthetic.main.order_coffee.*
import kotlin.collections.ArrayList

class CoffeeFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.order_coffee, container, false)
    }

    var dataList1 = ArrayList<MenuItem>()
    var dataList2 = ArrayList<MenuItem>()
    var dataList3 = ArrayList<MenuItem>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        makeCoffeeMenu()

        val adapter1 = context?.let { OrderRecyclerAdapter(it, R.layout.cardviewitem,dataList1) }
        val adapter2 = context?.let { OrderRecyclerAdapter(it, R.layout.cardviewitem,dataList2) }
        val adapter3 = context?.let { OrderRecyclerAdapter(it, R.layout.cardviewitem,dataList3) }

        val manager1 = LinearLayoutManager(context)
        manager1.orientation = LinearLayoutManager.HORIZONTAL
        coffee_recycler1.layoutManager = manager1

        val manager2 = LinearLayoutManager(context)
        manager2.orientation = LinearLayoutManager.HORIZONTAL
        coffee_recycler2.layoutManager = manager2

        val manager3 = LinearLayoutManager(context)
        manager3.orientation = LinearLayoutManager.HORIZONTAL
        coffee_recycler3.layoutManager = manager3

        coffee_recycler1.adapter = adapter1
        coffee_recycler2.adapter = adapter2
        coffee_recycler3.adapter = adapter3

    }
    private fun makeCoffeeMenu() {
        // 메뉴 정보를 입력하는 함수
        // 리사이클러 뷰1
        dataList1.add(MenuItem(R.drawable.americano, "콜드 브루 아메리카노", "Cold Brew Cafe Americano"
                , "NO.1", 2500, "G")
        )

        dataList1.add(MenuItem(R.drawable.caffelatte, "콜드 브루 카페라떼", "Cold Brew CafeLatte"
                , "BEST", 3500, "G"))

        dataList1.add(MenuItem(R.drawable.moca, "콜드 브루 카페모카", "Cold Brew Cafe Moca"
                , "추천", 3500, "G"))

        dataList3.add(MenuItem(R.drawable.americano, "콜드 브루 아메리카노", "Cold Brew Cafe Americano"
                , "NO.1", 2500, "G"))

        // 리사이클러 뷰3
        dataList3.add(MenuItem(R.drawable.caffelatte, "콜드 브루 카페라떼", "Cold Brew CafeLatte"
                , "BEST", 3500, "G"))

        dataList3.add(MenuItem(R.drawable.moca, "콜드 브루 카페모카", "Cold Brew CafeMoca"
                , "추천", 3500, "G"))

        // 리사이클러 뷰2
        dataList2.add(MenuItem(R.drawable.americano_promotion, "아메리카노", "Americano"
                , "NO.1", 2500, "G"))

        dataList2.add(MenuItem(R.drawable.cafelatte_promotion, "카페라떼", "CafeLatte"
                , "BEST", 3500, "G"))

        dataList2.add(MenuItem(R.drawable.cafemocca_promotion, "카페모카", "CafeMocca"
                , "추천", 3500, "G"))
    }
}
