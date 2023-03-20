@file:Suppress("DEPRECATION")

package com.vxplore.thetimesgroup.screens

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vxplore.thetimesgroup.R
import com.vxplore.thetimesgroup.custom_views.ExpandableCard
import com.vxplore.thetimesgroup.custom_views.TextFieldWithDropdownUsage
import com.vxplore.thetimesgroup.ui.theme.DonutGreenLight
import com.vxplore.thetimesgroup.ui.theme.GreyLight
import com.vxplore.thetimesgroup.viewModels.BillingScreenViewModel

@Composable
fun VendorBillingScreen(
    viewModel: BillingScreenViewModel = hiltViewModel(),
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
                text = "Billing Now",
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

                val context = LocalContext.current
                Button(
                    onClick = {
                        Toast.makeText(context, "Add Vendor", Toast.LENGTH_SHORT).show()
                    },
                    shape = RoundedCornerShape(25.dp),
                    modifier = Modifier.wrapContentSize(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = DonutGreenLight)
                ) {
                    Text(
                        text = "Add Vendor",
                        color = Color.White,
                        fontSize = 13.sp,
                        modifier = Modifier.align(Alignment.CenterVertically),
                        fontStyle = FontStyle.Normal
                    )
                }
            }
        }
        TextFieldWithDropdownUsage()
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(), contentAlignment = Alignment.TopEnd
        ) {
            Row(Modifier.wrapContentHeight()) {
                Text(
                    text = "Times of India",
                    color = Color.DarkGray,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.LightGray,
                        unfocusedBorderColor = Color.LightGray
                    ),
                    modifier = Modifier
                        .width(135.dp)
                        .height(50.dp)
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 15.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(7.dp))
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(), contentAlignment = Alignment.TopEnd
        ) {
            Row(Modifier.wrapContentHeight()) {
                Text(
                    text = "Economics Times",
                    color = Color.DarkGray,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.LightGray,
                        unfocusedBorderColor = Color.LightGray
                    ),
                    modifier = Modifier
                        .width(135.dp)
                        .height(50.dp)
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 15.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(7.dp))
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(), contentAlignment = Alignment.TopEnd
        ) {
            Row(Modifier.wrapContentHeight()) {
                Text(
                    text = "Ei Samay",
                    color = Color.DarkGray,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.LightGray,
                        unfocusedBorderColor = Color.LightGray
                    ),
                    modifier = Modifier
                        .width(135.dp)
                        .height(50.dp)
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 15.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(7.dp))
        ExpandableCard(header = "Returns", viewModel = viewModel)


        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.BottomEnd)
            ) {
                Divider(
                    color = Color.LightGray,
                    thickness = 0.8.dp,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
                Text(
                    text = "₹ 7500/-",
                    style = MaterialTheme.typography.h5,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(horizontal = 10.dp)
                        .align(Alignment.End)
                )
                Divider(
                    color = Color.LightGray,
                    thickness = 0.8.dp,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(Modifier.wrapContentHeight()) {
                    Text(
                        text = "Mode of Payment", color = Color.DarkGray, modifier = Modifier
                            .padding(horizontal = 15.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Box(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth(), contentAlignment = Alignment.TopEnd
                    ) {
                        Row(modifier = Modifier.wrapContentSize()) {
                            Text(
                                text = "Cash ₹",
                                color = Color.DarkGray,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                            OutlinedTextField(
                                value = "",
                                onValueChange = {},
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color.Gray,
                                    unfocusedBorderColor = Color.Gray,
                                    backgroundColor = Color.White,
                                ),
                                modifier = Modifier
                                    .width(120.dp)
                                    .height(45.dp)
                                    .padding(horizontal = 15.dp)
                            )
                        }

                    }
                }
                Spacer(modifier = Modifier.height(5.dp))



                Column(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight().padding(horizontal = 10.dp)
                ) {
                    Text(
                        text = "Coupons",
                        color = Color.DarkGray,
                        modifier = Modifier
                    )


                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        item {
                            Row(
                                modifier = Modifier
                                    .width(85.dp)
                                    //.weight(1f, true)
                                    .height(30.dp)
                                    .padding(horizontal = 4.dp)
                                   // .align(Alignment.CenterVertically)
                            ) {
                                Button(
                                    onClick = { },
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = Color.DarkGray,
                                        backgroundColor = Color.Gray
                                    ),
                                    shape = RoundedCornerShape(10, 0, 0, 10),
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .weight(1f, true)
                                ) {
                                    Text(text = "₹ 50")
                                }
                                OutlinedTextField(
                                    value = "",
                                    onValueChange = {},
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = Color.Gray,
                                        unfocusedBorderColor = Color.Gray,
                                        backgroundColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(0, 10, 10, 0),
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .weight(1f, true)
                                )
                            }

                        }

                        item {
                            Row(
                                modifier = Modifier
                                    .width(85.dp)
                                    //.weight(1f, true)
                                    .height(30.dp)
                                    .padding(horizontal = 4.dp)
                                    //.align(Alignment.CenterVertically)
                            ) {
                                Button(
                                    onClick = { },
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = Color.DarkGray,
                                        backgroundColor = Color.Gray
                                    ),
                                    shape = RoundedCornerShape(10, 0, 0, 10),
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .weight(1f, true)
                                ) {
                                    Text(text = "₹50",fontSize = 5.sp)
                                }
                                OutlinedTextField(
                                    value = "",
                                    onValueChange = {},
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = Color.Gray,
                                        unfocusedBorderColor = Color.Gray,
                                        backgroundColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(0, 10, 10, 0),
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .weight(1f, true)
                                )
                            }

                        }


                        item {
                            Row(
                                modifier = Modifier
                                    .width(85.dp)
                                    //.weight(1f, true)
                                    .height(30.dp)
                                    .padding(horizontal = 4.dp)
                                   // .align(Alignment.CenterVertically)
                            ) {
                                Button(
                                    onClick = { },
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = Color.DarkGray,
                                        backgroundColor = Color.Gray
                                    ),
                                    shape = RoundedCornerShape(10, 0, 0, 10),
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .weight(1f, true)
                                ) {
                                    Text(text = "₹ 50",fontSize = 25.sp,)
                                }
                                OutlinedTextField(
                                    value = "",
                                    onValueChange = {},
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = Color.Gray,
                                        unfocusedBorderColor = Color.Gray,
                                        backgroundColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(0, 10, 10, 0),
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .weight(1f, true),
                                    textStyle = TextStyle.Default.copy(fontSize = 25.sp)
                                )
                            }

                        }


                        item {
                            Text(
                                text = "₹ 7500/-",
                                style = MaterialTheme.typography.h6,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(horizontal = 10.dp)
                                   // .align(Alignment.CenterVertically)
                                //.weight(1f, true)
                            )
                        }




                    }//Row
                    Spacer(modifier = Modifier.height(7.dp))
                }//Column


            }


        }

    }
}

