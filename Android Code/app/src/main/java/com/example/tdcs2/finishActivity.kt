package com.example.tdcs2


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class finishActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        var another_session = findViewById<Button>(R.id.another_session)
        var exit = findViewById<Button>(R.id.exit)
        var output_text = findViewById<TextView>(R.id.output_text)

        another_session.setOnClickListener {
            val intent = Intent(baseContext, Select_stim::class.java)
            startActivity(intent)
        }
        exit.setOnClickListener {
            moveTaskToBack(true)
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(1)
        }
    }
}
