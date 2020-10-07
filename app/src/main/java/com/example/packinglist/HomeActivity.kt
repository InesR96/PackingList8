package com.example.packinglist


import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*
import java.text.SimpleDateFormat
import java.util.*


class HomeActivity : AppCompatActivity() {
    val formatDate= SimpleDateFormat("dd.MMM.YYYY", Locale.ENGLISH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val hide = supportActionBar?.hide()

        btn_date.setOnClickListener(View.OnClickListener {
            val getDate= Calendar.getInstance()
            val datepicer=DatePickerDialog(this,android.R.style.Theme_Holo_Light_Dialog_MinWidth, DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                val selectDate= Calendar.getInstance()
                selectDate.set(Calendar.YEAR, i)
                selectDate.set(Calendar.MONTH, i2)
                selectDate.set(Calendar.DAY_OF_MONTH, i3)
                val date: String=formatDate.format(selectDate.time)
                Toast.makeText(this,"Date : "+date, Toast.LENGTH_LONG).show()
                tv_date.text=date
            },
            getDate.get(Calendar.YEAR), getDate.get(Calendar.MONTH), getDate.get(Calendar.DAY_OF_MONTH))
            datepicer.show()
        })


        val imageButton=findViewById<ImageButton>(R.id.imageButton)

        imageButton.setOnClickListener{
            val intent=Intent(this, PackingActivity2::class.java)
            startActivity(intent)
        }




    }
}