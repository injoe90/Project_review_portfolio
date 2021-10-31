package com.example.cafe_admin.ui.store

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafe_admin.MainActivity
import com.example.cafe_admin.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.storage_menu_test.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class StorageFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.storage_menu_test, container, false)
    }

    private var coffeeCounts = ArrayList<Float>()
    private var dairyCounts = ArrayList<Float>()
    private var dessertCounts = ArrayList<Float>()
    private var fruitCounts = ArrayList<Float>()
    private var macaronCounts = ArrayList<Float>()

    // 리사이클러 뷰에 이용할 변수
    private val coffeeId = ArrayList<Int>()
    private val coffeeItem = ArrayList<String>()
    private val coffeeCountInt = ArrayList<Int>()

    private val dairyId = ArrayList<Int>()
    private val dairyItem = ArrayList<String>()
    private val dairyCountInt = ArrayList<Int>()

    private val dessertId = ArrayList<Int>()
    private val dessertItem = ArrayList<String>()
    private val dessertCountInt = ArrayList<Int>()

    private val fruitId = ArrayList<Int>()
    private val fruitItem = ArrayList<String>()
    private val fruitCountInt = ArrayList<Int>()

    private val macaronId = ArrayList<Int>()
    private val macaronItem = ArrayList<String>()
    private val macaronCountInt = ArrayList<Int>()

    // 더미변수: 코루틴으로 데이터를 한 번만 가져올 수 있도록 하는 변수
    var dataInsertTime: Int = 0
    var cartInsertTime: Int = 0
    var coffeeInsertTime: Int = 0
    var dairyInsertTime: Int = 0
    var fruitInsertTime: Int = 0
    var dessertInsertTime: Int = 0
    var macaronInsertTime: Int = 0
    private val localHostIp: String = "http://172.30.1.17:8000"

    var dataDetailList = ArrayList<StorageItem>()
    var storageCartList = ArrayList<StorageItem>()

    @SuppressLint("RtlHardcoded")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 슬라이드 화면을 여는 기능
        storage_center.setOnClickListener {
            if (!graphDrawer.isDrawerOpen(Gravity.LEFT)) {
                graphDrawer.openDrawer(Gravity.LEFT)
            }
        }

        // 슬라이드 화면을 닫는 기능
        graphDrawerClose.setOnClickListener {
            graphDrawer.closeDrawer(Gravity.LEFT)
        }

        // 슬라이드 화면 초기 설정
        setGraphAxis()

        // Django Web Server에서 데이터를 가져오는 스레드 작업 처리
        storage_data.setOnClickListener {
            if (dataInsertTime == 0) {
                Toast.makeText(context, "서버에서 데이터를 받고 있습니다", Toast.LENGTH_SHORT).show()
                CoroutineScope(IO).launch {
                    launch {
                        makeGraphData("coffee", "bean_name", "bean_count",
                                coffeeId, coffeeItem, coffeeCountInt, coffeeCounts)
                    }
                    launch {
                        makeGraphData("dairy", "dairy_name", "dairy_count",
                                dairyId, dairyItem, dairyCountInt, dairyCounts)
                    }
                    launch {
                        makeGraphData("dessert", "dessert_name", "dessert_count",
                                dessertId, dessertItem, dessertCountInt, dessertCounts)
                    }
                    launch {
                        makeGraphData("fruit", "fruit_name", "fruit_count",
                                fruitId, fruitItem, fruitCountInt, fruitCounts)
                    }
                    launch {
                        makeGraphData("macaron", "mac_name", "mac_count",
                                macaronId, macaronItem, macaronCountInt, macaronCounts)
                    }
                }
                dataInsertTime = 1
            } else {
                Toast.makeText(context, "서버에서 데이터를 받았습니다", Toast.LENGTH_SHORT).show()
            }

        }


        // 그래프 그리는 작업 처리
        coffee_storage.setOnClickListener {
            if (coffeeInsertTime == 0) {
                showDetailData(coffeeCounts, "coffee", coffeeId, coffeeItem, coffeeCountInt)

                coffeeInsertTime = 1
                dairyInsertTime = 0
                dessertInsertTime = 0
                fruitInsertTime = 0
                macaronInsertTime = 0
            } else {
                Toast.makeText(context, "커피 식재료 데이터가 입력되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        dairy_storage.setOnClickListener {
            if (dairyInsertTime == 0) {
                showDetailData(dairyCounts, "dairy", dairyId, dairyItem, dairyCountInt)

                coffeeInsertTime = 0
                dairyInsertTime = 1
                dessertInsertTime = 0
                fruitInsertTime = 0
                macaronInsertTime = 0
            } else {
                Toast.makeText(context, "유제품 식재료 데이터가 입력되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        dessert_storage.setOnClickListener {
            if (dessertInsertTime == 0) {
                showDetailData(dessertCounts, "dessert", dessertId, dessertItem, dessertCountInt)

                coffeeInsertTime = 0
                dairyInsertTime = 0
                dessertInsertTime = 1
                fruitInsertTime = 0
                macaronInsertTime = 0
            } else {
                Toast.makeText(context, "디저트 식재료 데이터가 입력되었습니다.", Toast.LENGTH_SHORT).show()
            }

        }
        fruit_storage.setOnClickListener {
            if (fruitInsertTime == 0) {
                showDetailData(fruitCounts, "fruit", fruitId, fruitItem, fruitCountInt)

                coffeeInsertTime = 0
                dairyInsertTime = 0
                dessertInsertTime = 0
                fruitInsertTime = 1
                macaronInsertTime = 0
            } else {
                Toast.makeText(context, "생과일 식재료 데이터가 입력되었습니다.", Toast.LENGTH_SHORT).show()
            }

        }
        macaron_storage.setOnClickListener {
            if (macaronInsertTime == 0) {
                showDetailData(macaronCounts, "macaron", macaronId, macaronItem, macaronCountInt)

                coffeeInsertTime = 0
                dairyInsertTime = 0
                dessertInsertTime = 0
                fruitInsertTime = 0
                macaronInsertTime = 1
            } else {
                Toast.makeText(context, "생과일 식재료 데이터가 입력되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        // 구매 목록 출력하기
        make_cart_list.setOnClickListener {
            if (cartInsertTime == 0) {
                Toast.makeText(context, "구매 목록을 불러오고 있습니다.", Toast.LENGTH_SHORT).show()
                makeCartList()
                cartInsertTime = 1
            } else {
                Toast.makeText(context, "이미 출력을 완료했습니다.", Toast.LENGTH_SHORT).show()
            }
            CoroutineScope(Main).launch {
                launch {
                    val cartAdapter = StorageCartAdapter(activity as MainActivity, R.layout.stock_cart_list, storageCartList)
                    val cartManager = LinearLayoutManager(context)

                    cartManager.orientation = LinearLayoutManager.VERTICAL

                    storage_cart_list.adapter = cartAdapter
                    storage_cart_list.layoutManager = cartManager
                }
            }
        }

        // 네이버 쇼핑으로 연결하는 암시적 인텐트 기능
        go_to_webCart.setOnClickListener {
            val uri = Uri.parse("https://shopping.naver.com/home/p/index.nhn")
            val cartIntent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(cartIntent)
        }
    }

    // Django Web Server에서 데이터를 가져오는 기능
    private fun getDjangoData(site: String): JSONArray {
        val url = URL(site)
        val con = url.openConnection() as HttpURLConnection
        val isr = InputStreamReader(con.inputStream, "UTF-8")
        val br = BufferedReader(isr)

        var str: String? = null

        val buf = StringBuffer()
        do {
            str = br.readLine()
            if (str != null) {
                buf.append(str)
            }
        } while (str != null)
        val data = buf.toString()

        return JSONArray(data)
    }

    private fun makeGraphData(storageName: String, itemName: String, itemCount: String, itemIdList: ArrayList<Int>,
                              itemNameList: ArrayList<String>, itemIntList: ArrayList<Int>, itemFloatList: ArrayList<Float>) {
        val site = "$localHostIp/$storageName"
        val root = getDjangoData(site)
        for (i in 0 until root.length()) {
            val jsonObj = root.getJSONObject(i)
            val idList = jsonObj.getInt("storage_id")
            val nameList = jsonObj.getString(itemName)
            val countList = jsonObj.getInt(itemCount)
            CoroutineScope(Main).launch {
                launch {
                    delay(500L)
                    itemIdList.add(idList)
                    itemNameList.add(nameList)
                    itemIntList.add(countList)
                    itemFloatList.add(countList.toFloat())
                }
            }
        }
    }

    // 구매 목록 작성하는 함수
    private fun findCartItem(cnt:Int, itemIdList: ArrayList<Int>,
                             itemNameList: ArrayList<String>, itemCountList: ArrayList<Int>) {
        for (i in 0 until itemCountList.size) {
            if (itemCountList[i] < cnt) {
                val itemCnt = cnt - itemCountList[i]
                val item = StorageItem(itemIdList[i], itemNameList[i], itemCnt)
                storageCartList.add(item)
            }
        }
    }

    private fun inputCartItem(keyWord: String, itemIdList: ArrayList<Int>,
                              itemNameList: ArrayList<String>, itemCountList: ArrayList<Int>) {
        when (keyWord) {
            "coffee" -> findCartItem(9, coffeeId, coffeeItem, coffeeCountInt)
            "dairy" -> findCartItem(10, dairyId, dairyItem, dairyCountInt)
            "dessert" -> findCartItem(5, dessertId, dessertItem, dessertCountInt)
            "fruit" -> findCartItem(8, fruitId, fruitItem, fruitCountInt)
            "macaron" -> findCartItem(8, macaronId, macaronItem, macaronCountInt)
        }
    }

    private fun makeCartList() {
        CoroutineScope(Main).launch {
            launch { inputCartItem("coffee", coffeeId, coffeeItem, coffeeCountInt) }
            launch { inputCartItem("dairy", dairyId, dairyItem, dairyCountInt) }
            launch { inputCartItem("dessert", dessertId, dessertItem, dessertCountInt) }
            launch { inputCartItem("fruit", fruitId, fruitItem, fruitCountInt) }
            launch { inputCartItem("macaron", macaronId, macaronItem, macaronCountInt) }
        }
    }


    // 데이터 리스트를 동적으로 변경한 리사이클러 뷰를 실행하는 함수
    private fun makeRecyclerData(itemIdList: ArrayList<Int>, itemNameList: ArrayList<String>,
                                 itemCountList: ArrayList<Int>) {
        dataDetailList = ArrayList()
        for (i in 0 until itemIdList.size) {
            val item = StorageItem(itemIdList[i], itemNameList[i], itemCountList[i])
            dataDetailList.add(item)
        }
        val storageAdapter = StorageItemListAdapter(activity as MainActivity, R.layout.stock_item_list, dataDetailList)

        val storageManager = LinearLayoutManager(context)

        storageManager.orientation = LinearLayoutManager.VERTICAL
        storage_detail.adapter = storageAdapter
        storage_detail.layoutManager = storageManager
    }

    // 원 그래프 작성하는 기능
    private fun showDetailData(stockCounts: ArrayList<Float>, keyWord: String,
                               itemIdList: ArrayList<Int>, itemNameList: ArrayList<String>, itemCountList: ArrayList<Int>) {
        makePieEntries(stockCounts, keyWord)
        makeRecyclerData(itemIdList, itemNameList, itemCountList)
        draw_pie_graph.centerText = keyWord
    }

    private fun makePieEntries(stockCounts: ArrayList<Float>, keyWord: String) {
        val stockEntries = ArrayList<PieEntry>()

        for (i in 0 until stockCounts.size) {
            val indexNum = i.toFloat()
            stockEntries.add(PieEntry(stockCounts[i], indexNum))
        }

        val pieDataSet = PieDataSet(stockEntries, "$keyWord  식재료 현황")
        pieDataSet.setColors(
                ContextCompat.getColor(draw_pie_graph.context, R.color.red),
                ContextCompat.getColor(draw_pie_graph.context, R.color.orange),
                ContextCompat.getColor(draw_pie_graph.context, R.color.yellow),
                ContextCompat.getColor(draw_pie_graph.context, R.color.yellow_green),
                ContextCompat.getColor(draw_pie_graph.context, R.color.green),
                ContextCompat.getColor(draw_pie_graph.context, R.color.teal_700),
                ContextCompat.getColor(draw_pie_graph.context, R.color.teal_200),
                ContextCompat.getColor(draw_pie_graph.context, R.color.blue),
                ContextCompat.getColor(draw_pie_graph.context, R.color.purple_200),
                ContextCompat.getColor(draw_pie_graph.context, R.color.purple_500)
        )
        pieDataSet.sliceSpace = 3f

        val pieData = PieData(pieDataSet)

        draw_pie_graph.data = pieData
        draw_pie_graph.invalidate()
    }

    // 원 그래프 양식 지정
    private fun setGraphAxis() {
        val pieLegend = draw_pie_graph.legend
        pieLegend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        pieLegend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        pieLegend.orientation = Legend.LegendOrientation.VERTICAL

        draw_pie_graph.setDrawCenterText(true)
    }
}