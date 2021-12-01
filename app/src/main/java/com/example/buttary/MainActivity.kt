package com.example.buttary

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.buttary.databinding.ActivityMainBinding

var binding: ActivityMainBinding? = null


class MainActivity : AppCompatActivity() {
    private var mReceiver: BroadcastReceiver? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        mReceiver = PhoneBatteryState()
    }


    override fun onStart() {
        registerReceiver(mReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        super.onStart()
    }

    override fun onStop() {
        unregisterReceiver(mReceiver)
        super.onStop()
    }


}

    class PhoneBatteryState : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            showBatteryLevel(intent)
            showBatteryChargingState(intent)
        }

        @SuppressLint("SetTextI18n")
        private fun showBatteryChargingState(intent: Intent?) {
            if (intent?.getIntExtra(
                    BatteryManager.EXTRA_STATUS,
                    -1
                ) == BatteryManager.BATTERY_STATUS_CHARGING
            ) {
                binding?.textViewChargingState?.text = "CHARGING"
            } else {
                binding?.textViewChargingState?.text = "NOT CHARGING"

            }
        }

        @SuppressLint("SetTextI18n")
        private fun showBatteryLevel(intent: Intent?) {
            if (intent?.action == "android.intent.action.BATTERY_CHANGED") {
                when (intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)) {
                    in 100 downTo 75 -> binding?.textViewBattaryLevel?.text = "Excellent"
                    in 74 downTo 30 -> binding?.textViewBattaryLevel?.text = "Good"
                    in 29 downTo 5 -> binding?.textViewBattaryLevel?.text = "Low"

                }

            }

        }


    }
