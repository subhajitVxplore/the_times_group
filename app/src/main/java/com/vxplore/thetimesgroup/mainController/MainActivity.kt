package com.vxplore.thetimesgroup.mainController

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.vxplore.thetimesgroup.R

import com.vxplore.thetimesgroup.navigation.MainNavGraph
import com.vxplore.thetimesgroup.ui.theme.TheTimesGroupTheme
import com.vxplore.thetimesgroup.viewModels.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val baseViewModel by viewModels<BaseViewModel>()

        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        setContent {
            TheTimesGroupTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    backgroundColor = MaterialTheme.colors.background,
                ) { paddingValues ->
                    MainNavGraph(
                        navHostController = rememberNavController(),
                        navigationChannel = baseViewModel.appNavigator.navigationChannel,
                        paddingValues = paddingValues,
                    )
                }
            }
        }
    }
}
