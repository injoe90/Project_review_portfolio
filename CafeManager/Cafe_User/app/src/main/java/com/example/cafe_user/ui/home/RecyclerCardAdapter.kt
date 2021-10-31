package com.example.cafe_user.ui.home

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.cafe_user.R
import com.example.cafe_user.ui.cart.CartFragment
import kotlinx.android.synthetic.main.main_card_view_menu.view.*
import kotlin.collections.ArrayList


class RecyclerCardAdapter(var context: Context, var itemlayout: Int, var main_menulist: ArrayList<MenuRecycleItem>)
                                                        : RecyclerView.Adapter<RecyclerCardAdapter.MyViewHolder>() {
    var orderFragmentState = 0
    val cartFragment = CartFragment()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerCardAdapter.MyViewHolder {
        //항목으로 사용할 View 객체 생성
        val itemView = LayoutInflater.from(context).inflate(itemlayout, null)
        val myViewHolder = MyViewHolder(itemView)

        return myViewHolder
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photoImg = itemView.main_cardimageView
        val infotext = itemView.main_cardinfotext

    }

    //MyViewHolder에 저장된 CircleImageView 꺼내서 데이터 연결
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: RecyclerCardAdapter.MyViewHolder, position: Int) {

        val myPhotoImg = holder.photoImg
        val myInfoText = holder.infotext
        //ViewHolder에서 꺼낸 텍스트뷰에 데이터 연결
        myPhotoImg.setImageResource(main_menulist[position].photo)
        myInfoText.text = main_menulist[position].menu_name

        // imageView를 눌렀을 때 대화 상자가 출력
        myPhotoImg.setOnClickListener {
            homeDialog(position, myPhotoImg)
        }

    }

    //RecyclerView에 출력할 데이터 갯수 리턴
    override fun getItemCount(): Int {
        return main_menulist.size
    }

    // 대화 상자에서 특정 버튼을 눌렀을 때 실행하는 이벤트 처리
    @RequiresApi(Build.VERSION_CODES.N)
    fun homeDialog(position: Int, navView:View) {
        val dlg = MenuDialog(context)
        dlg.setOnOKClickedListener { fragmentState ->
            orderFragmentState = fragmentState
            // 오류를 의미하는 코드를 받았을 때와 메뉴 ID를 수신했을 때를 구분하여 예외 발생을 해소
            if (orderFragmentState == 2) {
                Toast.makeText(context, "아직 상품을 준비하고 있습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "장바구니에 추가되었습니다.", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(navView).navigate(R.id.home_to_cart, dataCartDb(orderFragmentState))
            }
        }
        dlg.start(position)
    }

    // DB에 저장할 Bundle 작업
    private fun dataCartDb(menuId: Int): Bundle {
        val bundle = Bundle()

        when (menuId) {
            10 -> {
                bundle.putInt("menu_id", menuId)
                bundle.putString("menu_name", "아메리카노")
                bundle.putInt("menu_count", 1)
                bundle.putInt("temp_option", 1)
                bundle.putString("size_option", "G")
                bundle.putInt("menu_price",2000)
            }
            11, 12 -> {
                if (menuId == 11) {
                    bundle.putInt("menu_id", menuId)
                    bundle.putString("menu_name", "카페라떼")
                } else if (menuId == 12) {
                    bundle.putInt("menu_id", menuId)
                    bundle.putString("menu_name", "카페모카")
                }
                bundle.putInt("menu_count", 1)
                bundle.putInt("temp_option", 1)
                bundle.putString("size_option", "G")
                bundle.putInt("menu_price", 3000)
            }
            20, 21, 22 -> {
                when (menuId) {
                    20 -> {
                        bundle.putInt("menu_id", menuId)
                        bundle.putString("menu_name", "딸기 에이드")
                    }
                    21 -> {
                        bundle.putInt("menu_id", menuId)
                        bundle.putString("menu_name", "자몽 에이드")
                    }
                    22 -> {
                        bundle.putInt("menu_id", menuId)
                        bundle.putString("menu_name", "청포도 에이드")
                    }
                }
                bundle.putInt("menu_count", 1)
                bundle.putInt("temp_option", 1)
                bundle.putString("size_option", "G")
                bundle.putInt("menu_price", 3500)
            }
            30, 31, 32 ->{
                when (menuId) {
                    30 -> {
                        bundle.putInt("menu_id", menuId)
                        bundle.putString("menu_name", "키위 스무디")
                    }
                    31 -> {
                        bundle.putInt("menu_id", menuId)
                        bundle.putString("menu_name", "파인애플 코코넛 스무디")
                    }
                    32 -> {
                        bundle.putInt("menu_id", menuId)
                        bundle.putString("menu_name", "망고 용과 스무디")
                    }
                }
                bundle.putInt("menu_count", 1)
                bundle.putInt("temp_option", 1)
                bundle.putString("size_option", "G")
                bundle.putInt("menu_price", 4500)
            }
        }
        return bundle
    }
}