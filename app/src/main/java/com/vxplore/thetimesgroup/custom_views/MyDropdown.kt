package com.vxplore.thetimesgroup.custom_views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vxplore.thetimesgroup.screens.Paper

@Composable
fun MyDropdown(label: String, dataList: List<Paper>, onSelect: (String) -> Unit) {

    var mExpanded by remember { mutableStateOf(false) }
    var mSelectedText by remember { mutableStateOf("") }
    if (mSelectedText.isEmpty()){
        mSelectedText=label
    }

    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column() {
        Surface(
            border = BorderStroke(1.dp, Color.Gray),
            shape = RoundedCornerShape(4.dp),
            //backgroundColor = Color.Yellow
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {

                Row(modifier = Modifier.fillMaxHeight().weight(1f).padding(start = 15.dp)) {
                    Text(
                        text = mSelectedText,
                        //label = label,
                        color = Color.DarkGray,
                        fontSize = 17.sp,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
                    Icon(icon, "contentDescription",
                        Modifier
                            .padding(end=4.dp)
                            .align(Alignment.CenterVertically)
                            .clickable { mExpanded = !mExpanded })

            }
        }

//        OutlinedTextField(
//            readOnly = true,
//            value = mSelectedText,
//            onValueChange = { mSelectedText = it },
//            modifier = Modifier.fillMaxWidth(),
//            label = {Text(label)},
//            trailingIcon = {
//                Icon(icon,"contentDescription",Modifier.clickable { mExpanded = !mExpanded })
//
//            }
//        )

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
        }//DropdownMenu
    }
}
