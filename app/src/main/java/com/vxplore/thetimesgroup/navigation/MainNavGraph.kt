package com.vxplore.thetimesgroup.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.core.utils.NavigationIntent
import com.vxplore.core.common.AppRoutes
import com.vxplore.core.common.Destination
import com.vxplore.thetimesgroup.screens.*
import kotlinx.coroutines.channels.Channel

@Composable
fun MainNavGraph(
    navHostController: NavHostController,
    navigationChannel: Channel<NavigationIntent>,
    paddingValues: PaddingValues,
) {
    navHostController.NavEffects(navigationChannel)

    AppNavHost(
        navController = navHostController,
        startDestination = Destination.Splash,
        modifier = Modifier.padding(paddingValues),
        enterTransition = AppScreenTransitions.ScreenEnterTransition,
        popEnterTransition = AppScreenTransitions.ScreenPopEnterTransition,
        exitTransition = AppScreenTransitions.ScreenExitTransition,
        popExitTransition = AppScreenTransitions.ScreenPopExitTransition,
    ) {
        composable(destination = Destination.Splash) {
           // SplashScreen(navHostController)
            SplashScreen()
        }
        composable(destination = Destination.MobileNo) {
            // MobileNoScreen(onContinueClick = {navHostController.navigate(AppRoutes.OTP)})
            MobileNoScreen(navHostController)//
        }
        composable(destination=Destination.Otp){
            OtpScreen(navHostController)
        }
        composable(destination=Destination.Dashboard){
            DashboardScreen()
        }


    }
}