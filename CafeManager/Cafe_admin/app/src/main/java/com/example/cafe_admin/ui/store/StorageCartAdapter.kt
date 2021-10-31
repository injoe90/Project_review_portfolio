package com.example.cafe_admin.ui.store

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cafe_admin.R
import kotlinx.android.synthetic.main.stock_cart_list.view.*
import kotlinx.android.synthetic.main.stock_item_list.view.*

class StorageCartAdapter(var context: Context, var itemLayout: Int, var datalist: ArrayList<StorageItem>)
    : RecyclerView.Adapter<StorageCartAdapter.CartListViewHolder>() {
    inner class CartListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemId = itemView.item_cart_id!!
        val itemName = itemView.item_cart_name!!
        val itemCount = itemView.item_cart_count!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartListViewHolder {
        val itemView = LayoutInflater.from(context).inflate(itemLayout,null)
        return CartListViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartListViewHolder, position: Int) {
        val itemId = holder.itemId
        val itemName = holder.itemName
        val itemCount = holder.itemCount

        itemId.text = "상품 ID: ${datalist[position].id}"
        itemName.text = datalist[position].itemName
        itemCount.text = "${datalist[position].itemCount} 개"

        itemCount.setTextColor(Color.RED)
    }
}