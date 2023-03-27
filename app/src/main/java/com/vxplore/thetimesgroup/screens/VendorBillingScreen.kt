@file:Suppress("DEPRECATION")
package com.vxplore.thetimesgroup.screens
import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vxplore.thetimesgroup.R
import com.vxplore.thetimesgroup.custom_views.ExpandableCard
import com.vxplore.thetimesgroup.custom_views.HorizontalScrollableCoupon
import com.vxplore.thetimesgroup.custom_views.TextFieldWithDropdownUsage
import com.vxplore.thetimesgroup.custom_views.showPapersTakenList
import com.vxplore.thetimesgroup.ui.theme.DonutGreenLight
import com.vxplore.thetimesgroup.ui.theme.GreenLight
import com.vxplore.thetimesgroup.ui.theme.GreyLight
import com.vxplore.thetimesgroup.ui.theme.PinkLight
import com.vxplore.thetimesgroup.viewModels.BillingScreenViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun VendorBillingScreen(
    viewModel: BillingScreenViewModel = hiltViewModel(),
) {
//    var coupon_total = 0
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
                Button(
                    onClick = { viewModel.onBillingToAddVendor() },
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

        Column(
            modifier = Modifier
                //.fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .weight(weight = 1f)

        ) {
            TextFieldWithDropdownUsage()
            showPapersTakenList(paperList = getPaperPrice(), viewModel,
                onPriceChange = { value, index, multi ->
                    try {
                        viewModel.takenPapers[index] = Pair(first = multi, second = value.toInt())
                    }catch (e:Exception){
                        println(e)
                    }

                }
            )
            Spacer(modifier = Modifier.height(7.dp))
            ExpandableCard(header = "Returns", paperList = getPaperPrice(), viewModel = viewModel)
        }
//        TextFieldWithDropdownUsage()
//        showPapersTakenList(paperList = getPaperPrice(), viewModel,
//            onPriceChange = { value, index, multi ->
//                viewModel.takenPapers[index] = Pair(first = multi, second = value.toInt())
//            }
//        )
//        Spacer(modifier = Modifier.height(7.dp))
//        ExpandableCard(header = "Returns",paperList = getPaperPrice(),viewModel = viewModel)

Column(modifier = Modifier.weight(1f,true)) {
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Button(
                    enabled = viewModel.takenPaperTotal.value <= 0,
                    onClick = {
                        viewModel.calculatePapersPrice()
                        viewModel.calculateCurrentDue()
                              },
                    shape = RoundedCornerShape(25.dp),
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.CenterVertically)
                        .padding(start = 10.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = DonutGreenLight)
                ) {
                    Text(
                        text = "Calculate Price",
                        color = Color.White,
                        fontSize = 13.sp,
                        modifier = Modifier.align(Alignment.CenterVertically),
                        fontStyle = FontStyle.Normal
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = "₹${viewModel.takenPaperTotal.value}",
                        style = MaterialTheme.typography.h5,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(horizontal = 10.dp)
                            .align(Alignment.CenterEnd)
                    )
                }

            }

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
                        BasicTextField(
                            value = viewModel.cashPayment.value.toString(),
                            onValueChange = {
                                try {
                                    viewModel.cashPayment.value = it.toInt()
                                }catch (e:Exception){
                                    println(e)
                                }
                                            },
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done,
                                keyboardType = KeyboardType.Number
                            ),
                            maxLines = 1,
                            modifier = Modifier
                                .width(100.dp)
                                .height(37.dp)
                                .padding(start = 5.dp, end = 15.dp),
                            // textStyle = TextStyle.Default.copy(fontSize = 20.sp),
                            textStyle = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            ),

                            ) {
                            TextFieldDefaults.OutlinedTextFieldDecorationBox(
                                value = viewModel.cashPayment.value.toString(),
                                innerTextField = it,
                                enabled = true,
                                singleLine = true,
                                visualTransformation = VisualTransformation.None,
                                interactionSource = MutableInteractionSource(),
                                contentPadding = PaddingValues(all = 4.dp),
                                colors = ExposedDropdownMenuDefaults.textFieldColors(backgroundColor = Color.White)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            // HorizontalScrollableCoupon(getPersonAge())
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
            ) {
                Row(Modifier.wrapContentSize()) {
                    Text(
                        text = "Coupons",
                        color = Color.DarkGray,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(horizontal = 15.dp)
                            .align(Alignment.CenterVertically),

                        )
                    // val cntxt = LocalContext.current

                    TextButton(modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.CenterVertically),
                        enabled = viewModel.couponTotal.value <= 0,
                        onClick = {
                            viewModel.calculateCoupon()
                            //Toast.makeText(cntxt, "Coupon=₹${viewModel.couponTotal.value}", Toast.LENGTH_SHORT).show()
                        }) {
                        Text(
                            text = "Apply Coupon",
                            color = GreenLight,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                ) {
                    Column(Modifier.weight(1f, true)) {
                        HorizontalScrollableCoupon(getPersonAge(), viewModel,
                            onPriceChange = { value, index, multi ->
                                // viewModel.coupons[index] = value.toInt()
                                try {
                                    viewModel.coupons[index] =Pair(first = multi, second = value.toInt())
                                }catch (e:Exception){
                                    println(e)
                                }

                            }
                        )
                    }

                    Text(
                        text = "₹${viewModel.couponTotal.value}",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(horizontal = 10.dp)
                            .align(Alignment.CenterVertically),
                        fontSize = 20.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Surface(
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 15.dp),
                color = PinkLight,
                contentColor = Color.White
            ) {
                Row(
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .weight(3f, true)
                            .align(Alignment.CenterVertically)
                    ) {
                        Text(text = "Due Payment", color = Color.White)
                        Text(
                            text = "With previous ₹ ${viewModel.previousDue.value} Due",
                            color = Color.White,
                            fontSize = 10.sp
                        )
                    }
                    Text(
                        text = "₹${viewModel.currentDue.value}",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(horizontal = 10.dp)
                            .align(Alignment.CenterVertically),
                        fontSize = 20.sp
                    )
                }
            }//surface\
            Spacer(modifier = Modifier.height(7.dp))
            val context = LocalContext.current
            Button(
                onClick = {
                    Toast.makeText(context, "Generate Bill", Toast.LENGTH_SHORT).show()
                },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .fillMaxWidth()

                    .padding(horizontal = 15.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = GreenLight)
            ) {
                Text(
                    text = "Generate Bill",
                    color = Color.White,
                    style = MaterialTheme.typography.h6,
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}


    }//parent column
}
