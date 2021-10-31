package com.example.cafe_admin.ui.cctv

import android.content.Context
import android.util.Log
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class CctvMqtt(val context: Context, val uri:String) {
    var mqttClient:MqttAndroidClient = MqttAndroidClient(context, uri, MqttClient.generateClientId())

    fun connect(topics: Array<String>?=null) {
        val mqttConnectOptions = MqttConnectOptions()
        mqttClient.connect(mqttConnectOptions, null, object:IMqttActionListener{
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.d("cctv", "connect success...")
                topics?.map { subscribeTopic(it) }
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.d("cctv", "connect fail...")
            }
        })
    }

    private fun subscribeTopic(topic:String, qos:Int = 0) {
        mqttClient.subscribe(topic, qos, null, object: IMqttActionListener{
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.d("cctv", "subscribe success...")
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.d("cctv", "subscribe fail...")
            }
        })
    }

    fun setCallbak(callback: (topic: String, message: MqttMessage) -> Unit) {
        mqttClient.setCallback(object: MqttCallback{
            override fun connectionLost(cause: Throwable?) {
                Log.d("cctv", "connection lost...")
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                Log.d("cctv", "message arrived...")
                callback(topic!!, message!!)
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                Log.d("cctv", "delivery complete...")
            }
        })
    }
}