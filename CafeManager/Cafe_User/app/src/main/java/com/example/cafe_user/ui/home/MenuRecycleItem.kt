package com.example.cafe_user.ui.home

import android.os.Parcel
import android.os.Parcelable

class MenuRecycleItem():Parcelable {
    var photo:Int=0
    var menu_name:String?=null

    constructor(parcel: Parcel) : this() {
        photo = parcel.readInt()
        menu_name = parcel.readString()
    }

    constructor(photo:Int, menu_name:String?):this(){
        this.photo = photo
        this.menu_name = menu_name
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(photo)
        parcel.writeString(menu_name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MenuRecycleItem> {
        override fun createFromParcel(parcel: Parcel): MenuRecycleItem {
            return MenuRecycleItem(parcel)
        }

        override fun newArray(size: Int): Array<MenuRecycleItem?> {
            return arrayOfNulls(size)
        }
    }

}