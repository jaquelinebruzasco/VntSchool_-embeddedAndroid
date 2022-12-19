package br.org.venturus.mentoriasoftex.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.org.venturus.mentoriasoftex.MainActivity
import br.org.venturus.mentoriasoftex.R
import br.org.venturus.mentoriasoftex.Routes

@Composable
fun HomeScreen(navController: NavHostController) {

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource(id = R.drawable.firebase2),
            contentDescription = "Firebase image",
            modifier = Modifier.clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = { navController.navigate(Routes.SendClicks.route) },
        ) {
            Text(text = "INICIAR")
        }
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = {navController.navigate(Routes.LoginScreen.route) },
        ) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = { (context as MainActivity).connectToService() },
        ) {
            Text(text = "MOSTRAR TOAST")
        }
    }
}