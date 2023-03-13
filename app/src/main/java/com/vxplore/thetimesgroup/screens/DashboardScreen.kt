package com.vxplore.thetimesgroup.screens

import android.widget.ImageButton
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vxplore.thetimesgroup.R
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen() {
    //val currentScreen = remember { mutableStateOf(DrawerAppScreen.Screen1) }
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val ctx = LocalContext.current
    Scaffold(
        //scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        drawerContent = {
//            DrawerContentComponent(
//                currentScreen = currentScreen,
//                closeDrawer = { coroutineScope.launch { scaffoldState.drawerState.close() } })
        },
        topBar = {
            TopAppBar(
                modifier = Modifier.clip(
                    RoundedCornerShape(
                        bottomStart = 8.dp,
                        bottomEnd = 8.dp
                    )
                )
            ) {
                Box(modifier = Modifier.fillMaxSize().fillMaxWidth()) {
                IconButton(
                    onClick = {
                        coroutineScope.launch { scaffoldState.drawerState.open() }
                        Toast.makeText(ctx, "Menu", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Icon(Icons.Filled.Menu, "")
                }

                    Image(
                        painter = painterResource(id = R.drawable.the_times_group_logo),
                        contentDescription = "countryImage",
                        modifier = Modifier.align(Alignment.Center)
                        //.padding(70.dp, 0.dp, 0.dp, 0.dp)
                    )

                    IconButton(
                        onClick = {

                            Toast.makeText(ctx, "Notifications", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(Icons.Filled.Notifications, "")
                    }
                }


            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
//                BodyContentComponent(
//                    currentScreen = currentScreen.value,
//                    openDrawer = { coroutineScope.launch { scaffoldState.drawerState.open() } },
//                    viewModel = hiltViewModel()
//                )
            }
        },

    )

}