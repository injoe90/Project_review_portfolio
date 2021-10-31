package com.example.cafe_user.ui.cart

import android.annotation.SuppressLint
import android.app.Dialog
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.cafe_user.R
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.random.Random
import android.content.Context as Context

class CartDialog(context: Context) {
    // 부모 액티비티 및 프래그먼트의 context가 인수로 된다.
    private val dlg = Dialog(context)

    // 부모 액티비티 및 프래그먼트에서 사용할 리스터 변수 생성
    private lateinit var listener: CartDialogOKClickedListener

   // 대화 상자 레이아웃의 View ID 변수 생성
    private lateinit var btnOk: Button
    private lateinit var btnClose: Button

    private lateinit var menuImg: ImageView
    private lateinit var menuName: TextView
    private lateinit var menuDetail: TextView

    var menuLocation:String = ""
    fun start(position: Int) {
        // 타이틀 바 제거
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        // 대화 상자에 사용할 layout 파일을 불러온다.
        dlg.setContentView(R.layout.cart_dailog)
//        // 대화 상자 바깥 영역을 눌렀을 때 닫히지 않게 한다.
//        dlg.setCancelable(false)

        // View ID 연결
        btnOk = dlg.findViewById(R.id.btn_menu)
        btnClose = dlg.findViewById(R.id.btn_close)

        menuImg = dlg.findViewById(R.id.dialog_image)
        menuName = dlg.findViewById(R.id.dialog_name)
        menuDetail = dlg.findViewById(R.id.dialog_detail)

        // 판촉 상품 표시: 할인이 없음
        when (position) {
            // 커피 상품
            1 -> {
                val coffeeImg = mutableListOf(R.drawable.americano_promotion, R.drawable.cafelatte_promotion, R.drawable.cafemocca_promotion)
                val coffeeName = mutableListOf("아메리카노", "카페라떼", "카페모카")
                val coffeeDetail = mutableListOf("잠을 깨워주는", "점심을 마무리하는", "오늘 고생한 나를 위한")
                promotionGoods("커피", coffeeImg, coffeeName, coffeeDetail)

            }

            // 생과일 에이드 상품
            2 -> {
                val coffeeImg = mutableListOf(R.drawable.strawberry_promotion, R.drawable.grapefruit_promotion, R.drawable.grape_promotion)
                val coffeeName = mutableListOf("딸기 에이드", "자몽 에이드", "청포도 에이드")
                val coffeeDetail = mutableListOf("아침을 상괘하게 시작하는", "점심을 마무리하는", "오늘 고생한 나를 위한")
                promotionGoods("생과일 에이드", coffeeImg, coffeeName, coffeeDetail)
            }

            // 스무디 상품
            3 -> {
                val coffeeImg = mutableListOf(R.drawable.kiwi_promotion, R.drawable.pineapple_promotion, R.drawable.mango_promotion)
                val coffeeName = mutableListOf("키위 스무디", "파인애플 코코넛 스무디", "망고 용과 스무디")
                val coffeeDetail = mutableListOf("속 쓰린 아침을 달래는", "점심을 마무리하는", "방전된 나를 충전하는")
                promotionGoods("스무디", coffeeImg, coffeeName, coffeeDetail)
            }
        }

        // 각 버튼에 대한 이벤트 처리
        // 부모 액티비티 및 프래그먼트에서 처리할 작업에 관한 메서드를 입력
        btnOk.setOnClickListener {
            listener.onOKClicked(menuLocation)
            dlg.dismiss()
        }

        btnClose.setOnClickListener {
            dlg.dismiss()
        }
        dlg.show()
    }
    // 부모 액티비티 및 프래그먼트에서 실행할 콜백 인터페이스
    fun setOnOKClickedListener(listener: (String) -> Unit) {
        this.listener = object : CartDialogOKClickedListener {
            override fun onOKClicked(menuLocation: String) {
                listener(menuLocation)
            }
        }
    }

    interface CartDialogOKClickedListener {
        fun onOKClicked(menuLocation: String)
    }

    // 추천 상품 선택
    private fun promotionGoods(category:String, Img: MutableList<Int>, name: MutableList<String>, detail: MutableList<String>) {
        when (Random.nextInt(1, 4)) {
            1 -> {
                menuImg.setImageResource(Img[0])
                menuDetail.text = detail[0]
                menuName.text = name[0]

                menuLocation = "${detail[0]} ${name[0]}는 $category 메뉴에 있습니다"
            }
            2 -> {
                menuImg.setImageResource(Img[1])
                menuDetail.text = detail[1]
                menuName.text = name[1]

                menuLocation = "${detail[0]} ${name[0]}는 $category 메뉴에 있습니다"
            }
            3 -> {
                menuImg.setImageResource(Img[2])
                menuDetail.text = detail[2]
                menuName.text = name[2]

                menuLocation = "${detail[0]} ${name[0]}는 $category 메뉴에 있습니다"
            }
        }
    }
}