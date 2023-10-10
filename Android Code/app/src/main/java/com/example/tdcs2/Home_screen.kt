package com.example.tdcs2


import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi

class Home_screen : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_home)

        var disclaimer_text = findViewById<TextView>(R.id.disclaimer_content)
        var hardware_setup = findViewById<Button>(R.id.hardware_setup)
        var stimulation_setup = findViewById<Button>(R.id.stimulation_setup)

        stimulation_setup.setOnClickListener {
            val intent = Intent(baseContext, Select_stim::class.java)
            startActivity(intent)
        }
        hardware_setup.setOnClickListener {
            val intent = Intent(baseContext, Instruction_screen::class.java)
            startActivity(intent)
        }


    }
}
