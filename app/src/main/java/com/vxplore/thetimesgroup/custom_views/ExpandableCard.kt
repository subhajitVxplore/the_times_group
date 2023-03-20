package com.vxplore.thetimesgroup.custom_views

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vxplore.thetimesgroup.ui.theme.GreyLight
import com.vxplore.thetimesgroup.viewModels.BillingScreenViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpandableCard(
    header: String, // Header
    //description: String, // Description
   // color: Color, // Color
viewModel: BillingScreenViewModel
) {
    val rotationState by animateFloatAsState(if (viewModel.expand.value) 180f else 0f) // Rotation State
    Card(
        modifier = Modifier
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 400,
                    easing = LinearOutSlowInEasing
                )
            )
            .padding(8.dp),
        backgroundColor = Color.White,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(viewModel.stroke.value.dp, Color.Gray),
        onClick = {
            viewModel.expand.value = !viewModel.expand.value
            viewModel.stroke.value = if (viewModel.expand.value) 2 else 1
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(GreyLight)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween // Control the header Alignment over here.
            ) {
                Text(
                    text = header,
                    color = Color.DarkGray, // Header Color
                    fontSize = 15.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .weight(.9f)
                        .padding(start = 8.dp)
                )
                IconButton(
                    modifier = Modifier
                        .rotate(rotationState)
                        .weight(.1f),
                    onClick = {
                        viewModel.expand.value = !viewModel.expand.value
                        viewModel.stroke.value = if (viewModel.expand.value) 2 else 1
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        tint = Color.Gray, // Icon Color
                        contentDescription = "Drop Down Arrow",
                    )
                }
            }
            Divider(color = Color.LightGray, thickness = 0.8.dp)
            if (viewModel.expand.value) {
                Spacer(modifier = Modifier.height(7.dp))

                Row(Modifier.wrapContentHeight()) {
                        Column(modifier = Modifier.wrapContentSize().align(Alignment.CenterVertically).padding(13.dp,0.dp,0.dp,0.dp)) {
                            Text(text = "Times of India", color = Color.DarkGray)
                            Text(text = "Yesterday Total Paper 500 (Subscription 300)", color = Color.Gray, fontSize = 10.sp)
                        }
                        Box(modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth(), contentAlignment = Alignment.TopEnd){
                        OutlinedTextField(
                            value = "",
                            onValueChange = {},
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Gray,
                                unfocusedBorderColor = Color.Gray,
                                backgroundColor = Color.White,
                            ),
                            modifier = Modifier
                                .width(120.dp).height(45.dp)
                                .padding(horizontal = 15.dp)

                        )
                    }
                }
                Spacer(modifier = Modifier.height(7.dp))
                Row(Modifier.wrapContentHeight()) {
                    Column(modifier = Modifier.wrapContentSize().align(Alignment.CenterVertically).padding(13.dp,0.dp,0.dp,0.dp)) {
                        Text(text = "Economics Times", color = Color.DarkGray)
                        Text(text = "Yesterday Total Paper 400 (Subscription 200)", color = Color.Gray, fontSize = 10.sp)
                    }
                    Box(modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(), contentAlignment = Alignment.TopEnd){
                        OutlinedTextField(
                            value = "",
                            onValueChange = {},
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Gray,
                                unfocusedBorderColor = Color.Gray,
                                backgroundColor = Color.White,
                            ),
                            modifier = Modifier
                                .width(120.dp).height(45.dp)
                                .padding(horizontal = 15.dp)

                        )
                    }
                }
                Spacer(modifier = Modifier.height(7.dp))
                Row(Modifier.wrapContentHeight()) {
                    Column(modifier = Modifier.wrapContentSize().align(Alignment.CenterVertically).padding(13.dp,0.dp,0.dp,0.dp)) {
                        Text(text = "Ei Samay", color = Color.DarkGray)
                        Text(text = "Yesterday Total Paper 400 (Subscription 200)", color = Color.Gray, fontSize = 10.sp)
                    }
                    Box(modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(), contentAlignment = Alignment.TopEnd){
                        OutlinedTextField(
                            value = "",
                            onValueChange = {},
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Gray,
                                unfocusedBorderColor = Color.Gray,
                                backgroundColor = Color.White,
                            ),
                            modifier = Modifier
                                .width(120.dp).height(45.dp)
                                .padding(horizontal = 15.dp)

                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))


            }
        }
    }

}