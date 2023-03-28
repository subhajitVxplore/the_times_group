package com.vxplore.thetimesgroup.custom_views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.vxplore.thetimesgroup.screens.Paper

@Composable
fun MyDropdown(label:String,dataList: List<Paper>, onSelect: (String)->Unit){

    var mExpanded by remember { mutableStateOf(false) }
    var mSelectedText by remember { mutableStateOf("") }


    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown
  
    Column() {
        OutlinedTextField(
            readOnly = true,
            value = mSelectedText,
            onValueChange = { mSelectedText = it },
            modifier = Modifier.fillMaxWidth(),
            label = {Text(label)},
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable { mExpanded = !mExpanded })
            }
        )

        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
        ) {
            dataList.forEach {
                DropdownMenuItem(onClick = {
                    mSelectedText = it.name
                    mExpanded = false
                    onSelect(mSelectedText)
                }) {
                    Text(text = it.name)
                }
            }
        }
    }
}
