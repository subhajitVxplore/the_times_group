package com.vxplore.thetimesgroup.screens

import android.app.Activity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vxplore.core.domain.model.Vendor
import com.vxplore.thetimesgroup.R
import com.vxplore.thetimesgroup.custom_views.PincodeSearchField
import com.vxplore.thetimesgroup.custom_views.PincodesSuggestionsSection
import com.vxplore.thetimesgroup.extensions.bottomToUp
import com.vxplore.thetimesgroup.extensions.screenHeight
import com.vxplore.thetimesgroup.extensions.screenWidth
import com.vxplore.thetimesgroup.extensions.upToBottom
import com.vxplore.thetimesgroup.ui.theme.GreenLight
import com.vxplore.thetimesgroup.ui.theme.GreyDark
import com.vxplore.thetimesgroup.ui.theme.GreyLight
import com.vxplore.thetimesgroup.viewModels.BillPreviewScreenViewModel
import com.vxplore.thetimesgroup.viewModels.BillingScreenViewModel

@Composable
fun VendorBillPreviewScreen(
    viewModel: BillPreviewScreenViewModel = hiltViewModel(),
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 10.dp)) {

        Text(
            text = "CASH RECEIPT",
            color = Color.Black,
            fontSize = 10.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 10.dp),
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = "# B67089D8D20230427",
            color = Color.Black,
            fontSize = 8.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            // .padding(top = 3.dp),
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "27/04/2023",
            color = Color.Black,
            fontSize = 8.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 5.dp),
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "komal",
            color = Color.Black,
            fontSize = 8.sp,
            modifier = Modifier
                .align(Alignment.Start),
            //.padding(top = 2.dp),
            fontWeight = FontWeight.Bold,
        )
//-------------------------------------------------------------
        Card(
            shape = RectangleShape,
            // backgroundColor = Color.White,
            border = BorderStroke(1.dp, Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 5.dp)
        ) {

            Text(
                text = "Order Details",
                color = Color.Black,
                fontSize = 8.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                fontWeight = FontWeight.Bold,
            )
        }//card


        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(80.dp, 5.dp, 0.dp, 5.dp)
        ) {

            Text(
                text = "Paper(s)",
                fontWeight = FontWeight.Bold,
                fontSize = 8.sp,
                modifier = Modifier.weight(1f, true)
            )
            Text(
                text = "Price",
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f, true)
                // .padding(start = 10.dp)
            )
            Text(
                text = "Qty",
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f, true)
            )
            Text(
                text = "Total",
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f, true)
            )
        }//row


//-----------------------------------------------------------------------

        Card(
            shape = RectangleShape,
            // backgroundColor = Color.White,
            border = BorderStroke(1.dp, Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 5.dp)
        ) {

            Text(
                text = "Return Details",
                color = Color.Black,
                fontSize = 8.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                fontWeight = FontWeight.Bold,
            )
        }//card


        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(80.dp, 5.dp, 0.dp, 5.dp)
        ) {

            Text(
                text = "Paper(s)",
                fontWeight = FontWeight.Bold,
                fontSize = 8.sp,
                modifier = Modifier.weight(1f, true)
            )
            Text(
                text = "Price",
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f, true)
                // .padding(start = 10.dp)
            )
            Text(
                text = "Qty",
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f, true)
            )
            Text(
                text = "Total",
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f, true)
            )
        }//row


//-----------------------------------------------------------------------


        Card(
            shape = RectangleShape,
            // backgroundColor = Color.White,
            border = BorderStroke(1.dp, Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 5.dp)
        ) {

            Text(
                text = "Coupons",
                color = Color.Black,
                fontSize = 8.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                fontWeight = FontWeight.Bold,
            )
        }//card


        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(80.dp, 5.dp, 0.dp, 5.dp)
        ) {

            Text(
                text = "Coupon Name",
                fontWeight = FontWeight.Bold,
                fontSize = 8.sp,
                modifier = Modifier.weight(1f, true)
            )
            Text(
                text = "Price",
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f, true)
                // .padding(start = 10.dp)
            )
            Text(
                text = "Qty",
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f, true)
            )
            Text(
                text = "Total",
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f, true)
            )
        }//row



//-----------------------------------------------------------------------
        Divider(
            color = Color.Gray,
            thickness = 0.8.dp,
            modifier = Modifier.padding(vertical = 10.dp)
        )
//-----------------------------------------------------------------------
        Row(
            modifier = Modifier
                .wrapContentSize().align(Alignment.End)
                //.padding(200.dp, 5.dp, 0.dp, 5.dp)
        ) {

            Text(
                text = "Sub Total :",
                fontWeight = FontWeight.Bold,
                fontSize = 8.sp,
               // modifier = Modifier.weight(1f, true)
            )
            Text(
                text = "₹2000",
                fontSize = 8.sp,
                //fontWeight = FontWeight.Bold,
                modifier = Modifier
                // .width(500.dp)
                   // .weight(1f, true)
                 .padding(end = 20.dp)
            )

        }//row
        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(200.dp, 5.dp, 0.dp, 5.dp)
        ) {

            Text(
                text = "Old Due :",
                fontWeight = FontWeight.Bold,
                fontSize = 8.sp,
                modifier = Modifier.weight(1f, true)
            )
            Text(
                text = "+ 0",
                fontSize = 8.sp,
                //fontWeight = FontWeight.Bold,
                modifier = Modifier
                    // .width(200.dp)
                    // .weight(1f, true)
                    .padding(end = 20.dp)
            )

        }//row

//-----------------------------------------------------------------------


    }


}

//////////////////////////////////////////////////////////////////////////////////////////
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun showOrderedPaperList(vendorList: List<Vendor>, loading: Boolean) {

    AnimatedContent(
        targetState = loading,
        transitionSpec = {
            if (targetState && !initialState) {
                upToBottom()
            } else {
                bottomToUp()
            }
        }
    ) {
        if (!it) {

            LazyColumn() {
                itemsIndexed(items = vendorList) { index, vendorr ->
                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(100.dp, 5.dp, 0.dp, 5.dp)
                    ) {

                        Text(
                            text = "Paper(s)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 8.sp,
                            modifier = Modifier.weight(1f, true)
                        )
                        Text(
                            text = "Price",
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .weight(1f, true)
                            // .padding(start = 10.dp)
                        )
                        Text(
                            text = "Qty",
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f, true)
                        )
                        Text(
                            text = "Total",
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f, true)
                        )
                    }//row
                    Divider(
                        color = GreyLight,
                        thickness = 0.8.dp,
                        modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp)
                    )
                }
            }


        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(width = screenWidth * 0.15f, height = screenHeight * 0.15f)
                        .padding(bottom = screenHeight * 0.05f),
                    color = GreenLight,
                    strokeWidth = 5.dp,
                )
            }

        }
    }

    ///////////////////////////////////////////////////////////////////


}