package com.vxplore.thetimesgroup.screens

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.vxplore.core.common.AppRoutes
import com.vxplore.thetimesgroup.R
import com.vxplore.thetimesgroup.custom_views.OtpTextField
import com.vxplore.thetimesgroup.ui.theme.GreenDark
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OtpScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),

        // verticalArrangement = Arrangement.Center,
        //horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.ic_baseline_keyboard_backspace_24),
            contentDescription = "countryImage",

            )

        Text(
            text = "Verify OTP",
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.h5,
        )
        Text(
            text = "you have sent OTP on your mobile number",
            // style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(10.dp,7.dp,0.dp,0.dp),
            color = Color.Gray
        )


        Surface(
          //  border = BorderStroke(1.dp, Color.Gray),
           // shape = RoundedCornerShape(8.dp),
           // elevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
//            modifier = Modifier.fillMaxWidth().height(50.dp)

        ) {
            Row(
                // modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(5.dp)
                modifier = Modifier.padding(25.dp,0.dp,0.dp,0.dp)

            ) {


                Text(
                    text = "+91",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.align(Alignment.CenterVertically),

                    )

                Text(
                    text = "7012345678",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(10.dp).align(Alignment.CenterVertically),
                )

                Image(painter = painterResource(id = R.drawable.ic_edit_icon),
                    contentDescription = "Edit",
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .padding(0.dp, 10.dp, 10.dp, 10.dp).align(Alignment.CenterVertically),
                )

            }

        }


///////////////////////////////////////////////////////////

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White),
                //.padding(20.dp),
            color = Color.White
        ) {
            var otpValue by remember {
                mutableStateOf("")
            }

            OtpTextField(
                otpText = otpValue,
                onOtpTextChange = { value, otpInputFilled ->
                    otpValue = value
                }
            )
        }

/////////////////////////////////////////////////////////
        Row(
            // modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(5.dp)
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(25.dp,0.dp,0.dp,0.dp)

        ) {

            Text(
                text = "Haven't received?",
                // style = MaterialTheme.typography.h3,
                modifier = Modifier.align(Alignment.CenterVertically),
            )

            val cont= LocalContext.current
            TextButton(onClick = {
                Toast.makeText(cont, "", Toast.LENGTH_SHORT).show()
            }) {
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "Resend it",
                    textDecoration = TextDecoration.Underline,
                    color = GreenDark
                )
            }

        }
//////////////////////////////////////////////////////////

        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
                .align(Alignment.BottomCenter)) {
                val context= LocalContext.current
                Button(
                    onClick = {
                        navController.navigate(AppRoutes.DASHBOARD)

                    }, shape = RoundedCornerShape(5.dp), modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
                ) { Text(text = "Verify OTP", color = Color.White,style = MaterialTheme.typography.h6,)}

            }

        }



    }
    
    
    
    
}
