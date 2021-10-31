package com.example.cafe_user.ui.cart

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DB_VERSION = 2
class CartDBHelper(context: Context) : SQLiteOpenHelper(context, "cart.db", null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = """
            create table cart(
                cart_id Integer primary key autoincrement,
                table_id Integer,
                menu_id Integer,
                menu_name text,
                menu_count Integer,
                temp_option Integer,
                size_option text,
                menu_price Integer,
                cart_state Integer
            )
        """.trimIndent()
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}