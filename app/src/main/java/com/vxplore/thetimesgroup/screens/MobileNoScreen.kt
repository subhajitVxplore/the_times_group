package com.vxplore.thetimesgroup.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.vxplore.core.common.AppRoutes
import com.vxplore.thetimesgroup.R
import kotlinx.coroutines.launch

@Composable
//fun MobileNoScreen(onContinueClick: () -> Unit) {
fun MobileNoScreen(navController: NavController) {
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
                .wrapContentSize()
              //  .padding(5.dp)
//            modifier = Modifier.fillMaxWidth().height(50.dp)

        ) {
            Row(
               // modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(5.dp)
                modifier = Modifier.padding(10.dp,0.dp,0.dp,0.dp)

            ) {
                Image(painter = painterResource(id = R.drawable.indian_flag),
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
                        .padding(5.dp,0.dp,0.dp,0.dp)

                )

//                Text(
//                    text = "7012345678",
//                    style = MaterialTheme.typography.h5,
//                    modifier = Modifier.padding(10.dp,0.dp,0.dp,0.dp)
//                )

                var textValue by remember { mutableStateOf(TextFieldValue("")) }
                val mContext = LocalContext.current

                TextField(value = textValue,
                    onValueChange = {
                    if (it.text.length <= 10) textValue = it
                    else Toast.makeText(mContext, "Can not be more than 10", Toast.LENGTH_SHORT).show()

                                                             },
                    textStyle = TextStyle.Default.copy(fontSize = 23.sp),
                    placeholder = { Text(text = "your mobile number",style = MaterialTheme.typography.h6, color = Color.Gray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically)
                        .background(Color.White),


                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Phone
                    ),

                    colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor =  Color.Transparent, //hide the indicator
                    unfocusedIndicatorColor = Color.White)

                )
            }

        }



        Row(
            // modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(5.dp)
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()

        ) {
            Image(painter = painterResource(id = R.drawable.ic_round_check_circle_24),
                contentDescription = "check",
                modifier = Modifier
                    .width(35.dp)
                    .height(35.dp)
                    .align(Alignment.CenterVertically)
            )

            Text(
                text = "you will receive important updates and informations from us over the whatsapp",
               // style = MaterialTheme.typography.h3,
                modifier = Modifier.align(Alignment.CenterVertically)
                    .padding(0.dp, 7.dp, 0.dp, 0.dp)
                )

        }

        Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.wrapContentSize()
            .align(Alignment.BottomCenter)) {
val context= LocalContext.current
    Button(

        onClick = {
             //Toast.makeText(context, "continue", Toast.LENGTH_SHORT).show()
            //onContinueClick()
            navController.navigate(AppRoutes.OTP)

        }, shape = RoundedCornerShape(5.dp), modifier = Modifier
            .fillMaxWidth()

            .height(50.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
    ) { Text(text = "Continue", color = Color.White,style = MaterialTheme.typography.h6,)
    }

    Text(
        text = "We respect your privacy. We'll only share informations that's important for you - no spam!",
        // style = MaterialTheme.typography.h3,
        modifier = Modifier
            .padding(7.dp, 7.dp, 0.dp, 0.dp)
            .align(Alignment.CenterHorizontally),


        )
}

    }



    }
}