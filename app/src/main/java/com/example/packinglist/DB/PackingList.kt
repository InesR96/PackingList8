package com.example.packinglist.DB

class PackingList {
    var id: Long=-1
    var itemName=""
    var isCompleted=false
    var item : MutableList<PackingList> = ArrayList()


}