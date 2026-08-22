package com.example.soundinch10.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.soundinch10.ui.screens.LoginScreen
import com.example.soundinch10.ui.screens.MainScreen

@Composable
fun SoundInNavGraph(
    navController: NavHostController,
    ){
    NavHost(
        navController = navController,
        startDestination = SoundInRoutes.LOGIN
    ){
        composable(SoundInRoutes.LOGIN){
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(SoundInRoutes.REGISTER)
                },
                onLoginSuccess = {
                    navController.navigate(SoundInRoutes.MAIN){
                        popUpTo (SoundInRoutes.LOGIN) {inclusive = true}
                    }
                }
            )
        } // End composable LOGIN


        composable(SoundInRoutes.MAIN){
            MainScreen()
        }
    }
}