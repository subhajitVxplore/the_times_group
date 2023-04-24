@file:Suppress("DEPRECATION")

package com.vxplore.thetimesgroup.screens

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.vxplore.thetimesgroup.R
import com.vxplore.thetimesgroup.custom_views.*
import com.vxplore.thetimesgroup.mainController.MainActivity
import com.vxplore.thetimesgroup.ui.theme.DonutGreenLight
import com.vxplore.thetimesgroup.ui.theme.GreenLight
import com.vxplore.thetimesgroup.ui.theme.GreyLight
import com.vxplore.thetimesgroup.ui.theme.PinkLight
import com.vxplore.thetimesgroup.utility.ItemFile
import com.vxplore.thetimesgroup.utility.MyFileModel
import com.vxplore.thetimesgroup.viewModels.BillingScreenViewModel

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun VendorBillingScreen(
    viewModel: BillingScreenViewModel = hiltViewModel(),
) {
//    var coupon_total = 0
    val suggestions = viewModel.suggestion.collectAsState().value
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

        Spacer(modifier = Modifier.height(7.dp))
        VendorSearchField(viewModel)
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(7.dp))
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .weight(1f)
                ) {
                    showPapersTakenList(
                        viewModel.circleLoading.value,
                        viewModel.paperss.collectAsState().value,
                        viewModel
                    ) { value2, index, value1 ->
                        try {
                            // viewModel.takenPapers[index] =Pair(first = value1, second = value2.toInt())
                            Log.d("TESTING", "value1 $value1 value2 $value2")
                            //viewModel.takenPapers.add(index, Pair(first = value1, second = value2.toInt()))
                            viewModel.calculateTakenPapersPrice(value1, value2.toInt(), index)
                        } catch (e: NumberFormatException) {
                            viewModel.calculateTakenPapersPrice(value1, 0, index)
                            println(e)
                        } catch (e: Exception) {
                            println(e)
                        }
                    }
                    Spacer(modifier = Modifier.height(7.dp))

                    val paperssListing = viewModel.paperss.collectAsState().value
                    if (paperssListing.isNotEmpty()) {
                    ExpandableCard(
                        viewModel.circleLoading.value,
                        "Returns",
                        viewModel.paperss.collectAsState().value,
                        viewModel
                    ) { value2, index, value1 ->
                        try {
                            viewModel.calculateReturnPapersPrice(value1, value2.toInt(), index)
                        } catch (e: NumberFormatException) {
                            viewModel.calculateReturnPapersPrice(value1, 0, index)
                            println(e)
                        } catch (e: Exception) {
                            println(e)
                        }
                    }
                }


                }
                Column(modifier = Modifier.weight(1f, true)) {
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
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .align(Alignment.CenterVertically)
                                ) {
                                    Text(
                                        text = "₹${viewModel.takenMinusreturnPaperTotal.value}",
                                       //  text = "₹${viewModel.takenPapersTotal.value}",
                                        //   text = "₹",
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
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(Modifier.wrapContentHeight()) {
                                Text(
                                    text = "Mode of Payment",
                                    color = Color.DarkGray,
                                    modifier = Modifier
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
                                                  //  viewModel.calculateCurrentDue()
                                                } catch (e: Exception) {
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
                                                colors = ExposedDropdownMenuDefaults.textFieldColors(
                                                    backgroundColor = Color.White
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                            Column(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .fillMaxWidth()
                            ) {
                                Row(Modifier.wrapContentSize()) {

                                    Text(
                                        text ="Coupons:",
//                                        text = if (viewModel.couponsTotal.value != 0) "Total Coupon Price = ₹${viewModel.couponsTotal.value}"
//                                               else "Coupons:",
                                        color = Color.DarkGray,
                                        modifier = Modifier
                                            .wrapContentSize()
                                            .padding(horizontal = 15.dp)
                                            .align(Alignment.CenterVertically),
                                        )
                                }

                               // Spacer(modifier = Modifier.height(5.dp))

                                Row(
                                    modifier = Modifier
                                        .wrapContentHeight()
                                        .fillMaxWidth()) {

                                    Column(Modifier.weight(1f, true)) {
                                        HorizontalScrollableCoupon(viewModel.couponss.collectAsState().value, viewModel,
                                            onPriceChange = { value, index, multi ->
                                                try {
                                                    viewModel.calculateCouponPrice(multi, value.toInt(), index)
                                                } catch (e: NumberFormatException) {
                                                    viewModel.calculateCouponPrice(multi, 0, index)
                                                    println(e)
                                                } catch (e: Exception) {
                                                    println(e)
                                                }
                                            }
                                        )
                                    }
                                }
                            }
//---------------------------------------------------------------------------------------------------
                            Spacer(modifier = Modifier.height(5.dp))
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

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .align(Alignment.CenterVertically)
                                ) {
                                    Text(
                                       // text = "₹${viewModel.couponTotal.value}",
                                        text = "₹${viewModel.cashMinusCouponTotal.value}",
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
//---------------------------------------------------------------------------------------------------
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


                                    Box(modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f, true)
                                        .align(Alignment.CenterVertically)
                                        .padding(start = 15.dp)){
                                        Column(
                                            modifier = Modifier
                                                .wrapContentSize()
                                                //.align(Alignment.CenterVertically)
                                        ) {
                                            Text(text = "Due Payment", color = Color.White, modifier = Modifier.align(Alignment.Start))
                                            Text(
                                                text = "With previous ₹ ${viewModel.previousDue.value} Due",
                                                color = Color.White,
                                                fontSize = 10.sp,
                                                modifier = Modifier.align(Alignment.Start)
                                            )
                                        }
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
                        }
                    }
                }
       //----------------Generate Bill Button Layout-------------------//
                val context = LocalContext.current as MainActivity
                context.ShowItemFileLayout(context = context)
                Spacer(modifier = Modifier.height(10.dp))

            }//column


            ///suggestion dropdown section calling
            AnimatedContent(targetState = suggestions.isNotEmpty()) { state ->
                if (viewModel.searchVendorQuery != "") {
                    when (state) {
                        true -> SuggestionsSection(suggestions,viewModel)
                        false -> Surface() { state }
                    }
                }
            }
        }//box

    }//parent column

    val mContext = LocalContext.current
    LaunchedEffect(viewModel.toastError.value) {
        if (viewModel.toastError.value.isNotEmpty()) {
            Toast.makeText(mContext, viewModel.toastError.value, Toast.LENGTH_SHORT).show()
            viewModel.toastError.value = ""
        }
    }

    LaunchedEffect(viewModel.takenPapersTotal.value,viewModel.returnsTotal.value){
        viewModel.takenMinusreturnPaperTotal.value=viewModel.takenPapersTotal.value - viewModel.returnsTotal.value
        //viewModel.takenPapersTotal.value=0
    }

    LaunchedEffect(viewModel.cashPayment.value,viewModel.couponsTotal.value){
            viewModel.cashMinusCouponTotal.value =viewModel.cashPayment.value + viewModel.couponsTotal.value

    }

  LaunchedEffect(viewModel.takenMinusreturnPaperTotal.value,viewModel.cashMinusCouponTotal.value,viewModel.previousDue.value){
        if (viewModel.takenMinusreturnPaperTotal.value > 0) {
            viewModel.currentDue.value =viewModel.previousDue.value.plus(viewModel.takenMinusreturnPaperTotal.value - viewModel.cashMinusCouponTotal.value)

        }
    }


}





