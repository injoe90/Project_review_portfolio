package com.example.cafe_user.ui.cart



import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafe_user.R
import kotlinx.android.synthetic.main.activity_cart.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.cafe_user.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.NullPointerException
import kotlin.random.Random as Random


class CartFragment : Fragment() {
    private var cartData:CartMenuDB? = null
    private var cartDataList = ArrayList<CartItems>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        cartData = context?.let { CartMenuDB(it) }

        // HomeFragment에서 판촉 상품을 장바구니에 넣었을 때 DB Insert 기능을 수행
        insertCartMenuDb()

        return inflater.inflate(R.layout.activity_cart, container, false)
    }

    var recommendDrunk:Int = 0
    var totalMenuPay = 0
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cart_adapter = SwipeListAdapter(activity as MainActivity, R.layout.item_swipe, cartDataList)

        // 추천음료 메뉴 기능: 추후에 AI나 빅데이터를 활용
        recommendDrunk = Random.nextInt(1, 4)
        when (recommendDrunk) {
            1 -> {
                banner_view.setImageResource(R.drawable.coffee_banner)
            }
            2 -> {
                banner_view.setImageResource(R.drawable.juice_banner)
            }
            3 -> {
                banner_view.setImageResource(R.drawable.smoothie_banner)
            }
        }

        // DB에 저장된 정보를 리사이클러 뷰에 출력하는 기능: 코루틴이나 다른 이벤트 리스너로 처리하면 출력이 되지 않음,
        // 실시간 변화를 반영하지 못함
        CoroutineScope(Main).launch {
            launch {
                delay(500L)
                selectCartDb()
            }
            launch {
                delay(500L)
                val manager = LinearLayoutManager(activity as MainActivity)

                manager.orientation = LinearLayoutManager.VERTICAL
                cart_recycler.layoutManager = manager
                cart_recycler.adapter = cart_adapter
            }

            launch {
                delay(500L)
                for (i in cartDataList.indices) {
                    totalMenuPay += cartDataList[i].cart_item_price!!
                }
                total_txt.text = "총 주문금액: $totalMenuPay 원"
            }
        }

        // 추천 메뉴 대화 상자
        cart_dialog.setOnClickListener {
            val dlg = CartDialog(activity as MainActivity)
            dlg.setOnOKClickedListener {menuLocation ->
                if (menuLocation != "") {
                    Toast.makeText(context, menuLocation, Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(cart_dialog).navigate(R.id.cart_to_order)
                }
            }
            dlg.start(recommendDrunk)
        }

        // 결제를 누르면 itemState 변수 값이 1이 되고 OState 화면으로 이동
        // 추후에 대화 상자와 로그인 기능을 이용하여 할인 여부를 선택할 수 있도록 변경
        // 카카오톡 API를 이용해서 결제 기능 추가
        // 네트워크 코루틴을 통해 Django 서버를 경유하여 MariaDB에 데이터를 입력
        btn_payment.setOnClickListener {
            CoroutineScope(Default).launch {
                launch {
                    delay(500L)
                    for (i in cartDataList.indices) {
                        val item = cartDataList[i]
                        val itemId = item.cart_id!!
                        cartData?.cartStateUpdate(itemId, 1)
                    }
                }
            }
            Navigation.findNavController(btn_payment).navigate(R.id.cart_to_ostate)
        }

        // 리사이클러 뷰 내 목록을 하나씩 제거하는 기능
        btn_del.setOnClickListener {
            Toast.makeText(context, "${cart_adapter.getId(cart_adapter.getPosition())}", Toast.LENGTH_SHORT).show()
            CoroutineScope(Default).launch {
                launch {
                    delay(500L)
                    cartData?.cartDelete(cart_adapter.getId(cart_adapter.getPosition()))
                    try {
                        val deleteItem = cart_adapter.getItem()
                        CoroutineScope(Main).launch {
                            cartDataList.remove(deleteItem)
                            cart_adapter.notifyDataSetChanged()
                        }
                    } catch (e: NullPointerException) {
                        CoroutineScope(Main).launch {
                            Toast.makeText(context, "체크 상자를 눌러주세요", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    // DB에 Bundle 객체로 넘어온 데이터를 입력하는 기능
    private fun insertCartMenuDb() {
        CoroutineScope(Default).launch() {
            val insertJob = launch {
                if (arguments?.getInt("menu_id") != null) {
                    insertCartDb()
                }
            }
            insertJob.invokeOnCompletion { throwable ->
                if (throwable != null) {
                    Toast.makeText(context, "오류 발생: $throwable", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private suspend fun insertCartDb() {
        delay(500L)
        val itemTable = Random.nextInt(6) + 1
        val itemId = arguments?.getInt("menu_id")
        val itemName = arguments?.getString("menu_name")
        val itemCount = arguments?.getInt("menu_count")
        val itemTemp = arguments?.getInt("temp_option")
        val itemSize = arguments?.getString("size_option")
        val itemPrice = arguments?.getInt("menu_price")
        val itemState = 0

        Log.d("order", "$itemId, $itemName, $itemCount, $itemTemp, $itemSize, $itemPrice")
        val cartItem = CartMenu(itemTable, itemId!!, itemName!!, itemCount!!, itemTemp!!, itemSize!!, itemPrice!!, itemState)
        cartData?.insert(cartItem)
    }

    // DB에 있는 정보를 꺼내는 기능
    private fun selectCartDb() {
        val itemList: ArrayList<CartMenu>? = cartData?.selectCart()

        for (i in itemList!!.indices) {
            val cartItem = itemList[i]
            when (cartItem.menu_id) {
                10 -> {
                    makeCartItem(cartItem, R.drawable.americano_promotion, cartDataList)
                }
                11 -> {
                    makeCartItem(cartItem, R.drawable.cafelatte_promotion, cartDataList)
                }
                12 -> {
                    makeCartItem(cartItem, R.drawable.cafemocca_promotion, cartDataList)
                }

                20 -> {
                    makeCartItem(cartItem, R.drawable.strawberry_ade, cartDataList)
                }
                21 -> {
                    makeCartItem(cartItem, R.drawable.grapefruit_promotion, cartDataList)
                }
                22 -> {
                    makeCartItem(cartItem, R.drawable.grape_promotion, cartDataList)
                }

                30 -> {
                    makeCartItem(cartItem, R.drawable.kiwi_promotion, cartDataList)
                }
                31 -> {
                    makeCartItem(cartItem, R.drawable.pineapple_promotion, cartDataList)
                }
                32 -> {
                    makeCartItem(cartItem, R.drawable.mango_promotion, cartDataList)
                }
            }
        }
    }

    // 리사이클러 뷰에 연결한 데이터 리스트에 DB 정보를 넣는 기능
    private fun makeCartItem(itemView: CartMenu, img: Int, dataList:ArrayList<CartItems>) {
        val displayItem: CartItems
        if (itemView.temp_option == 1) {
            displayItem = CartItems(itemView.cart_id, img, itemView.menu_name, itemView.size_option,
                    itemView.menu_count, itemView.menu_price, cart_check = false, cart_item_temp = true)
            dataList.add(displayItem)
        } else {
            displayItem = CartItems(itemView.cart_id, img, itemView.menu_name, itemView.size_option,
                    itemView.menu_count, itemView.menu_price, cart_check = false, cart_item_temp = false)
            dataList.add(displayItem)
        }
    }

}

