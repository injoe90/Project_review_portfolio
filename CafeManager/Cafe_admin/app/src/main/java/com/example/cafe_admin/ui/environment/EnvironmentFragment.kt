package com.example.cafe_admin.ui.environment

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.*
import android.os.Looper.myLooper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cafe_admin.MainActivity
import com.example.cafe_admin.R
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.environment_control.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.json.JSONObject
import java.lang.NullPointerException
import kotlin.concurrent.thread

class EnvironmentFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.environment_control,container, false)
    }

    // mqtt 클라이언트
    lateinit var mqttClient:EnvironMqtt

    // 스레드 핸들러 변수
    lateinit var airCont:Handler
    lateinit var gasAlarm:Handler
    lateinit var dustAlarm:Handler

    var thread1:Thread? = null
    var thread2:Thread? = null

    // mqtt로 받은 센서 측정변수
    var hum:Float = 0.0f
    var temp:Float = 0.0f
    var gas:Float = 0.0f
    var dust:Float = 0.0f

    // 이벤트 상태 변수
    var air_state:Int = 0

    // 스레드 관련 변수
    var airHum:Float = 0.0f
    var airTemp:Float = 0.0f
    var gasCrit:Float = 0.0f
    var dustCrit:Float = 0.0f

    private val localHostIp = "172.30.1.17"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // mqtt Subscriber 수행 : broker ip
        mqttClient = EnvironMqtt(activity as MainActivity, "tcp://$localHostIp:1883")
        try {
            mqttClient.setCallback(::onReceived)
            mqttClient.connect(arrayOf("adminCustom"))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // 에어컨 ImageButton 이벤트 처리
        air_control_layout.setBackgroundColor(Color.rgb(226,226, 226))
        airCont = object  : Handler(myLooper()!!) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (air_state == 1) {
                    air_hum.text = airHum.toString()
                    air_temp.text = airTemp.toString()
                } else {
                    air_hum.text = ""
                    air_temp.text = ""
                }
            }
        }
        air_control.setOnClickListener {
            if (air_state == 0) {
                air_state = 1
                airInform()
                air_control_layout.setBackgroundColor(Color.rgb(255,189, 222))
            } else {
                air_state = 0
                airInform()
                air_control_layout.setBackgroundColor(Color.rgb(226,226, 226))
            }
        }
        // 가스 알람 이벤트 처리
        gas_alarm_img.setImageResource(R.drawable.gas_safe)
        gasAlarm()
        gasAlarm = object : Handler(myLooper()!!) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                try {
                    if (gasCrit >= 500) {
                        gas_alarm_img.setImageResource(R.drawable.gas_danger)
                    } else if (gasCrit < 500) {
                        gas_alarm_img.setImageResource(R.drawable.gas_safe)
                    }
                } catch (e:NullPointerException) {
                }
            }
        }
        // 미세먼지 알람 이벤트 처리
        dust_alarm_img.setImageResource(R.drawable.air_safe)
        dustAlarm()
        dustAlarm = object : Handler(myLooper()!!) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                try {
                    if (dustCrit >= 10 && dustCrit < 15) {
                        dust_alarm_img.setImageResource(R.drawable.air_weak)
                    } else if (dustCrit >= 15) {
                        dust_alarm_img.setImageResource(R.drawable.air_dirty)
                    } else if (dustCrit < 10) {
                        dust_alarm_img.setImageResource(R.drawable.air_safe)
                    }
                } catch (e: NullPointerException) {
                }
            }
        }

        contact_gasManagement.setOnClickListener {
            val uri = Uri.parse("https://cyber.kgs.or.kr/scu.Scu01List.ex.do?vocCategory=01&vocChCd=03")
            val cartIntent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(cartIntent)
        }

        // #### 온습도 실시간 그래프 처리 ####
        // realtime1 styling
        val rtxAxis1 = realtime_line1.xAxis
        rtxAxis1.position = XAxis.XAxisPosition.BOTTOM
        rtxAxis1.setAvoidFirstLastClipping(true)
        rtxAxis1.setDrawGridLines(false)
        rtxAxis1.textColor = R.color.teal_200

        val rtylAxis1 = realtime_line1.axisLeft
        rtylAxis1.setDrawGridLines(false)
        rtylAxis1.textColor = R.color.black
        rtylAxis1.axisMinimum = 0f
        rtylAxis1.axisMaximum = 55f

        val rtyrAxis1 = realtime_line1.axisRight
        rtyrAxis1.isEnabled = false

        // reatime2 styling
        val rtxAxis2 = realtime_line2.xAxis
        rtxAxis2.position = XAxis.XAxisPosition.BOTTOM
        rtxAxis2.setAvoidFirstLastClipping(true)
        rtxAxis2.setDrawGridLines(false)
        rtxAxis2.textColor = R.color.teal_200

        val rtylAxis2 = realtime_line2.axisLeft
        rtylAxis2.setDrawGridLines(false)
        rtylAxis2.textColor = R.color.black
        rtylAxis2.axisMinimum = 0f
        rtylAxis2.axisMaximum = 55f

        val rtyrAxis2 = realtime_line2.axisRight
        rtyrAxis2.isEnabled = false

        // realtime1 data
        realtime_line1.data = LineData()

        // realtime2 data
        realtime_line2.data = LineData()

        // realtime1 legend
        val rtLegend1 = realtime_line1.legend
        rtLegend1.textColor = Color.rgb(255, 210, 189)
        rtLegend1.textSize = 15f
        rtLegend1.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        rtLegend1.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        rtLegend1.orientation = Legend.LegendOrientation.HORIZONTAL
        rtLegend1.setDrawInside(false)

        // realtime2 legend
        val rtLegend2 = realtime_line2.legend
        rtLegend2.textColor = Color.rgb(189, 247, 255)
        rtLegend2.textSize = 15f
        rtLegend2.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        rtLegend2.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        rtLegend2.orientation = Legend.LegendOrientation.HORIZONTAL
        rtLegend2.setDrawInside(false)

        // 실시간 그래프 작성
        feedMultiple1()
        feedMultiple2()
    }
    private fun onReceived(topic:String, message: MqttMessage) {
        val msg = String(message.payload)
        val jsObj = JSONObject(msg)
        val floatHum = jsObj.getDouble("hum").toFloat()
        val floatTemp = jsObj.getDouble("temp").toFloat()
        val floatGas = jsObj.getDouble("gas").toFloat()
        val floatDust = jsObj.getDouble("dust").toFloat()

        Log.d("mytest",floatHum.toString())
        Log.d("mytest",floatTemp.toString())
        Log.d("mytest",floatGas.toString())
        Log.d("mytest",floatDust.toString())

        hum = floatHum + Math.random().toFloat()
        temp = floatTemp + Math.random().toFloat()
        gas = floatGas
        dust = floatDust
    }
    // 에어컨 온습도 실시간 확인 스레드 설정
    private fun airInform() {
        thread {
            while (true) {
                airHum = hum
                airTemp = temp
                SystemClock.sleep(100)
                airCont.sendMessage(airCont.obtainMessage())
            }
        }
    }

    // Gas 누출 경보 스레드 설정
    private fun gasAlarm() {
        CoroutineScope(IO).launch {
            while (true) {
                gasCrit = gas
                SystemClock.sleep(100)
                gasAlarm.sendMessage(gasAlarm.obtainMessage())
            }
        }
//        thread {
//            while (true) {
//                gasCrit = gas
//                SystemClock.sleep(100)
//                gasAlarm.sendMessage(gasAlarm.obtainMessage())
//            }
//        }
    }
    // 미세먼지 주의 스레드 설정
    private fun dustAlarm() {
        thread {
            while (true) {
                dustCrit = dust
                SystemClock.sleep(100)
                dustAlarm.sendMessage(dustAlarm.obtainMessage())
            }
        }
    }

    // 실시간 그래프 설정: 스레드 설정
    private fun feedMultiple1() {
        if (thread1 != null) {
            thread1!!.interrupt()
        }
        val runnable1 = Runnable {
            addEntry1()
        }
        thread1 = Thread(Runnable {
            while (true) {
                activity?.runOnUiThread(runnable1)
                try {
                    Thread.sleep(25)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        })
        thread1!!.start()
    }

    private fun feedMultiple2() {
        if (thread2 != null) {
            thread2!!.interrupt()
        }
        val runnable2 = Runnable {
            addEntry2()
        }
        thread2 = Thread(Runnable {
            while (true) {
                activity?.runOnUiThread(runnable2)
                try {
                    Thread.sleep(25)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        })
        thread2!!.start()
    }

    // 실시간 그래프 데이터 입력
    private fun addEntry1() {
        try {
            val data = realtime_line1.data
            if (data != null) {
                var set = data.getDataSetByIndex(0)
                if (set == null) {
                    set = createSet1()
                    data.addDataSet(set)
                }
                data.addEntry(
                        Entry(
                                set.entryCount.toFloat(),
                                hum
                        ), 0
                )
                data.notifyDataChanged()
                realtime_line1.notifyDataSetChanged()
                realtime_line1.setVisibleXRangeMaximum(120f)
                realtime_line1.moveViewToX(data.entryCount.toFloat())
            }
        } catch (e: NullPointerException) {
        }
    }
    private fun addEntry2() {
        try {
            val data = realtime_line2.data
            if (data != null) {
                var set = data.getDataSetByIndex(0)
                if (set == null) {
                    set = createSet2()
                    data.addDataSet(set)
                }
                data.addEntry(
                        Entry(
                                set.entryCount.toFloat(),
                                temp
                        ), 0
                )
                data.notifyDataChanged()
                realtime_line2.notifyDataSetChanged()
                realtime_line2.setVisibleXRangeMaximum(120f)
                realtime_line2.moveViewToX(data.entryCount.toFloat())
            }
        } catch (e:NullPointerException) {
        }
    }

    // 실시간 데이터 표현 형식 지정
    private fun createSet1(): LineDataSet {
        val set = LineDataSet(null, "Humidity Data")
        set.axisDependency = YAxis.AxisDependency.LEFT
        set.color = ColorTemplate.getHoloBlue()
        set.setCircleColor(Color.WHITE)
        set.lineWidth = 2f
        set.circleRadius = 4f
        set.fillAlpha = 65
        set.fillColor = ColorTemplate.getHoloBlue()
        set.highLightColor = Color.rgb(244, 117, 117)
        set.valueTextColor = Color.WHITE
        set.valueTextSize = 9f
        set.setDrawValues(false)
        return set
    }
    private fun createSet2(): LineDataSet {
        val set = LineDataSet(null, "temperature Data")
        set.axisDependency = YAxis.AxisDependency.LEFT
        set.color = ColorTemplate.getHoloBlue()
        set.setCircleColor(Color.WHITE)
        set.lineWidth = 2f
        set.circleRadius = 4f
        set.fillAlpha = 65
        set.fillColor = ColorTemplate.getHoloBlue()
        set.highLightColor = Color.rgb(244, 117, 117)
        set.valueTextColor = Color.WHITE
        set.valueTextSize = 9f
        set.setDrawValues(false)
        return set
    }
}