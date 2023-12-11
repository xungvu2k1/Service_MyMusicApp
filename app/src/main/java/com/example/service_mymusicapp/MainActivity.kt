package com.example.service_mymusicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    private var edtDataIntent : EditText? = null
    private var btnStartService : Button? = null
    private var btnStopService : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtDataIntent = findViewById(R.id.edt_data_intent)
        btnStartService = findViewById(R.id.btn_start_service)
        btnStopService = findViewById(R.id.btn_stop_service)

        btnStartService?.setOnClickListener{
            clickStartService()
        }

        btnStopService?.setOnClickListener{
            clickStopService()
        }
    }

    private fun clickStartService() {

        var song : Song = Song("Thi Mau", "Hoa Minzy", R.drawable.thimau, R.raw.thimau)
        var intent = Intent(this,MyService::class.java)
        // truyền đối tượng Song qua bundle
        var bundle : Bundle = Bundle()
        bundle.putSerializable("object_song", song)
        intent.putExtras(bundle)
        // sau khi put data vào intent thì bên nhận là MyService phải có cơ chế nhận data trong intent => chạy sang file MyService=> hàm onStartCommand
        startService(intent)
    }

    private fun clickStopService() {
        var intent = Intent(this,MyService::class.java)
        stopService(intent )
    }
}