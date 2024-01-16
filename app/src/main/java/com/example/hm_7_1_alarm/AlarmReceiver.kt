package com.example.hm_7_1_alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.provider.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AlarmReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        val alarmSound: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val defaultAlarmSound = Settings.System.DEFAULT_ALARM_ALERT_URI
        val soundUri = alarmSound ?: defaultAlarmSound

        GlobalScope.launch(Dispatchers.Default) {
            playAlarm(soundUri, context)
        }
    }

    private suspend fun playAlarm(soundUri: Uri, context: Context) {
        val mediaPlayer = MediaPlayer().apply {
            setDataSource(context, soundUri)
            prepare()
            start()
        }

        while (mediaPlayer.isPlaying) {
            kotlinx.coroutines.delay(1000)
        }
        mediaPlayer.release()
    }

}