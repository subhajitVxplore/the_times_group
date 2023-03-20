package com.vxplore.thetimesgroup.custom_views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties

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