package com.example.cafe_kitchen.StorageManage

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafe_kitchen.MainActivity
import com.example.cafe_kitchen.R
import kotlinx.android.synthetic.main.fragment_storage_manager.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.NoRouteToHostException
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class StorageManageFragment:Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_storage_manager, container, false)
    }

    private var coffeeList = ArrayList<StorageItem>()
    private var dairyList = ArrayList<StorageItem>()
    private var dessertList = ArrayList<StorageItem>()
    private var fruitList = ArrayList<StorageItem>()
    private var macaronList = ArrayList<StorageItem>()

    private var idList = ArrayList<Int>()

    private var dataSelectTime:Int = 0
    private val localHostIp: String = "http://172.30.1.57:8000"

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nowDate()

        // Web Server에서 데이터를 가져오는 기능(Select)
        select_storageManage.setOnClickListener {
            if (dataSelectTime == 0) {
                Toast.makeText(context, "서버에서 데이터를 받고 있습니다", Toast.LENGTH_SHORT).show()
                CoroutineScope(IO).launch {
                    launch {
                        selectData("coffee", coffeeList, "bean_name",
                            "bean_count", "bean_date", "bean_shelf")
                    }
                    launch {
                        selectData("dairy", dairyList, "dairy_name",
                            "dairy_count", "dairy_date", "dairy_shelf")
                    }
                    launch {
                        selectData("dessert", dessertList, "dessert_name",
                            "dessert_count", "dessert_date", "dessert_shelf")
                    }
                    launch {
                        selectData("fruit", fruitList, "fruit_name",
                            "fruit_count", "fruit_date", "fruit_shelf")
                    }
                    launch {
                        selectData("macaron", macaronList, "mac_name",
                            "mac_count", "mac_date", "mac_shelf")
                    }
                }
                dataSelectTime = 1
            } else {
                Toast.makeText(context, "서버에서 데이터를 받았습니다", Toast.LENGTH_SHORT).show()
            }
        }

        // 라디오 버튼을 눌렀을 때 재고 현황과 폐기 주의 목록을 보여주는 기능
        coffee_storageManage.setOnClickListener {
            recyclerManager(coffeeList)
            deleteListManager(coffeeList)
            makeIdList(coffeeList)
        }
        dairy_storageManage.setOnClickListener {
            recyclerManager(dairyList)
            deleteListManager(dairyList)
            makeIdList(dairyList)
        }
        dessert_storageManage.setOnClickListener {
            recyclerManager(dessertList)
            deleteListManager(dessertList)
            makeIdList(dessertList)
        }
        fruit_storageManage.setOnClickListener {
            recyclerManager(fruitList)
            deleteListManager(fruitList)
            makeIdList(fruitList)
        }
        macaron_storageManage.setOnClickListener {
            recyclerManager(macaronList)
            deleteListManager(macaronList)
            makeIdList(macaronList)
        }

        // 날짜를 입력하는 기능
        entranceDate_storageManage.setOnClickListener {
            dateInsert(entranceDate_storageManage)
        }

        expireDate_storageManage.setOnClickListener {
            dateInsert(expireDate_storageManage)
        }

        // 모든 화면의 내용을 초기화하는 기능
        refresh_storageManage.setOnClickListener {
            textInit()
            listInit()
            dataSelectTime = 0
        }

        // Server에 데이터를 입력하는 기능
        insert_StorageManage.setOnClickListener {
            if (id_storageManage.text.toString() != "" && name_storageManage.text.toString() != ""
                    && count_storageManage.text.toString() != "" && entranceDate_storageManage.text.toString() != ""
                    && expireDate_storageManage.text.toString() != "") {
                when (id_storageManage.text.toString().toInt() / 10) {
                    1 -> {
                        insertItemDB("mac_name", "mac_count",
                                "mac_date", "mac_shelf")
                    }
                    2 -> {
                        insertItemDB("bean_name", "bean_count",
                                "bean_date", "bean_shelf")
                    }
                    3 -> {
                        insertItemDB("fruit_name", "fruit_count",
                                "fruit_date", "fruit_shelf")
                    }
                    4 -> {
                        insertItemDB("dessert_name", "dessert_count",
                                "dessert_date", "dessert_shelf")
                    }
                    5 -> {
                        insertItemDB("dairy_name", "dairy_count",
                                "dairy_date", "dairy_shelf")
                    }
                }
            } else {
                Toast.makeText(context, "빈 곳이 남아있습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        // Web Server에 데이터를 갱신하는 기능
       update_storageManage.setOnClickListener {
            Toast.makeText(context, "${idList.size}", Toast.LENGTH_SHORT).show()
            if (id_storageManage.text.toString() != "" && name_storageManage.text.toString() != ""
                    && count_storageManage.text.toString() != "" && entranceDate_storageManage.text.toString() != ""
                    && expireDate_storageManage.text.toString() != "") {

                val idNumber = id_storageManage.text.toString().toInt()

                if (idList.contains(idNumber)) {
                    when (id_storageManage.text.toString().toInt() / 10) {
                        1 -> {
                            updateItemDB("mac_name", "mac_count",
                                    "mac_date", "mac_shelf")
                        }
                        2 -> {
                            updateItemDB("bean_name", "bean_count",
                                    "bean_date", "bean_shelf")
                        }
                        3 -> {
                            updateItemDB("fruit_name", "fruit_count",
                                    "fruit_date", "fruit_shelf")
                        }
                        4 -> {
                            updateItemDB("dessert_name", "dessert_count",
                                    "dessert_date", "dessert_shelf")
                        }
                        5 -> {
                            updateItemDB("dairy_name", "dairy_count",
                                    "dairy_date", "dairy_shelf")
                        }
                    }
                } else {
                    Toast.makeText(context, "현재 식자재 품목에 없는 id가 포함되어 있습니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "빈 곳이 남아있습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun nowDate() {
        val nowDay = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA).format(nowDay)
        nowDate_storageManage.text = dateFormat
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

    private fun selectData(storageName:String, dataList: ArrayList<StorageItem>, itemName:String,
                           itemCount:String, entranceDate:String, expireDate:String) {
        try {
            val site = "$localHostIp/$storageName"
            val root = getDjangoData(site)
            for (i in 0 until root.length()) {
                val jsonObj = root.getJSONObject(i)
                val item = StorageItem(jsonObj.getInt("storage_id"), jsonObj.getString(itemName),
                        jsonObj.getInt(itemCount), jsonObj.getString(entranceDate), jsonObj.getString(expireDate))
                CoroutineScope(Main).launch {
                    delay(250L)
                    dataList.add(item)
                }
            }
        } catch (e: NoRouteToHostException) {
            CoroutineScope(Main).launch {
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 현재 품목별 id 목록을 만드는 기능
    private fun makeIdList(dataList:ArrayList<StorageItem>) {
        for (i in 0 until dataList.size) {
            val idItem = dataList[i].id
            idList.add(idItem)
        }
    }

    // 품목별 식자재 재고 현황을 리사이클러 뷰로 보여주는 기능

    private fun recyclerManager(dataList:ArrayList<StorageItem>) {
        CoroutineScope(Main).launch {
            val adapter = StorageSelectAdapter(activity as MainActivity, R.layout.recycler_item_storage_manager, dataList)
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.VERTICAL

            itemList_storageManage.adapter = adapter
            itemList_storageManage.layoutManager = manager
        }
    }

    // 폐기 주의 목록을 리사이클러 뷰로 보여주는 기능

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.N)
    private fun deleteListManager(dataList: ArrayList<StorageItem>) {
        CoroutineScope(IO).launch {
            val deleteDataList = ArrayList<StorageItemDelete>()
            val today = Calendar.getInstance().time
            for (i in 0 until dataList.size) {
                val expireDay = SimpleDateFormat("yyyy-MM-dd").parse(dataList[i].expireDate)
                val cnt = (expireDay.time - today.time) / 60 * 60 * 24 * 1000
                if (cnt <= 0) {
                    val item = StorageItemDelete(0, dataList[i].id, dataList[i].name, dataList[i].count,
                        dataList[i].entranceDate, dataList[i].expireDate)
                    deleteDataList.add(item)
                } else if (cnt in 1..2) {
                    val item = StorageItemDelete(1, dataList[i].id, dataList[i].name, dataList[i].count,
                        dataList[i].entranceDate, dataList[i].expireDate)
                    deleteDataList.add(item)
                }
            }
            CoroutineScope(Main).launch {
                if (deleteDataList.size == 0) {
                    Toast.makeText(context, "대상 품목이 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                    val adapter = DeleteItemAdapter(activity as MainActivity, R.layout.recycler_item_delete_storage_manager, deleteDataList)
                    val manager = LinearLayoutManager(context)
                    manager.orientation = LinearLayoutManager.VERTICAL

                    removeList_storageManage.adapter = adapter
                    removeList_storageManage.layoutManager = manager
                } else {
                    val adapter = DeleteItemAdapter(activity as MainActivity, R.layout.recycler_item_delete_storage_manager, deleteDataList)
                    val manager = LinearLayoutManager(context)
                    manager.orientation = LinearLayoutManager.VERTICAL

                    removeList_storageManage.adapter = adapter
                    removeList_storageManage.layoutManager = manager
                }
            }
        }
    }

    // 날짜를 입력하는 기능
    private fun dateInsert(dateInputView: TextView) {
        val selectDay = Calendar.getInstance()
        var dateString = ""
        val dateSetListener = DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
            var selectMonth = ""
            var selectDay =""
            selectMonth = if (month + 1 < 10) {
                "0${month + 1}"
            } else {
                "${month + 1}"
            }

            selectDay = if (dayOfMonth < 10) {
                "0$dayOfMonth"
            } else {
                "$dayOfMonth"
            }
            dateString = "$year-$selectMonth-$selectDay"
            dateInputView.text = dateString
        }
        DatePickerDialog(activity as MainActivity, dateSetListener, selectDay.get(Calendar.YEAR),
                selectDay.get(Calendar.MONTH), selectDay.get(Calendar.DAY_OF_MONTH)).show()
    }

    // Web Server로 입력 데이터를 전송하는 기능:Insert

    private fun insertItemDB(storageName:String,
                             storageCount:String, storageEntrance:String, storageExpire:String) {
        CoroutineScope(IO).launch {
            launch {
                delay(250L)
                val site = "$localHostIp/input"
                val jsonObj = JSONObject()
                jsonObj.put("storage_id", id_storageManage.text.toString().toInt())
                jsonObj.put(storageName, name_storageManage.text.toString())
                jsonObj.put(storageCount, count_storageManage.text.toString().toInt())
                jsonObj.put(storageEntrance, entranceDate_storageManage.text.toString())
                jsonObj.put(storageExpire, expireDate_storageManage.text.toString())

                val client = OkHttpClient()
                val jsonData= jsonObj.toString()
                val request = Request.Builder()
                        .url(site)
                        .post(RequestBody.create(MediaType.parse("application/json"), jsonData))
                        .build()

                val response = client.newCall(request).execute()
                val result = response.body()!!.string()
            }
        }
    }

    // Web Server로 입력 데이터를 전송하는 기능: Update
    private fun updateItemDB(storageName:String,
                             storageCount:String, storageEntrance:String, storageExpire:String) {
        CoroutineScope(IO).launch {
            launch {
                delay(250L)
                val site = "$localHostIp/input"
                val jsonObj = JSONObject()
                jsonObj.put("storage_id", id_storageManage.text.toString().toInt())
                jsonObj.put(storageName, name_storageManage.text.toString())
                jsonObj.put(storageCount, count_storageManage.text.toString().toInt())
                jsonObj.put(storageEntrance, entranceDate_storageManage.text.toString())
                jsonObj.put(storageExpire, expireDate_storageManage.text.toString())

                val client = OkHttpClient()
                val jsonData= jsonObj.toString()
                val request = Request.Builder()
                        .url(site)
                        .put(RequestBody.create(MediaType.parse("application/json"), jsonData))
                        .build()

                val response = client.newCall(request).execute()
                val result = response.body()!!.string()
            }
        }
    }

    // 화면을 초기화하는 기능
    private fun textInit() {
        id_storageManage.setText("")
        name_storageManage.setText("")
        count_storageManage.setText("")
        entranceDate_storageManage.text = "화면을 눌러보세요"
        expireDate_storageManage.text = "화면을 눌러보세요"
    }

    private fun listInit() {
        CoroutineScope(Main).launch {
            launch {
                delay(250L)
                idList = ArrayList()
                coffeeList = ArrayList()
                dairyList = ArrayList()
                dessertList = ArrayList()
                fruitList = ArrayList()
                macaronList = ArrayList()
            }
            launch {
                val deleteItemList = ArrayList<StorageItemDelete>()
                val adapter = DeleteItemAdapter(activity as MainActivity, R.layout.recycler_item_delete_storage_manager, deleteItemList)
                val manager = LinearLayoutManager(context)
                manager.orientation = LinearLayoutManager.VERTICAL

                removeList_storageManage.adapter = adapter
                removeList_storageManage.layoutManager = manager
            }
            launch {
                val selectItemList = ArrayList<StorageItem>()
                val adapter = StorageSelectAdapter(activity as MainActivity, R.layout.recycler_item_storage_manager, selectItemList)
                val manager = LinearLayoutManager(context)
                manager.orientation = LinearLayoutManager.VERTICAL

                itemList_storageManage.adapter = adapter
                itemList_storageManage.layoutManager = manager
            }
        }
    }
}