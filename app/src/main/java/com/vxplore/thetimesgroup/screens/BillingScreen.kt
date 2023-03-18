@file:Suppress("DEPRECATION")

package com.vxplore.thetimesgroup.screens

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vxplore.thetimesgroup.R
import com.vxplore.thetimesgroup.custom_views.AutoCompleteTextView
import com.vxplore.thetimesgroup.ui.theme.DonutGreenLight
import com.vxplore.thetimesgroup.ui.theme.DonutGreenMidium
import com.vxplore.thetimesgroup.ui.theme.GreyLight
import com.vxplore.thetimesgroup.viewModels.BillingScreenViewModel
import com.vxplore.thetimesgroup.viewModels.DashboardViewModel

@Composable
fun BillingScreen(
//    viewModel: BillingScreenViewModel = hiltViewModel(),
//    viewModelVendor: DashboardViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize()
            //.padding(16.dp),
    ) {
        Row(
            // modifier = Modifier.fillMaxWidth().wrapContentHeight()
            modifier = Modifier.fillMaxWidth().wrapContentHeight().background(GreyLight).padding(5.dp)
        ) {
            val activity = LocalContext.current as Activity
            Image(painter = painterResource(id = R.drawable.ic_baseline_keyboard_backspace_24),
                contentDescription = "back button",
                modifier = Modifier.clickable{
                     activity.onBackPressed()
                }.align(Alignment.CenterVertically).padding(horizontal = 10.dp)
            )

            Text(
                text = "Billing Now",
                color = Color.DarkGray,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Box(modifier = Modifier.wrapContentHeight().fillMaxWidth().align(Alignment.CenterVertically),
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
                            fontSize = 15.sp,
                            modifier = Modifier.align(Alignment.CenterVertically),
                            fontStyle = FontStyle.Normal
                        )
                    }
            }
        }


//
//        AutoCompleteTextView(
//            modifier = Modifier.fillMaxWidth(),
//            query = viewModelVendor.vendorsQuery.value,
//            queryLabel = "stringResource(id = R.string.location_section_address)",
//            onQueryChanged = { updatedVendor ->
//                viewModelVendor.vendorsQuery.value = updatedVendor
//               // viewModel.search(updatedAddress)
//                viewModelVendor.searchVendors(updatedVendor)
//            },
//            predictions = viewModelVendor.vendors.collectAsState().value,
//            onClearClick = {
//                viewModelVendor.vendorsQuery.value = ""
//                //Todo: call the view model method to clear the predictions
//            },
//            onDoneActionClick = {  },
//            onItemClick = {
//                //Todo: call the view model method to update the UI with the selection
//            }
//        ) {
//
//            //Define how the items need to be displayed
//            Text(it.top_vendors, fontSize = 14.sp)
//
//        }




    }
}
