package com.example.hm_7_1_alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hm_7_1_alarm.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var alarmManager: AlarmManager ?= null
    private lateinit var alarmIntent: PendingIntent
    private val  calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(this, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        }

        binding.btnSelectTime.setOnClickListener {
            calendar.set(Calendar.HOUR_OF_DAY, binding.timePick.hour)
            calendar.set(Calendar.MINUTE, binding.timePick.minute)
            if (alarmManager?.canScheduleExactAlarms() == true) {
                alarmManager?.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    alarmIntent
                )
            }
            Toast.makeText(this,
                "Время будильника установлено на ${binding.timePick.hour}:${binding.timePick.minute}",
                Toast.LENGTH_SHORT).show()
        }

    }


}
