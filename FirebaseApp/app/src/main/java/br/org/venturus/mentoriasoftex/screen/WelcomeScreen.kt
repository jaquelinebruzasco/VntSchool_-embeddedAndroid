package br.org.venturus.mentoriasoftex.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.org.venturus.mentoriasoftex.MainActivity
import br.org.venturus.mentoriasoftex.Routes

@Composable
fun WelcomeScreen(navController: NavController) {

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome",
            fontSize = 40.sp,
            modifier = Modifier.padding(32.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = { (context as MainActivity).rebootDevice() },
        ) {
            Text(text = "REBOOT")
        }
    }
}