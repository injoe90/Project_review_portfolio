package com.example.cafe_admin.ui.cctv

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class CctvCheckDB(context: Context) {
    var contentValObj: ContentValues? = null
    var cctvDB:SQLiteDatabase? = null
    var context:Context? = context

    init {
        val cctvHelper = CctvDBHelper(context)
        cctvDB = cctvHelper.writableDatabase
    }

    fun insert(resource: CctvResource) {
        contentValObj = ContentValues()
        contentValObj!!.put("data", resource.currentDate)

        cctvDB?.insert("cctvCheck", null, contentValObj)
    }
}