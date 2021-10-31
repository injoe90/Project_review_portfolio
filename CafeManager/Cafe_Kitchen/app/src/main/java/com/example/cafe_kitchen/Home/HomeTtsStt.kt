package com.example.cafe_kitchen.Home

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.util.*

class HomeTtsStt(context:Context) {
    private val listener = (object: RecognitionListener{
        override fun onReadyForSpeech(params: Bundle?) {
        }

        override fun onBeginningOfSpeech() {
        }

        override fun onRmsChanged(rmsdB: Float) {
        }

        override fun onBufferReceived(buffer: ByteArray?) {
        }

        override fun onEndOfSpeech() {
        }

        override fun onError(error: Int) {
            var message = ""
            when (error) {
                SpeechRecognizer.ERROR_AUDIO -> message = "오디오에서 오류가 발생했습니다..."
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> message = "권한 설정에서 오류가 발생했습니다..."
                SpeechRecognizer.ERROR_CLIENT -> message = "클라이언트에서 오류가 발생했습니다..."
                SpeechRecognizer.ERROR_NETWORK -> message = "네트워크에서 오류가 발생했습니다..."
                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> message = "다른 작업을 처리하고 있습니다..."
                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> message = "시간 초과가 발생했습니다..."
            }
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        override fun onResults(results: Bundle?) {
            var voiceMsg = ""
            val voiceData:ArrayList<String> =
                results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION) as ArrayList<String>
            for (i in voiceData.indices) {
                voiceMsg = voiceData[i]
            }
            // 실행할 이벤트 처리
        }

        override fun onPartialResults(partialResults: Bundle?) {
        }

        override fun onEvent(eventType: Int, params: Bundle?) {
        }
    })

    fun init(ttsObj: TextToSpeech, context:Context):TextToSpeech {
        return TextToSpeech(context, TextToSpeech.OnInitListener {
            if (it != TextToSpeech.ERROR) {
                ttsObj.language = Locale.KOREAN
            }
        })
    }

    fun sttRun(context: Context) {
        val sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        sttIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName)
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")

        val recognizer = SpeechRecognizer.createSpeechRecognizer(context)
        recognizer.setRecognitionListener(listener)
        recognizer.startListening(sttIntent)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun ttsRun(ttsObj:TextToSpeech, msg: String, utteranceId: String) {
        ttsObj.setPitch(1f)
        ttsObj.setSpeechRate(1f)
        ttsObj.speak(msg, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun ttsPitchUp(ttsObj:TextToSpeech, msg: String, pitchNum:Float,
                   speedNum:Float, utteranceId: String) {
        ttsObj.setPitch(pitchNum * 2f)
        ttsObj.setSpeechRate(speedNum)
        ttsObj.speak(msg, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun ttsPitchDown(ttsObj:TextToSpeech, msg: String, pitchNum:Float,
                     speedNum:Float, utteranceId: String) {
        ttsObj.setPitch(pitchNum * 0.5f)
        ttsObj.setSpeechRate(speedNum)
        ttsObj.speak(msg, TextToSpeech.QUEUE_FLUSH, null, utteranceId)

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun ttsSpeedUp(ttsObj:TextToSpeech, msg: String, pitchNum:Float,
                   speedNum:Float, utteranceId: String) {
        ttsObj.setPitch(pitchNum)
        ttsObj.setSpeechRate(speedNum * 2f)
        ttsObj.speak(msg, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun ttsSpeedDown(ttsObj:TextToSpeech, msg: String, pitchNum:Float,
                     speedNum:Float, utteranceId: String) {
        ttsObj.setPitch(pitchNum)
        ttsObj.setSpeechRate(speedNum * 0.5f)
        ttsObj.speak(msg, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
    }
}