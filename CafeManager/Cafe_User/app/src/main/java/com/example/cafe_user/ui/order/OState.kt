package com.example.cafe_user.ui.order

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafe_user.MainActivity
import com.example.cafe_user.R
import com.example.cafe_user.ui.cart.CartItems
import com.example.cafe_user.ui.cart.CartMenu
import com.example.cafe_user.ui.cart.CartMenuDB
import com.example.cafe_user.ui.menu.OrderStateAdapter
import kotlinx.android.synthetic.main.activity_o_state.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OState : Fragment() {
    var dataList:ArrayList<CartItems> = ArrayList()

    private var stateData: CartMenuDB? = null
    var progressVal:Int = 0
    lateinit var handler1: Handler

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        stateData = context?.let { CartMenuDB(it) }
        return inflater.inflate(R.layout.activity_o_state, container, false)
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val stateAdapter = OrderStateAdapter(activity as MainActivity, R.layout.order_state_row, dataList)

        CoroutineScope(Main).launch {
            launch {
                delay(500L)
                stateCartDb()
            }
            launch {
                delay(500L)
                val manager = LinearLayoutManager(activity as MainActivity)
                manager.orientation = LinearLayoutManager.VERTICAL
                state_list.adapter = stateAdapter
                state_list.layoutManager = manager
            }

            // ?????? ?????? ????????? Bundle ??????, ???????????? ???????????? ????????? ?????? ?????? ?????? ???????????? ????????????
            // ?????? ????????? ????????? ????????? ??? ????????? ????????? ????????? ??????
            launch {
                delay(500L)
                var totalMenuPay:Int = 0
                for (i in dataList.indices) {
                   totalMenuPay += dataList[i].cart_item_price!! * dataList[i].cart_item_count!!
                }
                total_price.text = "$totalMenuPay ???"
                adjusted_total_price.text = "$totalMenuPay ???"
            }
        }

        // ?????? ?????? ?????? ??????
        orderState_button.setOnClickListener {
            CoroutineScope(IO).launch {
                launch {
                    for (i in 1..100) {
                        SystemClock.sleep(40)
                        order_progress.progress = i
                        CoroutineScope(Main).launch {
                            showOrderState(i)
                        }
                    }
                }
            }
        }
    }
    private fun showOrderState(progressValue:Int) {
        if (progressValue == 50) {
            orderState_text.text = "????????? ??????????????????."
            orderState_img.setImageResource(R.drawable.wait)
            orderState_detail_msg.text = "???????????? ????????? ????????? ????????? ????????? ????????????."
        } else if (progressValue == 100) {
            orderState_text.text = "????????? ?????????????????????."
            orderState_img.setImageResource(R.drawable.complete)
            orderState_detail_msg.text = "????????? ?????????????????????."
            makeAlarm()
        }
    }

    private fun makeAlarm() {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.complete)
        val builder = NotificationCompat.Builder(activity as MainActivity, "1111")
                .setSmallIcon(android.R.drawable.ic_notification_overlay)
                .setContentTitle("?????? ??????")
                .setContentText("???????????? ?????? ???????????????~~~~")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setLargeIcon(bitmap)
                .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE or Notification.DEFAULT_LIGHTS)

        val style = NotificationCompat.BigPictureStyle(builder)
        style.bigPicture(bitmap)

        builder.setStyle(style)
        createNotiChannel(builder, "1111")
    }

    private fun createNotiChannel(builder : NotificationCompat.Builder, id : String) {
        //?????? ????????? ???????????? ?????? ??????
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(id, "mynetworkchannel", NotificationManager.IMPORTANCE_HIGH)
            val notificationManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            notificationManager.notify(Integer.parseInt(id), builder.build())
        } else {
            //?????? ????????? ??????
            val notificationManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(Integer.parseInt(id), builder.build())
        }
    }

    // ????????? ????????? ?????? ????????? ???????????? ??????
    private fun stateCartDb() {
        val menuState = stateData?.selectOstate()
        for (i in menuState!!.indices) {
            val menuItem = makeStateItem(menuState[i])
            dataList.add(menuItem)
        }
    }

    private fun makeStateItem(item: CartMenu): CartItems {
        return if (item.temp_option == 0) {
            CartItems(item.cart_id, R.drawable.ic_launcher_background, item.menu_name,
                item.size_option, item.menu_count, item.menu_price, false, cart_item_temp = false)
        } else {
            CartItems(item.cart_id, R.drawable.ic_launcher_background, item.menu_name,
                item.size_option, item.menu_count, item.menu_price, false, cart_item_temp = true)
        }
    }
}