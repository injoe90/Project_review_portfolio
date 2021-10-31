package com.example.cafe_admin.ui.environment

import android.content.Context
import android.util.Log
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class EnvironMqtt(val context: Context, val uri:String) {
    var mqttClient:MqttAndroidClient = MqttAndroidClient(context, uri, MqttClient.generateClientId())

    fun connect(topics:Array<String>?=null) {
        val mqttconnect_options = MqttConnectOptions()
        mqttClient.connect(mqttconnect_options, null, object: IMqttActionListener{
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.d("mymqtt","Connect Success..")
                topics?.map { subscribeTopic(it) }
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.d("mymqtt","Connect Fail..")
            }
        })
    }
    fun publish(topic:String, payload:String, qos:Int=0) {
        if (mqttClient.isConnected === false) {
            mqttClient.connect()
        }
        val message = MqttMessage()
        message.payload = payload.toByteArray()
        message.qos = qos
        mqttClient.publish(topic, message, null, object:IMqttActionListener{
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.d("mqtt","Publish Success..")
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.d("mqtt","Publish Fail..")
            }

        })
    }
    private fun subscribeTopic(topic:String, qos:Int = 0) {
        mqttClient.subscribe(topic,qos,null,object:IMqttActionListener{
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.d("mqtt","Subscribe Success..")
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.d("mqtt","Subscribe Fail..")
            }
        })
    }
    fun setCallback(callback:(topic:String, message:MqttMessage) -> Unit) {
        mqttClient.setCallback(object: MqttCallback{
            override fun messageArrived(topic: String?, message: MqttMessage?) {
                Log.d("mqtt","Message arrived..")
                callback(topic!!, message!!)
            }

            override fun connectionLost(cause: Throwable?) {
                Log.d("mqtt","Connection Lost..")
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                Log.d("mqtt","Delivery Complete..")
            }

        })
    }
}