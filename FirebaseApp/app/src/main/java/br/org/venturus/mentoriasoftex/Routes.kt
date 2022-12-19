package br.org.venturus.mentoriasoftex

sealed class Routes (val route: String) {
    object SendClicks: Routes("SendClicks")
    object HomeScreen: Routes("HomeScreen")
    object LoginScreen: Routes("LoginScreen")
    object CreateAccountScreen: Routes("CreateAccountScreen")
    object WelcomeScreen: Routes("WelcomeScreen")
}