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

            // 추후 대화 상자와 Bundle 객체, 로그인을 이용하여 포인트 차감 혹은 할인 이벤트를 적용하여
            // 실제 지불한 금액을 표시할 수 있도록 기능을 추가할 계획
            launch {
                delay(500L)
                var totalMenuPay:Int = 0
                for (i in dataList.indices) {
                   totalMenuPay += dataList[i].cart_item_price!! * dataList[i].cart_item_count!!
                }
                total_price.text = "$totalMenuPay 원"
                adjusted_total_price.text = "$totalMenuPay 원"
            }
        }

        // 주문 상태 확인 기능
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
            orderState_text.text = "주문을 접수했습니다."
            orderState_img.setImageResource(R.drawable.wait)
            orderState_detail_msg.text = "주방에서 직원이 열심히 메뉴를 만들고 있습니다."
        } else if (progressValue == 100) {
            orderState_text.text = "주문이 완료되었습니다."
            orderState_img.setImageResource(R.drawable.complete)
            orderState_detail_msg.text = "메뉴가 완성되었습니다."
            makeAlarm()
        }
    }

    private fun makeAlarm() {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.complete)
        val builder = NotificationCompat.Builder(activity as MainActivity, "1111")
                .setSmallIcon(android.R.drawable.ic_notification_overlay)
                .setContentTitle("주문 완료")
                .setContentText("주문하신 메뉴 받아가세요~~~~")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setLargeIcon(bitmap)
                .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE or Notification.DEFAULT_LIGHTS)

        val style = NotificationCompat.BigPictureStyle(builder)
        style.bigPicture(bitmap)

        builder.setStyle(style)
        createNotiChannel(builder, "1111")
    }

    private fun createNotiChannel(builder : NotificationCompat.Builder, id : String) {
        //낮은 버전의 사용자에 대한 설정
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(id, "mynetworkchannel", NotificationManager.IMPORTANCE_HIGH)
            val notificationManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            notificationManager.notify(Integer.parseInt(id), builder.build())
        } else {
            //이전 버전인 경우
            val notificationManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(Integer.parseInt(id), builder.build())
        }
    }

    // 결제가 완료된 메뉴 목록을 표시하는 기능
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