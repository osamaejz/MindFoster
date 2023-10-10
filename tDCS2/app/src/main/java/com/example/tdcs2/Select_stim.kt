package com.example.tdcs2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import kotlin.random.Random


class Select_stim : AppCompatActivity() {

    public var Extrastrength = 0
    public var Extraspan = 0

    public var stimul_strength = 0
    public var stimul_span = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_stim)



        var stimulation_spinner = findViewById<Spinner>(R.id.spinner1)
        var stimulation_array_adapter = ArrayAdapter<String>(this@Select_stim, android.R.layout.simple_list_item_1,
            getResources().getStringArray(R.array.stimulation))

        stimulation_array_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        stimulation_spinner.adapter = stimulation_array_adapter

        var Timer_spinner = findViewById<Spinner>(R.id.spinner2)
        var Timer_array_adapter = ArrayAdapter<String>(this@Select_stim, android.R.layout.simple_list_item_1,
            getResources().getStringArray(R.array.timer))

        Timer_array_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        Timer_spinner.adapter = Timer_array_adapter

        stimulation_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                stimul_strength = Integer.parseInt(position.toString())
            }
        }

        Timer_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                stimul_span = Integer.parseInt(position.toString())
            }


        }
        val nextbutton = findViewById<Button>(R.id.nextbutton)
        nextbutton.setOnClickListener {
            if (stimul_span > 0 && stimul_strength > 0){
                val intent = Intent(this, Timer_screen::class.java)
                intent.putExtra("Extrastrength",stimul_strength)
                intent.putExtra("Extraspan",stimul_span)
                startActivity(intent)

            }
        }
    }
}
