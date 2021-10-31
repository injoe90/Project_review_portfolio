package com.example.cafe_kitchen.StorageManage

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cafe_kitchen.R
import kotlinx.android.synthetic.main.recycler_item_delete_storage_manager.view.*

class DeleteItemAdapter(var context: Context, var itemLayout:Int, var datalist:ArrayList<StorageItemDelete>)
    : RecyclerView.Adapter<DeleteItemAdapter.ItemDeleteViewHolder>() {
        inner class ItemDeleteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val deleteId = itemView.delete_id_storageManage
            val deleteName = itemView.delete_name_storageManage
            val deleteCount = itemView.delete_count_storageManage
            val deleteEntrance = itemView.delete_entrance_storageManage
            val deleteExpire = itemView.delete_expire_storageManage
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDeleteViewHolder {
        val itemView = LayoutInflater.from(context).inflate(itemLayout, null)
        return ItemDeleteViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onBindViewHolder(holder: ItemDeleteViewHolder, position: Int) {
        val deleteId = holder.deleteId
        val deleteName = holder.deleteName
        val deleteCount = holder.deleteCount
        val deleteEntrance = holder.deleteEntrance
        val deleteExpire = holder.deleteExpire

        deleteId.text = "상품 ID: ${datalist[position].id}"
        deleteName.text = datalist[position].name
        deleteCount.text = "수량: ${datalist[position].count}"
        deleteEntrance.text = datalist[position].entranceDate
        deleteExpire.text = datalist[position].expireDate

        if (datalist[position].day == 0) {
            deleteId.setTextColor(R.color.delete_red)
            deleteName.setTextColor(R.color.delete_red)
            deleteCount.setTextColor(R.color.delete_red)
            deleteEntrance.setTextColor(R.color.delete_red)
            deleteExpire.setTextColor(R.color.delete_red)
        } else if (datalist[position].day == 1) {
            deleteId.setTextColor(R.color.delete_green)
            deleteName.setTextColor(R.color.delete_green)
            deleteCount.setTextColor(R.color.delete_green)
            deleteEntrance.setTextColor(R.color.delete_green)
            deleteExpire.setTextColor(R.color.delete_green)
        }
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}

