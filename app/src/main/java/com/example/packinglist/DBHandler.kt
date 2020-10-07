package com.example.packinglist

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.packinglist.DB.PackingList

class DBHandler(val context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val createPackingListTable="CREATE TABLE PackingList (" +
                "$COL_ID integer PRIMARY KEY AUTOINCREMENT," +
                "$COL_ITEM_NAME itemName varchar," +
                "$COL_IS_COMPLETED isCompleted integer" +
                ");"


        db.execSQL(createPackingListTable)


    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

        }
    fun addPackingList(packingList: PackingList): Boolean{
        val db = writableDatabase
        val cv =ContentValues()
        cv.put(COL_ITEM_NAME, packingList.itemName)

        if(packingList.isCompleted)
        {
            cv.put(COL_IS_COMPLETED,true)

        }
        else
        {
            cv.put(COL_IS_COMPLETED,false)
        }
        val result: Long=db.insert(TABLE_PACKING_LIST, null,cv)
        return result!=(-1).toLong()


    }
    fun updateList(packingList: PackingList){
        val db = writableDatabase
        val cv =ContentValues()
        cv.put(COL_ITEM_NAME, packingList.itemName)

        db.update(TABLE_PACKING_LIST,cv, "$COL_ID=?", arrayOf(packingList.id.toString()))




    }
    fun deletePackingList(packingListId: Long){
       val db:SQLiteDatabase=writableDatabase
        db.delete(TABLE_PACKING_LIST, "$COL_ID=?", arrayOf(packingListId.toString()))


    }
    fun updatePackingList(packingList: PackingList){
        val db = writableDatabase
        val cv =ContentValues()
        cv.put(COL_ITEM_NAME, packingList.itemName)

        if(packingList.isCompleted)
        {
            cv.put(COL_IS_COMPLETED,true)

        }
        else
        {
            cv.put(COL_IS_COMPLETED,false)
        }
        db.update(TABLE_PACKING_LIST, cv,"$COL_ID=?", arrayOf(packingList.id.toString()))



    }
    fun getPackingLists(): MutableList<PackingList>{
        val result: MutableList<PackingList> = ArrayList()
        val db:SQLiteDatabase=readableDatabase
        val queryResult= db.rawQuery("SELECT * from $TABLE_PACKING_LIST", null)
        if(queryResult.moveToFirst())
        {
            do {
                val packingList= PackingList()
                packingList.id=queryResult.getLong(queryResult.getColumnIndex(COL_ID))
                packingList.itemName=queryResult.getString((queryResult.getColumnIndex(
                    COL_ITEM_NAME)))
                packingList.isCompleted=queryResult.getInt(queryResult.getColumnIndex(COL_IS_COMPLETED))==1
                result.add(packingList)

            }while ((queryResult.moveToNext()))
        }
        queryResult.close()
        return  result
    }

}