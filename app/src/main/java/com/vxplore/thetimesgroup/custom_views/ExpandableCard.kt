@file:Suppress("DEPRECATION")

package com.vxplore.thetimesgroup.custom_views

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vxplore.thetimesgroup.screens.Paper
import com.vxplore.thetimesgroup.ui.theme.GreyLight
import com.vxplore.thetimesgroup.viewModels.BillingScreenViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpandableCard(
    header: String, // Header
    //description: String, // Description
   // color: Color, // Color
    paperList: List<Paper>,
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
                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween // Control the header Alignment over here.
            ) {
                Text(
                    text = header,
                    color = Color.DarkGray, // Header Color
                    fontSize = 15.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.weight(.9f).padding(start = 8.dp)
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
                LazyColumn(modifier = Modifier.height(125.dp).fillMaxWidth()) {
                    itemsIndexed(items = paperList) { index, paperr ->
                        Row(Modifier.wrapContentHeight()) {
                            Column(modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.CenterVertically)
                                .padding(13.dp, 0.dp, 0.dp, 0.dp)) {
                                Text(text = paperr.name, color = Color.DarkGray)
                                Text(text = "Yesterday Total Paper 500 (Subscription 300)", color = Color.Gray, fontSize = 10.sp)
                            }
                            Box(modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxWidth(), contentAlignment = Alignment.TopEnd){
                                Surface(modifier = Modifier.wrapContentSize() .padding(horizontal = 15.dp), color = Color.White) {
                                    BasicTextField(
                                        value = viewModel.toiReturn.value,
                                        onValueChange = {viewModel.toiReturn.value=it},
                                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
                                        maxLines = 1,
                                        modifier = Modifier.width(80.dp).height(37.dp),
                                        textStyle = TextStyle.Default.copy(fontSize = 17.sp)
                                    ) {
                                        TextFieldDefaults.OutlinedTextFieldDecorationBox(
                                            value = viewModel.toiReturn.value,
                                            innerTextField = it,
                                            enabled = true,
                                            singleLine = true,
                                            visualTransformation = VisualTransformation.None,
                                            interactionSource = MutableInteractionSource(),
                                            contentPadding = PaddingValues(all = 4.dp),
                                            colors = textFieldColors(backgroundColor = Color.White)
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(7.dp))
                    }

                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }

}