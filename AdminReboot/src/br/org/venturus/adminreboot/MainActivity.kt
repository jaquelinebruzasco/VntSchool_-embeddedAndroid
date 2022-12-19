package br.org.venturus.adminreboot

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PowerManager
import android.telephony.TelephonyManager
import android.app.UiModeManager
import android.provider.MediaStore
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.org.venturus.adminreboot.R

class MainActivity : AppCompatActivity() {

    val REQUEST_CODE = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cameraButton = findViewById<Button>(R.id.camera_button)
        cameraButton.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, REQUEST_CODE)
            Toast.makeText(
                this,
                "Camera requested",
                Toast.LENGTH_SHORT
            ).show()
        }

        val rebootButton = findViewById<Button>(R.id.reboot_button)
        rebootButton.setOnClickListener {
            Toast.makeText(
                this,
                "Device will reboot, run for your life...",
                Toast.LENGTH_LONG
            ).show()
            rebootDevice(this)
        }

        val imeiText = this.findViewById<TextView>(R.id.tv_imei_number)
        imeiText.text = "O Número do IMEI do telefone é: ${getImeiNumber(this)}"

        val changeThemeButton = this.findViewById<Button>(R.id.theme_changer_button)
        changeThemeButton.setOnClickListener {
            Toast.makeText(this, "Changing theme from device!!", 1).show()
            changeTheme(this)
        }
    }

    fun rebootDevice(context: Context) {
        val powerManager = context.getSystemService(PowerManager::class.java)
        powerManager?.reboot("MentoriaSoftex")
    }

    private fun getImeiNumber(context: Context): String {
        val telephonyManager = context.getSystemService(TelephonyManager::class.java)
        return telephonyManager.imei
    }

    private fun changeTheme(context: Context) {
        val uiModeManager = context.getSystemService(UiModeManager::class.java)
        if (uiModeManager.nightMode == UiModeManager.MODE_NIGHT_NO) {
            uiModeManager.setNightModeActivated(true)
        } else {
            uiModeManager.setNightModeActivated(false)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            Toast.makeText(
                this,
                "Camera closed",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    
}