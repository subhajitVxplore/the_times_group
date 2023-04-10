package com.vxplore.thetimesgroup.custom_views

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.vxplore.core.domain.model.Pincodes
import com.vxplore.core.domain.model.SearchVendor
import com.vxplore.thetimesgroup.R
import com.vxplore.thetimesgroup.extensions.*
import com.vxplore.thetimesgroup.ui.theme.GreenLight
import com.vxplore.thetimesgroup.viewModels.AddVendorViewModel
import com.vxplore.thetimesgroup.viewModels.BillingScreenViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PincodeSearchField(viewModel: AddVendorViewModel) {

//    var mExpanded by remember { mutableStateOf(false) }

    val icon = if (viewModel.mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.Add


    AnimatedContent(
        targetState = true,
        transitionSpec = {
            if(targetState && !initialState) {
                upToBottom()
            } else {
                bottomToUp()
            }
        }
    ) {
//        if (!it) {

            Column() {
                Surface(
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                    border = BorderStroke(1.dp, Color.Gray),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                //  .clickable { mExpanded = !mExpanded }
                                .fillMaxHeight()
                                .weight(1f)
                                .padding(start = 15.dp)
                        ) {
                            Text(
                                //text = viewModel.selectedPincode.value,
                                text = viewModel.previousSelectedPincode.value,
                                //label = label,
                                color = Color.DarkGray,
                                fontSize = 17.sp,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                        Icon(icon, "contentDescription",
                            Modifier
                                .padding(end = 4.dp)
                                .align(Alignment.CenterVertically)
                                .clickable {
                                    viewModel.mExpanded = !viewModel.mExpanded

                                    viewModel.isFocused.value=true
                                    viewModel.getPincodesByDistributorId("")
                                })
                    }
                }
            }

//        } else {
//            CircularProgressIndicator(
//                modifier = Modifier
//                    .size(width = screenWidth * 0.15f, height = screenHeight * 0.15f)
//                    .padding(bottom = screenHeight * 0.05f),
//                color = GreenLight,
//                strokeWidth = 5.dp,
//            )
//        }
    }


   // }
}/////----->
////////////////////////////////////////////////////////////////////////////
@Composable
fun SearchPincodesSection(viewModel: AddVendorViewModel) {

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val context= LocalContext.current
    val maxWidth = LocalConfiguration.current.screenWidthDp.dp
    Box(modifier = Modifier
        .fillMaxWidth()
        .focusRequester(focusRequester), contentAlignment = Alignment.CenterEnd) {
        Column(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = viewModel.selectedPincode.value,
                onValueChange = {
                    focusRequester.captureFocus()
                    viewModel.selectedPincode.value=it
                   // viewModel.isFocused.value=true
                    viewModel.getPincodesByDistributorId(it)
                   // Toast.makeText(context, "focused", Toast.LENGTH_SHORT).show()
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done,keyboardType = KeyboardType.Number),
                singleLine = true,
                trailingIcon = {
                    if (viewModel.selectedPincode.value.isNotEmpty()) {
                        Icon(painter = painterResource(id = R.drawable.ic_baseline_close_24),
                            contentDescription = null, modifier = Modifier.clickable {
                                viewModel.clearPincodesQuery()
                            })
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = null
                        )
                    }
                },
                placeholder = {
                    Text(text = "Serving PinCode(s)")
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp)
            )
        }
    }
}

///////////////////////////////////////////////////////////////
@Composable
fun PincodesSuggestionsSection(viewModel: AddVendorViewModel) {
    val pncodes = viewModel.pincodes.collectAsState().value
    val focusManager = LocalFocusManager.current
    var visible by remember { mutableStateOf(true) }
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(initialAlpha = 0.4f),
        exit = fadeOut(animationSpec = tween(durationMillis = 250))
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            shape = RoundedCornerShape(12.dp),
            color = Color.LightGray
        ) {
            Column() {
            SearchPincodesSection( viewModel)
            LazyColumn(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp)

            ) {
                items(pncodes) { item ->
                    Text(
                        text = item.pincode,
                        modifier = Modifier.fillMaxSize().padding(start = 15.dp, bottom = 10.dp).clickable(onClick = {
                                viewModel.onSelectPincode(item)
                                visible = !visible
                                //focusManager.clearFocus()
                            })
                    )
                }
            }
        }//column
        }
    }
}




