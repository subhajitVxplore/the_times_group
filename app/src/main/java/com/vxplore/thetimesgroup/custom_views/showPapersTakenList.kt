package com.vxplore.thetimesgroup.custom_views


import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vxplore.thetimesgroup.screens.Paper
import com.vxplore.thetimesgroup.viewModels.BillingScreenViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun showPapersTakenList(paperList: List<Paper>, viewModel: BillingScreenViewModel,
                        price: String = "",
                        onPriceChange: (String, Int, Int) -> Unit) {


            LazyColumn(modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()) {
                itemsIndexed(items = paperList) { index, paperr ->
                    Box(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth(), contentAlignment = Alignment.TopEnd
                    ) {
                        Row(Modifier.wrapContentHeight()) {
                            Text(
                                text = paperr.name,
                                color = Color.Gray,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                            BasicTextField(
                                value = viewModel.takenPaperTotal.value.toString(),
                                onValueChange = {
                                    onPriceChange(it, index, paperr.price)
                                    viewModel.takenPaperTotal.value = it.toInt()
                                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done,
                                    keyboardType = KeyboardType.Number
                                ),
                                maxLines = 1,
                                modifier = Modifier
                                    .width(125.dp)
                                    .height(35.dp)
                                    .padding(horizontal = 15.dp),
                                textStyle = TextStyle.Default.copy(fontSize = 20.sp)
                            ) {
                                TextFieldDefaults.OutlinedTextFieldDecorationBox(
                                    value = viewModel.takenPaperTotal.value.toString(),
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