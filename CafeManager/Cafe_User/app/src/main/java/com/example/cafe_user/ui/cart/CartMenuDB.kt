package com.example.cafe_user.ui.cart

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class CartMenuDB {
    var contentValObj: ContentValues? = null
    var cartMenu:SQLiteDatabase? = null
    var context:Context? = null
    constructor(context:Context){
        this.context = context
        val cartDbHelper = CartDBHelper(context)
        cartMenu = cartDbHelper.writableDatabase
    }

    // Cart DB Function
    fun insert(menu: CartMenu){
        contentValObj = ContentValues()
        contentValObj!!.put("table_id", menu.table_id)
        contentValObj!!.put("menu_id", menu.menu_id)
        contentValObj!!.put("menu_name", menu.menu_name)
        contentValObj!!.put("menu_count", menu.menu_count)
        contentValObj!!.put("temp_option", menu.temp_option)
        contentValObj!!.put("size_option", menu.size_option)
        contentValObj!!.put("menu_price", menu.menu_price)
        contentValObj!!.put("cart_state", menu.menu_state)

        cartMenu?.insert("cart", null, contentValObj)
    }

    @SuppressLint("Recycle")
    fun selectCart():ArrayList<CartMenu> {
        val dataList: ArrayList<CartMenu> = ArrayList()
        val cursor = cartMenu?.query("cart", null, null, null, null, null, null)

        while (cursor!!.moveToNext()) {
            if (cursor.getInt(8) == 0) {
                val cartDisplayItem = CartMenu(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2),
                        cursor.getString(3), cursor.getInt(4), cursor.getInt(5), cursor.getString(6),
                        cursor.getInt(7), cursor.getInt(8))
                dataList.add(cartDisplayItem)
            }
        }
        return dataList
    }

    fun cartUpdate(id:Int, count:Int) {
        contentValObj = ContentValues()
        contentValObj!!.put("menu_count", count)

        val where:String = "cart_id like ?"
        cartMenu?.update("cart", contentValObj, where, arrayOf("%${id}%"))
    }

    fun cartStateUpdate(id:Int, state:Int) {
        contentValObj = ContentValues()
        contentValObj!!.put("cart_state", state)

        val where = "cart_id like ?"
        cartMenu?.update("cart", contentValObj, where, arrayOf("%${id}%"))
    }

    fun cartDelete(id:Int) {
        cartMenu?.delete("cart", "cart_id=?", arrayOf("%${id}%"))
    }

    // OState DB Function
    @SuppressLint("Recycle")
    fun selectOstate(): ArrayList<CartMenu> {
        val dataList: ArrayList<CartMenu> = ArrayList()
        val cursor = cartMenu?.query("cart", null, null, null, null, null, null)

        while (cursor!!.moveToNext()) {
            if (cursor.getInt(8) == 1) {
                val cartDisplayItem = CartMenu(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2),
                    cursor.getString(3), cursor.getInt(4), cursor.getInt(5), cursor.getString(6),
                    cursor.getInt(7), cursor.getInt(8))
                dataList.add(cartDisplayItem)
            }
        }
        return dataList
    }
}