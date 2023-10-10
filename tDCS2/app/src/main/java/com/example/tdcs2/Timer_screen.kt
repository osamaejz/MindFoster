package com.example.tdcs2


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_timer_screen.*

import java.util.*

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.widget.Toast
import java.io.IOException
import java.util.concurrent.TimeUnit

class Timer_screen : AppCompatActivity() {
    //val tv = findViewById<TextView>(R.id.textViewcountdown)
    var time_left = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer_screen)

        ConnectBT().execute()

        val extras = intent.extras ?: return
        val stim_stren = extras.getInt("Extrastrength")
        val stim_time = extras.getInt("Extraspan")

        //mTimeLeftInMillis = Start_time_in_millis

        var strength_array = arrayOf<String>("None","0.5 mAmps","1.0 mAmps","1.5 mAmps","2.0 mAmps")
        var span_array = arrayOf<Long>(0,600000,1200000,1800000)// 10,20,30 minutes in milli seconds
        var span_array2 = arrayOf<String>("None","10 minutes","20 minutes","30 minutes")// same as above but in another form


        val output_text = findViewById<TextView>(R.id.textView7)
        output_text.setText("Delivering a stimulus of " + strength_array[stim_stren] + " for " + span_array2[stim_time] + ".")



        val stop = findViewById<Button>(R.id.stop)
        val start = findViewById<Button>(R.id.start)

        //val tv = findViewById<TextView>(R.id.textViewcountdown)
        val timer = MyCounter(span_array[stim_time], 1000)

        start.setOnClickListener (object : View.OnClickListener {
            override fun onClick(v: View?) {
                //sendData((stim_stren.toString()))
                sendData((stim_stren.toString()))
                timer.start()
                //hardwareActivity().ConnectBT()
                //hardwareActivity().sendData("2")
                //updaterFunction()
            }
        })
        stop.setOnClickListener (object : View.OnClickListener {
            override fun onClick(v: View?) {
                sendData("6")
                //hardwareActivity().sendData("6")
                timer.cancel()
                //updaterFunction()
            }

        })

    }

    //fun sendData(message: String) {
      //  Instruction_screen().ConnectBT().execute()
       // Log.d(Instruction_screen.TAG, "Sending data:$message")
       // if (Instruction_screen().btSocket != null) {
        //    try {
         //       Instruction_screen().btSocket!!.outputStream.write(message.toByteArray())
        //    } catch (e: IOException) {
           //     e.printStackTrace()
         //   }
       // }
   // }

    inner class MyCounter(millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {

        override fun onFinish() {
            val tv = findViewById<TextView>(R.id.textViewcountdown)
            println("Timer Completed.")
            tv.text = "Timer Completed."
            val intent = Intent(baseContext, finishActivity::class.java)
            startActivity(intent)
        }

        override fun onTick(millisUntilFinished: Long) {
            val tv = findViewById<TextView>(R.id.textViewcountdown)
            tv.textSize = 50f

            //tv.text = (millisUntilFinished / 1000).toString() + ""

            val minutes = (millisUntilFinished / 1000).toInt() / 60
            val seconds = (millisUntilFinished / 1000).toInt() % 60
            val timeLeftFormatted: String =
                java.lang.String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
            tv.text = timeLeftFormatted

            println("Timer  : " + millisUntilFinished / 1000)
        }
    }


    lateinit var myBluetooth: BluetoothAdapter
    var btSocket: BluetoothSocket? = null
    private var isBtConnected = false

    public inner class ConnectBT : AsyncTask<Void?, Void?, Void?>() // UI thread
    {
        private var ConnectSuccess = true //if it's here, it's almost connected
        protected override fun onPreExecute() { // progress = ProgressDialog.show(.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        protected override fun doInBackground(vararg devices: Void?): Void? //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (btSocket == null || !isBtConnected) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter() //get the mobile bluetooth device
                    val device: BluetoothDevice = myBluetooth.getRemoteDevice(address) //connects to the device's address and checks if it's available
                    btSocket = device.createInsecureRfcommSocketToServiceRecord(myUUID) //create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                    btSocket!!.connect() //start connection
                }
            } catch (e: IOException) {
                ConnectSuccess = false //if the try failed, you can check the exception here
                e.printStackTrace()
            }
            return null
        }

        protected override fun onPostExecute(result: Void?) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result)
            if (!ConnectSuccess) {

                Toast.makeText(this@Timer_screen, "Bluetooth Connection Failure", Toast.LENGTH_SHORT).show()

                //msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
//finish();
            } else {
                Toast.makeText(this@Timer_screen, "Bluetooth Connected", Toast.LENGTH_SHORT).show()
                //msg("Connected.");
                isBtConnected = true
                sendData("5")
                //updaterFunction()
            }
            //progress.dismiss();
        }
    }

    fun sendData(message: String) {
        Log.d(TAG, "Sending data:$message")
        if (btSocket != null) {
            try {
                btSocket!!.outputStream.write(message.toByteArray())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun receiveData(): String {
        try {
            TimeUnit.MILLISECONDS.sleep(600)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        val buffer = ByteArray(1024)
        var bytes = 0
        val string: String
        if (btSocket != null) {
            try {
                bytes = btSocket!!.getInputStream().read(buffer)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        string = String(buffer, 0, bytes)
        Log.d(TAG, "receiving data:$string")
        return string
    }

    companion object {
        //SPP UUID. Look for it
        val myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        //private static String address = "98:D3:31:FB:61:26";
        private const val address = "FC:F5:C4:29:7F:46"
        const val TAG = "fromPhone"
    }


}
