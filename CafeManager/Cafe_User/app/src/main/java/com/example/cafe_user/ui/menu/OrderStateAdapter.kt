package com.example.cafe_user.ui.menu

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cafe_user.ui.cart.CartItems
import kotlinx.android.synthetic.main.order_state_row.view.*

class OrderStateAdapter (var context : Context, var itemlayout : Int, var datalist : ArrayList<CartItems>)
    : RecyclerView.Adapter<OrderStateAdapter.OrderStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderStateViewHolder {
        val itemView = LayoutInflater.from(context).inflate(itemlayout, null)
        return OrderStateViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: OrderStateViewHolder, position: Int) {
        val menuNum = holder.menuNum
        menuNum.text = "No. ${datalist[position].cart_id}"

        val menuName = holder.menuName
        menuName.text = datalist[position].menu_name

        val menuSize = holder.menuSize
        menuSize.text = datalist[position].cart_item_size

        val menuTemp = holder.menuTemp
        if (datalist[position].cart_item_temp == true) {
            menuTemp.text = "ice"
        } else {
            menuTemp.text = "hot"
        }

        val menuCount = holder.menuCount
        menuCount.text = datalist[position].cart_item_count.toString()

        val menuPrice = holder.menuPrice
        menuPrice.text = datalist[position].cart_item_price.toString()
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    inner class OrderStateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val menuNum = itemView.menu_num
        val menuName = itemView.menu_name
        val menuSize = itemView.menu_size
        val menuTemp = itemView.menu_temp
        val menuCount = itemView.menu_count
        val menuPrice = itemView.menu_price
    }
}