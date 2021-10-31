package com.example.cafe_admin.ui.cctv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_cctv.view.*

class CctvRecyclerAdapter(var context: Context, var itemLayout:Int, var datalist:ArrayList<CctvResource>)
    :RecyclerView.Adapter<CctvRecyclerAdapter.CctvViewHolder>() {
    inner class CctvViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val cctvView = itemView.showText_cctv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CctvViewHolder {
        val itemView = LayoutInflater.from(context).inflate(itemLayout, null)
        return CctvViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CctvViewHolder, position: Int) {
        val cctvImageView = holder.cctvView
        cctvImageView.text = datalist[position].currentDate
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}