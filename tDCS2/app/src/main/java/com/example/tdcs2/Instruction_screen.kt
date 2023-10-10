package com.example.tdcs2

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

class Instruction_screen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instruction_screen)

        var hardware_setup_text = findViewById<TextView>(R.id.hardware_setup_text)
        var OK = findViewById<Button>(R.id.OK)


        OK.setOnClickListener {
            val intent = Intent(baseContext, Home_screen::class.java)
            startActivity(intent)
            //sendData("6")
        }

    }

}
