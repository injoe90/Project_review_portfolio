package com.example.cafe_user.ui.order

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.cafe_user.R
import com.example.cafe_user.ui.menu.MenuItem
import kotlinx.android.synthetic.main.cardviewitem.view.*
import java.util.*
import kotlin.collections.ArrayList


class OrderRecyclerAdapter(var context: Context, var itemLayout: Int, var datalist:ArrayList<MenuItem>)
    :RecyclerView.Adapter<OrderRecyclerAdapter.OrderViewHolder>() {


    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var menuimg = itemView.menu_image
        var kor_menu = itemView.menu_kor
        var eng_menu = itemView.menu_eng
        var detail = itemView.menu_detail
        var price = itemView.price
        var btn = itemView.btn_add_cart

        var hot = itemView.temp_radio_hot
        var ice = itemView.temp_radio_ice

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val itemView = LayoutInflater.from(context).inflate(itemLayout,null)
        val orderViewHolder = OrderViewHolder(itemView)
        return orderViewHolder
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val menuimg = holder.menuimg
        menuimg.setImageResource(datalist[position].menuimg!!)
        val kor = holder.kor_menu
        kor.text = datalist[position].kor_title!!
        val eng = holder.eng_menu
        eng.text = datalist[position].eng_title!!
        val detail = holder.detail
        detail.text = datalist[position].menu_detail!!
        val price = holder.price
        price.text = datalist[position].price.toString()

        val btn = holder.btn
        val hot = holder.hot
        val ice = holder.ice

        btn.setOnClickListener {
            val coffeeCategory: String = eng.text.toString().toLowerCase(Locale.ROOT)
            val bundle = Bundle()
            when {
                coffeeCategory.contains("americano") -> {
                    makeBundle(bundle, 10, "아메리카노", ice, hot)
                }
                coffeeCategory.contains("cafelatte") -> {
                    makeBundle(bundle, 11, "카페라떼", ice, hot)
                }
                coffeeCategory.contains("cafemocca") -> {
                    makeBundle(bundle, 12, "카페모카", ice, hot)
                }
            }
            when (datalist[position].size) {
                "T" -> {
                    bundle.putString("size_option", "T")
                }
                "G" -> {
                    bundle.putString("size_option", "G")
                }
                "V" -> {
                    bundle.putString("size_option", "V")
                }
            }
            bundle.putInt("menu_count", 1)
            bundle.putInt("menu_price", datalist[position].price!!.toInt())
            Navigation.findNavController(btn).navigate(R.id.order_to_cart, bundle)
            Toast.makeText(context, "장바구니에 담겼습니다.", Toast.LENGTH_LONG).show()
        }
    }
    private fun makeBundle(bundle: Bundle, id: Int, name: String, ice: RadioButton, hot: RadioButton) {
        bundle.putInt("menu_id", id)
        bundle.putString("menu_name", name)
        if (ice.isChecked) {
            bundle.putInt("temp_option", 1)
        } else if (hot.isChecked) {
            bundle.putInt("temp_option", 0)
        }
    }
}