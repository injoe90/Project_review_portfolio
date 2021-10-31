package com.example.cafe_user.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_swipe.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Suppress("UNUSED_CHANGED_VALUE")
class SwipeListAdapter (var context : Context, var itemlayout : Int, var datalist : ArrayList<CartItems>)
    : RecyclerView.Adapter<SwipeListAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        var cart_img = itemView.cart_image
        var menu_name = itemView.item_name
        var cart_id = itemView.cart_id
        var item_size = itemView.item_size
        var item_count = itemView.item_count
        var item_price = itemView.item_price

        var item_temp = itemView.cart_temp_group
        var del_check = itemView.del_check

        var count_up = itemView.btn_count_up
        var count_down = itemView.btn_count_down
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(itemlayout, null)
        return MyViewHolder(itemView)
    }

    private var delPosition = 0
    private var delItems:CartItems? = null
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val cartDB = CartMenuDB(context)
        val cart_img = holder.cart_img
        cart_img.setImageResource(datalist[position].cart_img!!)

        val name = holder.menu_name
        name.text = datalist[position].menu_name!!

        val cart_id = holder.cart_id
        cart_id.text = datalist[position].cart_id.toString()

        val size = holder.item_size
        size.text = datalist[position].cart_item_size!!

        val count = holder.item_count
        count.text = datalist[position].cart_item_count.toString()

        val price = holder.item_price
        price.text = datalist[position].cart_item_price.toString()

        val check = holder.del_check
        check.isChecked = datalist[position].cart_check!!

        val temp = holder.item_temp
        if (datalist[position].cart_item_temp!!) {
            temp.cart_radio_ice.isChecked = datalist[position].cart_item_temp!!
        } else {
            temp.cart_radio_hot.isChecked = !datalist[position].cart_item_temp!!
        }

        val itemCountUp = holder.count_up
        val itemCountDown = holder.count_down

        // 삭제할 항목의 위치를 저장하는 기능
        holder.del_check.setOnClickListener {
            setPosition(position)
            delItems = datalist[delPosition]
        }

        // 장바구니 목록의 주문 메뉴 개수를 조정하는 기능: Update
        // 현재 상황: 실시간 변화를 보여줄 수 없음
        itemCountUp.setOnClickListener {
            val cnt = datalist[position].cart_item_count!! + 1
            CoroutineScope(Default).launch {
                launch {
                    delay(500L)
                    cartDB.cartUpdate(datalist[position].cart_id!!, cnt)
                }
            }
            datalist[position].cart_item_count = cnt
            notifyDataSetChanged()
        }
        itemCountDown.setOnClickListener {
            val cnt = datalist[position].cart_item_count!! - 1
            if (cnt > 0) {
                CoroutineScope(Default).launch {
                    launch {
                        delay(500L)
                        cartDB.cartUpdate(datalist[position].cart_id!!, cnt)
                    }
                }
                datalist[position].cart_item_count = cnt
                notifyDataSetChanged()
            } else {
                Toast.makeText(context, "더 이상 수량을 줄일 수 없습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    fun getPosition(): Int {
        return delPosition
    }

    private fun setPosition(position: Int) {
        delPosition = position
    }

    fun getId(position: Int):Int {
        return datalist[position].cart_id!!
    }

    fun getItem():CartItems {
        return delItems!!
    }
}