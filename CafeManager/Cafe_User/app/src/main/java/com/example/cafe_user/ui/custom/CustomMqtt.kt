package com.example.cafe_user.ui.custom

import android.content.Context
import android.util.Log
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class CustomMqtt(val context: Context, val uri:String) {
    var mqttClient:MqttAndroidClient = MqttAndroidClient(context,uri, MqttClient.generateClientId())

    fun connect(topics : Array<String>?=null) {
        val mqttconnect_options = MqttConnectOptions()
        // connect 호출 - broker에 연결
        mqttClient.connect(mqttconnect_options, null, object:IMqttActionListener{
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.d("mymqtt","connect success..")
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.d("mymqtt","connect fail..")
            }

        })
    }
    fun publish(topic:String, payload:String, qos:Int = 0) {
        if (mqttClient.isConnected() === false) {
            mqttClient.connect()
        }
        val message = MqttMessage()
        message.payload = payload.toByteArray()
        message.qos = qos
        mqttClient.publish(topic,message,null,object :IMqttActionListener{
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.d("mymqtt","publish success..")
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.d("mymqtt", "publish fail..")
            }
        })
    }
}