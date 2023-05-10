@file:Suppress("DEPRECATION")

package com.vxplore.thetimesgroup.screens

import android.app.Activity
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.vxplore.thetimesgroup.R
import com.vxplore.thetimesgroup.mainController.MainActivity
import com.vxplore.thetimesgroup.ui.theme.DonutGreenLight
import com.vxplore.thetimesgroup.ui.theme.GreyLight
import com.vxplore.thetimesgroup.viewModels.BaseViewModel
import com.vxplore.thetimesgroup.viewModels.BillPreviewScreenViewModel

@Composable
fun VendorBillPreviewScreen(
    viewModel: BillPreviewScreenViewModel = hiltViewModel(),
    baseViewModel: BaseViewModel
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                // .wrapContentHeight()
                .height(50.dp)
                .background(GreyLight)
                .padding(5.dp)
        ) {
            val activity = LocalContext.current as Activity
            Image(painter = painterResource(id = R.drawable.ic_baseline_keyboard_backspace_24),
                contentDescription = "back button",
                modifier = Modifier
                    .clickable {
                        activity.onBackPressed()
                    }
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 10.dp)
            )

            Text(
                text = "Bill Preview",
                color = Color.DarkGray,
                fontSize = 17.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically),
                contentAlignment = Alignment.TopEnd
            ) {
                Button(
                    onClick = {
                        //viewModel.onBillingToAddVendor()
                    },
                    shape = RoundedCornerShape(25.dp),
                    modifier = Modifier.wrapContentSize(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = DonutGreenLight)
                ) {
                    Text(
                        text = "Share",
                        color = Color.White,
                        fontSize = 13.sp,
                        modifier = Modifier.align(Alignment.CenterVertically),
                        fontStyle = FontStyle.Normal
                    )
                }
            }
        }


        //----------------------------------------------------------------------------------

        val activity = LocalContext.current as MainActivity

        Column(modifier = Modifier.fillMaxSize()) {

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(baseViewModel.bitmapImg) { index,imgs->
                    Image(
                        bitmap = imgs.asImageBitmap(),
                        // painter =rememberAsyncImagePainter(viewModel.bitmapImg),
                        contentDescription = null,
                        modifier = Modifier
                            .border(
                                width = 2.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(12.dp)
                            )
                    )

                }
            }

//        LazyColumn(modifier = Modifier.fillMaxSize()) {
//            items(images.size) {

//            }
//        }
        }
//        Image(
//            // bitmap = viewModel.bitmapImg!!.asImageBitmap(),
//            painter = coil.compose.rememberAsyncImagePainter(viewModel.bitmapImg),
//            //  painter = painterResource(id = R.drawable.ic_launcher_background),
//            contentDescription = "back button",
//            modifier = Modifier.padding(10.dp).fillMaxSize()
//        )


    }
}




