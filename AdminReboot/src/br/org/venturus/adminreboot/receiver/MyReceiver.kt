package br.org.venturus.adminreboot.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import android.os.PowerManager

class MyReceiver : BroadcastReceiver() {

	val ACTION_TOAST = "br.org.venturus.adminreboot.TOAST"
	val ACTION_REBOOT = "br.org.venturus.adminreboot.REBOOT"

	private val handler = Handler(Looper.getMainLooper())

	override fun onReceive(context: Context, intent: Intent) {

		Log.d("Mentoria", "onReceive()")
		if (ACTION_TOAST.equals(intent.getAction())) {
			Log.d("Mentoria", "TOAST broadcast was received")
			handler.post {
				Toast.makeText(context, "Hello there from MyReceiver", 1).show()
			}
		} else if(ACTION_REBOOT.equals(intent.getAction())) {
			Log.d("Mentoria", "REBOOT broadcast was received")
			handler.post {
				Toast.makeText(context, "REBOOT received on MyReceiver", 1).show()
				val powerManager = context.getSystemService(PowerManager::class.java)
				powerManager?.reboot("MentoriaSoftex")
			}
		}
	}
}
