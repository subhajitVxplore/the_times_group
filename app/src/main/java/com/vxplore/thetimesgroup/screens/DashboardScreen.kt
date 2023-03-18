package com.vxplore.thetimesgroup.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vxplore.thetimesgroup.R
import com.vxplore.thetimesgroup.screens.dashboard_content.DashboardMainScreen
import com.vxplore.thetimesgroup.screens.dashboard_content.Screen2Component
import com.vxplore.thetimesgroup.screens.dashboard_content.Screen3Component
import com.vxplore.thetimesgroup.screens.dashboard_content.Screen4Component
import com.vxplore.thetimesgroup.ui.theme.GreyLight
import com.vxplore.thetimesgroup.viewModels.DashboardViewModel
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen() {
    val currentScreen = remember { mutableStateOf(DrawerAppScreen.DashboardMain) }
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val ctx = LocalContext.current
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        drawerContent = {
            DrawerContentComponent(
                currentScreen = currentScreen,
                closeDrawer = { coroutineScope.launch { scaffoldState.drawerState.close() } })
        },
        topBar = {
            TopAppBar(
               // modifier = Modifier.clip(RoundedCornerShape(bottomStart = 8.dp,bottomEnd = 8.dp)),
                backgroundColor = GreyLight
            ){
                Box(modifier = Modifier.fillMaxSize().fillMaxWidth()) {
                IconButton(
                    //modifier = Modifier.align(CenterStart),
                    onClick = {
                        coroutineScope.launch { scaffoldState.drawerState.open() }
                       // Toast.makeText(ctx, "Menu", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Icon(Icons.Filled.Menu, "")
                }

                    Image(
                        painter = painterResource(id = R.drawable.the_times_group_logo),
                        contentDescription = "countryImage",
                        modifier = Modifier.align(Alignment.Center).padding(8.dp)
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
                BodyContentComponent(
                    currentScreen = currentScreen.value,
                    openDrawer = { coroutineScope.launch { scaffoldState.drawerState.open() } },
                    viewModel = hiltViewModel()
                )
            }
        },

    )

}


@Composable
fun DrawerContentComponent(currentScreen: MutableState<DrawerAppScreen>, closeDrawer: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        for (index in DrawerAppScreen.values().indices) {
            val screen = getScreenBasedOnIndex(index)
            Column(Modifier.clickable(onClick = {
                currentScreen.value = screen
                closeDrawer()
            }), content = {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = if (currentScreen.value == screen) {
                        MaterialTheme.colors.secondary
                    } else {
                        MaterialTheme.colors.surface
                    }
                ) {
                    Text(text = screen.name, modifier = Modifier.padding(16.dp))
                }
            })
        }
    }
}

enum class DrawerAppScreen { DashboardMain, Screen2, Screen3 ,Screen4}
fun getScreenBasedOnIndex(index: Int) = when (index) {
    0 -> DrawerAppScreen.DashboardMain
    1 -> DrawerAppScreen.Screen2
    2 -> DrawerAppScreen.Screen3
    3 -> DrawerAppScreen.Screen4
    else -> DrawerAppScreen.DashboardMain
}
@Composable
fun BodyContentComponent(
    currentScreen: DrawerAppScreen,
    openDrawer: () -> Unit,
    viewModel: DashboardViewModel
) {
    when (currentScreen) {
        DrawerAppScreen.DashboardMain -> DashboardMainScreen(openDrawer,viewModel)
        DrawerAppScreen.Screen2 -> Screen2Component(openDrawer)
        DrawerAppScreen.Screen3 -> Screen3Component(openDrawer)
        DrawerAppScreen.Screen4 -> Screen4Component(openDrawer)
    }
}

