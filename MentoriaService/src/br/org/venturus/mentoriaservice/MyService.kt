package br.org.venturus.mentoriaservice

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast

import br.org.venturus.mentoriaservice.aidl.IMyService

class MyService : Service() {

	val ACTION_TOAST = "br.org.venturus.adminreboot.TOAST"
	val ACTION_REBOOT = "br.org.venturus.adminreboot.REBOOT"

	override fun onBind(intent: Intent?) = mMyService

	private val handler = Handler(Looper.getMainLooper())

	private val mMyService = object : IMyService.Stub() {
		override fun showToast() {
			handler.post {
				Log.d("MentoriaService", "showToast() was called")
				Toast.makeText(this@MyService, "Hello there from MyService", 1).show()

				val intent = Intent().apply {
					component = ComponentName (
						"br.org.venturus.adminreboot",
						"br.org.venturus.adminreboot.receiver.MyReceiver"
					)
					action = ACTION_TOAST
				}
				sendBroadcast(intent)
			}
		}
		override fun rebootDevice() {
			handler.post {
				Log.d("MentoriaService", "rebootDevice() was called")
				Toast.makeText(this@MyService, "Send command REBOOT to AdminReboot", 1).show()
				
				val intent = Intent().apply {
					component = ComponentName (
						"br.org.venturus.adminreboot",
						"br.org.venturus.adminreboot.receiver.MyReceiver"
					)
					action = ACTION_REBOOT
				}
				sendBroadcast(intent)
			}
		}
	}
}

