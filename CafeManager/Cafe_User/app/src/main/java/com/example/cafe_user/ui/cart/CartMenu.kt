package com.example.cafe_user.ui.cart

class CartMenu {
    var cart_id:Int = 0
    var table_id:Int = 0
    var menu_id:Int = 0
    var menu_name:String = ""
    var menu_count:Int = 0
    var temp_option:Int = 0
    var size_option:String = ""
    var menu_price:Int = 0
    var menu_state:Int = 0

    constructor(table_id:Int, menu_id:Int, menu_name:String, menu_count: Int,
                temp_option:Int, size_option:String, menu_price:Int, menu_state:Int) {
        this.table_id = table_id
        this.menu_id = menu_id
        this.menu_name = menu_name
        this.menu_count = menu_count
        this.temp_option = temp_option
        this.size_option = size_option
        this.menu_price = menu_price
        this.menu_state = menu_state
    }

    constructor(cart_id:Int, table_id:Int, menu_id:Int, menu_name:String, menu_count: Int,
                temp_option:Int, size_option:String, menu_price:Int, menu_state:Int) {
        this.cart_id = cart_id
        this.table_id = table_id
        this.menu_id = menu_id
        this.menu_name = menu_name
        this.menu_count = menu_count
        this.temp_option = temp_option
        this.size_option = size_option
        this.menu_price = menu_price
        this.menu_state = menu_state
    }
}