@file:Suppress("DEPRECATION")

package com.vxplore.thetimesgroup.screens

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vxplore.thetimesgroup.R
import com.vxplore.thetimesgroup.viewModels.MobileNoScreenViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MobileNoScreen(
     viewModel: MobileNoScreenViewModel = hiltViewModel()
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val mContext = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
        // verticalArrangement = Arrangement.Center,
        //horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val activity = LocalContext.current as Activity
        Image(painter = painterResource(id = R.drawable.ic_baseline_keyboard_backspace_24),
            contentDescription = "countryImage",
            modifier = Modifier.clickable{
               // activity.onBackPressed()
                activity.finishAffinity()
            }
        )

        Text(
            text = "Register or SignIn with your Mobile number",
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.h5,
        )

        Surface(
            border = BorderStroke(1.dp, Color.Gray),
            shape = RoundedCornerShape(8.dp),
            elevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(),

            //  .padding(5.dp)
//            modifier = Modifier.fillMaxWidth().height(50.dp)

        ) {
            Row(
                // modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(5.dp)
                modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)

            ) {
                Image(
                    painter = painterResource(id = R.drawable.indian_flag),
                    contentDescription = "Flag",
                    modifier = Modifier
                        .width(40.dp)
                        .wrapContentHeight()
                        .align(Alignment.CenterVertically)
                )

                Text(
                    text = "+91",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .align(Alignment.CenterVertically)
                        .padding(5.dp, 0.dp, 0.dp, 0.dp)
                )

                val maxLength=9
                TextField(
                    value = viewModel.MobileNoText.value ?: "",
                    singleLine = true,
                    onValueChange = {
                        if (it.length <= 10){ viewModel.MobileNoText.value = it}
                        else if (it.length >maxLength) { keyboardController?.hide()}
                        else {
                            Toast.makeText(mContext, "Can not be more than 10", Toast.LENGTH_SHORT).show()
                            //keyboardController?.hide()
                    }

                    },
                    textStyle = TextStyle.Default.copy(fontSize = 23.sp),
                    placeholder = {
                        Text(
                            text = "your mobile number",
                            style = MaterialTheme.typography.h6,
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically)
                        .background(Color.White) ,

                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done, keyboardType = KeyboardType.Number
                    ),

                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,//hide the indicator
                        unfocusedIndicatorColor = Color.White
                    )

                )
            }

        }



        Row(
            // modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(5.dp)
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(0.dp, 7.dp, 0.dp, 0.dp)

        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_round_check_circle_24),
                contentDescription = "check",
                modifier = Modifier
                    .width(35.dp)
                    .height(35.dp)
                    .align(Alignment.CenterVertically)
            )

            Text(
                text = "you will receive important updates and informations from us over the whatsapp",
                modifier = Modifier.align(Alignment.CenterVertically),
                color = Color.Gray
            )

        }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.BottomCenter)
            ) {
                val context = LocalContext.current
                Button(

                    onClick = {
                        //Toast.makeText(context, "continue", Toast.LENGTH_SHORT).show()
                        //onContinueClick()
                        if (viewModel.MobileNoText.value != null && viewModel.MobileNoText.value!!.length < 10){
                            Toast.makeText(context, "10 digits required", Toast.LENGTH_SHORT).show()
                        } else {
                           // navController.navigate(AppRoutes.OTP+ "/${MobileNoText.text}",)
                            viewModel.onMobToOtp("${viewModel.MobileNoText.value}")
                        }
                      //  navController.navigate(Routes.Settings.route + "/$counter")
                    },
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()

                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
                ) {
                    Text(
                        text = "Continue",
                        color = Color.White,
                        style = MaterialTheme.typography.h6,
                    )
                }

                Text(
                    text = "We respect your privacy. We'll only share informations that's important for you - no spam!",
                    // style = MaterialTheme.typography.h3,
                    modifier = Modifier
                        .padding(7.dp, 7.dp, 0.dp, 0.dp)
                        .align(Alignment.CenterHorizontally),
                    color = Color.Gray
                    )
            }
        }
    }


}