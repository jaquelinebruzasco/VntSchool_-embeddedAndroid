package br.org.venturus.mentoriasoftex

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import br.org.venturus.mentoriaservice.aidl.IMyService
import br.org.venturus.mentoriasoftex.screen.MainScreen
import br.org.venturus.mentoriasoftex.ui.theme.FirebaseAppTheme

class MainActivity : ComponentActivity(), ServiceConnection {

    var isConnected = false
    lateinit var myService: IMyService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = viewModel()

            FirebaseAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }

    override fun onServiceConnected(componentName: ComponentName?, binder: IBinder?) {
        Log.d("Mentoria", "Service connected")
        myService = IMyService.Stub.asInterface(binder)
        isConnected = true

        showToast()
    }

    override fun onServiceDisconnected(componentName: ComponentName?) {
        Log.d("Mentoria", "Service disconnected unexpectedly !!!")
        isConnected = false
    }

    fun connectToService() {
        if (!isConnected) {
            checkConnection()
        } else {
            showToast()
        }
    }

    private fun checkConnection() {
        val intent = Intent().apply {
            component = ComponentName(
                "br.org.venturus.mentoriaservice",
                "br.org.venturus.mentoriaservice.MyService"
            )
        }
        bindService(intent, this, BIND_AUTO_CREATE)
    }

    fun rebootDevice() {
        if (!isConnected) {
            checkConnection()
        } else {
            myService.apply(IMyService::rebootDevice)
        }
    }

    fun showToast() {
        myService.apply(IMyService::showToast)

    }

    override fun onStop() {
        super.onStop()
        if (isConnected) unbindService(this)
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FirebaseAppTheme {
        MainScreen()
    }
}