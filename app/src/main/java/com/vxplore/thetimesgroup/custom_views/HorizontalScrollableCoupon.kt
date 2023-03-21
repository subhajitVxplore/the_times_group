package com.vxplore.thetimesgroup.custom_views

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
import com.vxplore.thetimesgroup.screens.Person

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HorizontalScrollableCoupon(personList: List<Person>) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            //.padding(10.dp)
            .horizontalScroll(
                state = scrollState,
            ),
        content = {
            for ((index, person) in personList.withIndex()) {
                Card(
                    shape = RoundedCornerShape(4.dp),
                    backgroundColor = Color.Gray,
                    modifier = Modifier.fillMaxHeight().wrapContentWidth().padding(horizontal = 10.dp)


                ) {
                    Row(modifier = Modifier.fillMaxHeight().wrapContentWidth()) {
                        Text(
                            person.age.toString(),
                            modifier = Modifier.wrapContentSize().align(Alignment.CenterVertically),
                            //modifier = Modifier.padding(16.dp),
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 15.sp
                            )
                        )
                        Surface(modifier = Modifier.fillMaxHeight(), color = Color.White) {
                            BasicTextField(
                                value = "",
                                onValueChange = {},
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
                                maxLines = 1,
                               // modifier = Modifier.width(80.dp).height(37.dp),
                                textStyle = TextStyle.Default.copy(fontSize = 15.sp)
                            ) {
                                TextFieldDefaults.OutlinedTextFieldDecorationBox(
                                    value = "",
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
            }
        })
}