package com.example.cafe_admin.ui.cctv

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.GridLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafe_admin.MainActivity
import com.example.cafe_admin.R
import kotlinx.android.synthetic.main.cctv.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.text.SimpleDateFormat
import java.util.*


class Cctv : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.cctv, container, false)

    }
    lateinit var cctvClient: CctvMqtt
    private val cctvDataList = ArrayList<CctvResource>()
    private var cctvDB:CctvCheckDB? = null
    private val localHostIp = "172.30.1.17"
    private val flaskServerIp = "172.30.1.21"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val current = Calendar.getInstance().time
        val Format = SimpleDateFormat("yyyy.MM.dd  ", Locale.KOREA).format(current)

        // mqtt Subscriber 기능
        cctvClient = CctvMqtt(activity as MainActivity, "tcp://$localHostIp:1883")
        try {
            cctvClient.setCallbak(::onReceived)
            cctvClient.connect(arrayOf("admin/cctv"))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // DB CRUD 메서드를 인스턴스하는 기능
        cctvDB = context?.let { CctvCheckDB(it) }

        date_text.text = Format
        cctv_view.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }
        cctv_view.loadUrl("http://$flaskServerIp:5000/stream?src=-1")
    }

    // MQTT에서 받은 데이터를 변환하고 DB에 넣는 기능 + 리사이클러 뷰로 나타내는 기능
   private fun onReceived(topic: String, message: MqttMessage) {
        val cctvRawData = message.payload

        Toast.makeText(context, cctvRawData.toString(), Toast.LENGTH_SHORT).show()

//        val currentDate = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA).format(Calendar.getInstance().time)
//
//        insertCctvDB(currentDate)
//
//        CoroutineScope(Main).launch {
//            cctvDataList.add(CctvResource(currentDate))
//        }
//
//        CoroutineScope(Main).launch {
//            val cctvAdapter = context?.let { CctvRecyclerAdapter(it, R.layout.item_cctv, cctvDataList) }
//            val cctvGridLayout = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
//            cctv_storage_imageView.adapter = cctvAdapter
//            cctv_storage_imageView.layoutManager = cctvGridLayout
//        }
    }

    private fun insertCctvDB(currentData:String) {
        CoroutineScope(Dispatchers.Default).launch {
            val cctvDBItem = CctvResource(currentData)
            cctvDB?.insert(cctvDBItem)
        }
    }
}
