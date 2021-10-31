package com.example.cafe_user.ui.order

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.cafe_user.MainActivity
import com.example.cafe_user.R
import com.example.cafe_user.ui.cart.CartFragment
import kotlinx.android.synthetic.main.cardviewitem.*
import kotlinx.android.synthetic.main.menu_detail.*
import kotlinx.android.synthetic.main.order_screen.*
import kotlinx.android.synthetic.main.testlayout.*

class OrderFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.order_screen, container, false)
        return view
    }

    var CoffeeView: CoffeeFragment? = null
    var JuiceView: JuiceFragment? = null
    var SmoothieView: SmoothieFragment? = null
    var BreadView: BreadFragment? = null
    var DessertView: DessertFragment? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoffeeView = CoffeeFragment()
        JuiceView = JuiceFragment()
        SmoothieView = SmoothieFragment()
        BreadView = BreadFragment()
        DessertView = DessertFragment()

        category_coffee.setOnClickListener{
            childFragmentManager.beginTransaction().replace(R.id.order_menu_fragment,CoffeeView!!).commit()
        }
        category_juice.setOnClickListener{
            childFragmentManager.beginTransaction().replace(R.id.order_menu_fragment,JuiceView!!).commit()
        }
        category_smoothie.setOnClickListener {
            childFragmentManager.beginTransaction().replace(R.id.order_menu_fragment,SmoothieView!!).commit()
        }
        category_bread.setOnClickListener {
            childFragmentManager.beginTransaction().replace(R.id.order_menu_fragment,BreadView!!).commit()
        }
        category_dessert.setOnClickListener {
            childFragmentManager.beginTransaction().replace(R.id.order_menu_fragment,DessertView!!).commit()
        }
    }
}