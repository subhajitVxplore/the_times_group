package com.vxplore.thetimesgroup.screens

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vxplore.thetimesgroup.R
import com.vxplore.thetimesgroup.ui.theme.GreenLight
import com.vxplore.thetimesgroup.ui.theme.GreyLight

@Composable
fun VendorAddSuccessScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(GreyLight)
            .padding(5.dp)) {

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
                text = "Add Vendor",
                color = Color.DarkGray,
                fontSize = 17.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = "Thank you",
            color = Color.DarkGray,
            modifier = Modifier
                .padding(start = 15.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Vendor Raman Sharma added.",
            color = Color.Gray,
            modifier = Modifier
                .padding(start = 15.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(30.dp))
        val context = LocalContext.current
        Button(
            onClick = {
                Toast.makeText(context, "Generate Bill", Toast.LENGTH_SHORT).show()
            },
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .height(53.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = GreenLight)
        ) {
            Text(
                text = "Add Vendors",
                color = Color.White,
                style = MaterialTheme.typography.h6,
            )
        }
    }


}