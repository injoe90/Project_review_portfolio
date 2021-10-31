package com.example.cafe_kitchen.StorageManage

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cafe_kitchen.MainActivity
import com.example.cafe_kitchen.R
import kotlinx.android.synthetic.main.recycler_item_storage_manager.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

class StorageSelectAdapter(var context: Context, var itemLayout:Int, var datalist:ArrayList<StorageItem>)
    : RecyclerView.Adapter<StorageSelectAdapter.ItemViewHolder>() {
        inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val itemCheck = itemView.item_check_storageManage
            val itemId = itemView.item_id_storageManage
            val itemName = itemView.item_name_storageMange
            val itemCount = itemView.item_count_storageManage
            val itemEntrance = itemView.item_entrance_storageManage
            val itemExpire = itemView.item_expire_storageManage
            val deleteItem = itemView.item_delete_storageManage
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(context).inflate(itemLayout, null)
        return ItemViewHolder(itemView)
    }

    private var deleteStorageItem: StorageItem? = null
    private var deletePosition:Int? = null
    private val localHostIp: String = "http://172.30.1.57:8000"
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val itemCheck = holder.itemCheck
        val itemId = holder.itemId
        val itemName = holder.itemName
        val itemCount = holder.itemCount
        val entranceDate = holder.itemEntrance
        val expireDate = holder.itemExpire
        val deleteItem = holder.deleteItem

        itemId.text = "상품 ID: ${datalist[position].id}"
        itemName.text = datalist[position].name
        itemCount.text = "수량: ${datalist[position].count}"
        entranceDate.text = datalist[position].entranceDate
        expireDate.text = datalist[position].expireDate

        itemCheck.setOnCheckedChangeListener { buttonView, isChecked ->
            deletePosition = getPosition(position)
            deleteStorageItem = datalist[position]
        }

        deleteItem.setOnClickListener {
            if (deleteStorageItem == null || !itemCheck.isChecked) {
                Toast.makeText(context, "체크 상자를 눌러주세요", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "$position", Toast.LENGTH_SHORT).show()
                deleteItemDB(localHostIp, deleteStorageItem!!)
                datalist.remove(deleteStorageItem)
                notifyDataSetChanged()
                deletePosition = null
                deleteStorageItem = null
            }
        }
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    private fun getPosition(position: Int):Int {
        deletePosition = position
        return deletePosition!!
    }

    private fun deleteItemDB(localIp:String, storageItem:StorageItem) {
        CoroutineScope(IO).launch {
            launch {
                delay(250L)
                val site = "$localIp/delete"
                val jsonObj = JSONObject()
                jsonObj.put("storage_id", storageItem.id)

                val client = OkHttpClient()
                val jsonData= jsonObj.toString()
                val request = Request.Builder()
                        .url(site)
                        .delete(RequestBody.create(MediaType.parse("application/json"), jsonData))
                        .build()

                val response = client.newCall(request).execute()
                val result = response.body()!!.string()
            }
        }
    }
}