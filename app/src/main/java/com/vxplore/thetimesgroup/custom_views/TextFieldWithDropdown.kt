package com.vxplore.thetimesgroup.custom_views

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TextFieldWithDropdown(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    setValue: (TextFieldValue) -> Unit,
    onDismissRequest: () -> Unit,
    dropDownExpanded: Boolean,
    list: List<String>,
) {
    Box(modifier) {

//        BasicTextField(
//            value = value,
//            onValueChange = setValue,
//            //keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
//            maxLines = 1,
//            modifier = Modifier.fillMaxWidth().height(40.dp).padding(horizontal = 15.dp)
//                .onFocusChanged { focusState ->
//                    if (!focusState.isFocused)
//                        onDismissRequest()
//                },
//            //placeholder = { Text(text = "Vendor Name", color = Color.Gray)},
//            textStyle = TextStyle.Default.copy(fontSize = 20.sp)
//        ) {
//            TextFieldDefaults.OutlinedTextFieldDecorationBox(
//                value = value.toString(),
//                innerTextField = it,
//                enabled = true,
//                singleLine = true,
//                visualTransformation = VisualTransformation.None,
//                interactionSource = MutableInteractionSource(),
//                //contentPadding = PaddingValues(all = 4.dp),
//                contentPadding = PaddingValues(start = 10.dp),
//                placeholder = { Text(text = "Vendor Name", color = Color.Gray)},
//                colors = ExposedDropdownMenuDefaults.textFieldColors(backgroundColor = Color.White)
//            )
//        }


        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth().padding(horizontal = 15.dp, vertical = 10.dp).height(50.dp)
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused)
                        onDismissRequest()
                },
            value = value,
            onValueChange = setValue,
            placeholder = { Text(text = "Vendor Name", color = Color.Gray)},
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.LightGray,
                unfocusedBorderColor = Color.LightGray
            )

        )
        DropdownMenu(
            expanded = dropDownExpanded,
            properties = PopupProperties(
                focusable = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            onDismissRequest = onDismissRequest
        ) {
            list.forEach { text ->
                DropdownMenuItem(onClick = {
                    setValue(
                        TextFieldValue(
                            text,
                            TextRange(text.length)
                        )
                    )
                }) {
                    Text(text = text)
                }
            }
        }
    }
}


val all = listOf("aaa", "baa", "aab", "abb", "bab")

val dropDownOptions = mutableStateOf(listOf<String>())
val textFieldValue = mutableStateOf(TextFieldValue())
val dropDownExpanded = mutableStateOf(false)
fun onDropdownDismissRequest() {
    dropDownExpanded.value = false
}

fun onValueChanged(value: TextFieldValue) {
    dropDownExpanded.value = true
    textFieldValue.value = value
    dropDownOptions.value = all.filter { it.startsWith(value.text) && it != value.text }.take(3)
}

@Composable
fun TextFieldWithDropdownUsage() {
    TextFieldWithDropdown(
        modifier = Modifier.fillMaxWidth(),
        value = textFieldValue.value,
        setValue = ::onValueChanged,
        onDismissRequest = ::onDropdownDismissRequest,
        dropDownExpanded = dropDownExpanded.value,
        list = dropDownOptions.value,
    )
}