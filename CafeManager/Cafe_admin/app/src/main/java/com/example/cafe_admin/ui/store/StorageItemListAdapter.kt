package com.example.cafe_admin.ui.store

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.stock_item_list.view.*

class StorageItemListAdapter(var context: Context, var itemLayout: Int, var datalist: ArrayList<StorageItem>)
    : RecyclerView.Adapter<StorageItemListAdapter.ListViewHolder>() {
    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val itemId = itemView.item_id!!
        val itemName = itemView.item_name!!
        val itemCount = itemView.item_count!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView = LayoutInflater.from(context).inflate(itemLayout,null)
        return ListViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val itemId = holder.itemId
        val itemName = holder.itemName
        val itemCount = holder.itemCount

        itemId.text = "상품 ID: ${datalist[position].id}"
        itemName.text = datalist[position].itemName
        itemCount.text = "${datalist[position].itemCount} 개"
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}