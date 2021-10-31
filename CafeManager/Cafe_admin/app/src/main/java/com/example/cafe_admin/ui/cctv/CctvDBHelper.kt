package com.example.cafe_admin.ui.cctv

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DB_Version = 2
class CctvDBHelper(context: Context) : SQLiteOpenHelper(context, "cctv.db", null, DB_Version) {
    override fun onCreate(db: SQLiteDatabase?) {
        val sql = """
            create table cctvCheck(
                idx integer primary key autoincrement,
                date text
            )
        """.trimIndent()
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}