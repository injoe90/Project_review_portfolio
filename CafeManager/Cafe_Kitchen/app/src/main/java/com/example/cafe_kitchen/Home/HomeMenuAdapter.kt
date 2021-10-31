package com.example.cafe_kitchen.Home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.cafe_kitchen.R
import kotlinx.android.synthetic.main.recycler_item_home.view.*

class HomeMenuAdapter(var context: Context, var itemLayout:Int, var datalist:ArrayList<HomeMenuItem>)
    : RecyclerView.Adapter<HomeMenuAdapter.HomeMenuViewHolder>() {
    inner class HomeMenuViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val menuId = itemView.home_menu_id
        val menuImage = itemView.home_menu_img
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMenuViewHolder {
        val itemView = LayoutInflater.from(context).inflate(itemLayout, null)
        return HomeMenuViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HomeMenuViewHolder, position: Int) {
        val homeMenuId = holder.menuId
        val homeMenuImage = holder.menuImage

        homeMenuId.text = datalist[position].homeMenuId.toString()
        homeMenuImage.setImageResource(datalist[position].homeMenuImage)

        homeMenuImage.setOnClickListener {
            if (datalist[position].homeMenuId == 1) {
                Navigation.findNavController(homeMenuImage).navigate(R.id.home_to_order)
            } else if (datalist[position].homeMenuId == 2) {
                Navigation.findNavController(homeMenuImage).navigate(R.id.home_to_storage)
            }
        }
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}