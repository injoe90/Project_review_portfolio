package com.example.cafe_user.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafe_user.MainActivity
import com.example.cafe_user.R
import kotlinx.android.synthetic.main.main_card_recycle_menu.*

class HomeFragment : Fragment() {
    var main_menulist = ArrayList<MenuRecycleItem>()
    var flag = 0
    private lateinit var homeViewModel: HomeViewModel


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.main_card_recycle_menu, container, false)

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 스마트 카페 대표 메뉴 표시
        main_menulist.add(MenuRecycleItem(R.drawable.main_coffee, "Coffee"))
        main_menulist.add(MenuRecycleItem(R.drawable.main_bread, "Bread & Cake"))
        main_menulist.add(MenuRecycleItem(R.drawable.main_des, "Dessert"))
        main_menulist.add(MenuRecycleItem(R.drawable.main_juice, "Juice"))
        main_menulist.add(MenuRecycleItem(R.drawable.main_smoothie, "Smoothie"))

        // 라사이클러 뷰 작업
        val myadapter = RecyclerCardAdapter(activity as MainActivity, R.layout.main_card_view_menu, main_menulist)
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        recycler_card.layoutManager = manager
        recycler_card.adapter = myadapter

    }
}