package com.vxplore.thetimesgroup.custom_views


import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vxplore.core.domain.model.SearchVendor
import com.vxplore.thetimesgroup.screens.Paper
import com.vxplore.thetimesgroup.screens.Person
import com.vxplore.thetimesgroup.viewModels.BillingScreenViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun showPapersTakenList(
    paperList: MutableList<Paper>, viewModel: BillingScreenViewModel,
    price: String = "",
    onPriceChange: (String, Int, Int) -> Unit) {
//    LazyColumn(modifier = Modifier.height(300.dp).fillMaxWidth()) {
//                itemsIndexed(items = paperList) { index, paperr ->
    Column(modifier = Modifier.fillMaxSize()) {

                    for ((index, paperr) in paperList.withIndex()) {
                    Box(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth(),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Row(Modifier.wrapContentHeight()) {

                            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                                Text(text = paperr.name,color = Color.Gray,)
                                Text(
                                    text = "₹${paperr.price}",
                                    color = Color.Gray,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(end = 5.dp).align(Alignment.End)
                                )
                            }


                            var value by remember(price) {
                                mutableStateOf(price)
                            }

                            BasicTextField(
                                value = value,
                                onValueChange = {
                                    value = it
                                    onPriceChange(value, index, paperr.price)
                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done,
                                    keyboardType = KeyboardType.Number
                                ),
                                maxLines = 1,
                                modifier = Modifier
                                    .width(125.dp)
                                    .height(40.dp)
                                    .padding(horizontal = 15.dp),
                                textStyle = TextStyle.Default.copy(fontSize = 20.sp)
                            ) {
                                TextFieldDefaults.OutlinedTextFieldDecorationBox(
                                    value = value,
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
                    Spacer(modifier = Modifier.height(7.dp))
                }

     }
    }


