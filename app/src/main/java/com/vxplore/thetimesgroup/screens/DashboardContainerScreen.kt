@file:Suppress("DEPRECATION")

package com.vxplore.thetimesgroup.screens

import android.app.Activity
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.vxplore.core.domain.model.AppVersion
import com.vxplore.core.helpers.AppStore
import com.vxplore.thetimesgroup.R
import com.vxplore.thetimesgroup.screens.dashboard_content.DashboardMainScreen
import com.vxplore.thetimesgroup.screens.dashboard_content.Screen2Component
import com.vxplore.thetimesgroup.screens.dashboard_content.Screen3Component
import com.vxplore.thetimesgroup.screens.dashboard_content.Screen4Component
import com.vxplore.thetimesgroup.ui.theme.GreyLight
import com.vxplore.thetimesgroup.viewModels.DashboardViewModel
import kotlinx.coroutines.launch

@Composable
fun DashboardContainerScreen(viewModel: DashboardViewModel = hiltViewModel()) {
    val currentScreen = remember { mutableStateOf(DrawerAppScreen.Dashboard) }
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val ctx = LocalContext.current
    val activity= LocalContext.current as Activity


    //val onBack = { Toast.makeText(ctx, "onBack", Toast.LENGTH_SHORT).show() }
    //BackPressHandler(onBackPressed = onBack)
    BackPressHandler(onBackPressed = {
        viewModel.onBackDialog()
    })



    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        drawerContent = {
            DrawerContentComponent(
                currentScreen = currentScreen,
                closeDrawer = { coroutineScope.launch { scaffoldState.drawerState.close() } }
            )},
        topBar = {
            TopAppBar(
               // modifier = Modifier.clip(RoundedCornerShape(bottomStart = 8.dp,bottomEnd = 8.dp)),
                backgroundColor = GreyLight
            ){
                Box(modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()) {
                IconButton(
                    //modifier = Modifier.align(CenterStart),
                    onClick = {
                        coroutineScope.launch { scaffoldState.drawerState.open() }
                       // Toast.makeText(ctx, "drawer contents", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Icon(Icons.Filled.Menu, "")
                }

                    Image(
                        painter = painterResource(id = R.drawable.the_times_group_logo),
                        contentDescription = "countryImage",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(8.dp)
                        //.padding(70.dp, 0.dp, 0.dp, 0.dp)
                    )

                    IconButton(
                        onClick = {
                           // viewModel.onBackDialog()
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

    viewModel.dashboardBack.value?.apply {
        if (currentState()) {
            AlertDialog(
                properties= DialogProperties(dismissOnClickOutside = false),
                shape = RoundedCornerShape(10.dp),
                onDismissRequest = {
                    onDismiss?.invoke(null)
//                    if ((currentData?.data as AppVersion).isSkipable) {
////                        setState(MyDialog.Companion.State.DISABLE)
////                    }
                },
                buttons = {
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                      //  if ((currentData?.data as AppVersion).isSkipable) {
                            currentData?.negative?.let {
                                OutlinedButton(
                                    modifier = Modifier.padding(10.dp),
                                    onClick = {
                                        onDismiss?.invoke(null)
                                    },
                                    shape = RoundedCornerShape(30),
                                    border = BorderStroke(1.dp, color = Color.Gray,
                                    )
                                ) {
                                    Text(text = it, color = Color.Gray) //updateLater  btn text
                                }
                            }
                       // }

                        currentData?.positive?.let {
                            OutlinedButton(
                                onClick = {
                                   // onConfirm?.invoke(currentData?.data)
                                    onConfirm?.invoke(activity.finish())
                                          },
                                shape = RoundedCornerShape(30),
                                border = BorderStroke(1.dp, color = Color.Blue),
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Text(text = it, color = Color.Blue) //updateNow  btn text
                            }
                        }
                    }
                },
                modifier = Modifier,
                title = {
                    currentData?.title?.let {
                        Text(text = it, ) //version update title
                    }

                },
                text = {
                    currentData?.message?.let {
                        Text(text = it, ) //version message from Api
                    }
                }
            )
        }
    }





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

enum class DrawerAppScreen { Dashboard, Screen2, Screen3 ,Screen4,LogOut}
fun getScreenBasedOnIndex(index: Int) = when (index) {
    0 -> DrawerAppScreen.Dashboard
    1 -> DrawerAppScreen.Screen2
    2 -> DrawerAppScreen.Screen3
    3 -> DrawerAppScreen.Screen4
    4 -> DrawerAppScreen.LogOut
    else -> DrawerAppScreen.Dashboard
}
@Composable
fun BodyContentComponent(
    currentScreen: DrawerAppScreen,
    openDrawer: () -> Unit,
    viewModel: DashboardViewModel,
) {

    when (currentScreen) {
        DrawerAppScreen.Dashboard -> DashboardMainScreen(openDrawer,viewModel)
        DrawerAppScreen.Screen2 -> Screen2Component(openDrawer)
        DrawerAppScreen.Screen3 -> Screen3Component(openDrawer)
        DrawerAppScreen.Screen4 -> Screen4Component(openDrawer)
        DrawerAppScreen.LogOut -> {
            viewModel.onLogoutFromDashboard()
        }
    }
}



//////////////////////////////////////////////////////////////////////
@Composable
fun BackPressHandler(
    backPressedDispatcher: OnBackPressedDispatcher? =
        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    onBackPressed: () -> Unit
) {
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)

    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }

    DisposableEffect(key1 = backPressedDispatcher) {
        backPressedDispatcher?.addCallback(backCallback)

        onDispose {
            backCallback.remove()
        }
    }
}

