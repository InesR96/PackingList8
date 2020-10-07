package com.example.packinglist

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import com.example.packinglist.DB.PackingList
import kotlinx.android.synthetic.main.activity_packing2.*

class PackingActivity2 : AppCompatActivity() {
    lateinit var dbHandler: DBHandler
    var listId:Long=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_packing2)
        setSupportActionBar(dashboard_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)




        dbHandler= DBHandler(this)
        rv_dashboard.layoutManager=LinearLayoutManager(this)
        fab_dashboard.setOnClickListener{
            val dialog=AlertDialog.Builder(this)
            val view= layoutInflater.inflate(R.layout.dialog_packing, null)
            val packingListName=view.findViewById<EditText>(R.id.ev_packing)
            dialog.setView(view)
            dialog.setPositiveButton("Add") { _: DialogInterface, _: Int ->
                if(packingListName.text.isNotEmpty())
                {val packingList= PackingList()
                    packingList.itemName=packingListName.text.toString();
                    dbHandler.addPackingList(packingList)
                    refreshList()
                }
            }
            dialog.setNegativeButton("Cancel"){ _: DialogInterface, _: Int -> }

            dialog.show()
        }



    }
    fun updateList(packingList: PackingList){
        val dialog=AlertDialog.Builder(this)
        dialog.setTitle("Update List")
        val view= layoutInflater.inflate(R.layout.dialog_packing, null)
        val packingListName=view.findViewById<EditText>(R.id.ev_packing)
        packingListName.setText(packingList.itemName)
        dialog.setView(view)
        dialog.setPositiveButton("Update") { _: DialogInterface, _: Int ->
            if(packingListName.text.isNotEmpty())
            {
                packingList.itemName=packingListName.text.toString();
                dbHandler.updateList(packingList)
                refreshList()
            }
        }
        dialog.setNegativeButton("Cancel"){ _: DialogInterface, _: Int -> }

        dialog.show()
    }

    override fun onResume() {
        refreshList()
        super.onResume()
    }
    private fun refreshList()
    {
        rv_dashboard.adapter=PackingAdapter(this, dbHandler.getPackingLists())
    }
    class PackingAdapter(val activity2: PackingActivity2, val list: MutableList<PackingList>) : RecyclerView.Adapter<PackingAdapter.ViewHolder>(){


        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
            return  ViewHolder(LayoutInflater.from(activity2).inflate(R.layout.rv_child_packing, p0, false))
        }

        override fun getItemCount(): Int {

            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
            holder.packingListName.text=list[p1].itemName
            holder.packingListName.isChecked=list[p1].isCompleted
            holder.packingListName.setOnClickListener {
                list[p1].isCompleted=!list[p1].isCompleted
                activity2.dbHandler.updatePackingList(list[p1])
            }
            holder.menu.setOnClickListener {
                val popup=PopupMenu(activity2, holder.menu)
                popup.inflate(R.menu.list_child)
                popup.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menu_edit->{
                            activity2.updateList(list[p1])


                        }
                        R.id.menu_delete->{
                            activity2.dbHandler.deletePackingList(list[p1].id)
                            activity2.refreshList()


                        }

                    }

                    true
                }
                popup.show()
            }



        }

        class ViewHolder(v : View): RecyclerView.ViewHolder(v){
            val packingListName:CheckBox=v.findViewById(R.id.cb_item)
            val menu: ImageView=v.findViewById(R.id.iv_menu)

        }
    }
}