package com.vxplore.thetimesgroup.custom_views

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import com.vxplore.core.domain.model.Pincodes

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AddPinCodes(label: String, loading: Boolean, dataList: List<Pincodes>, onSelect: (String) -> Unit) {//need to inherit "DropDownItem" to model classes
//
//    var mExpanded by remember { mutableStateOf(false) }
//    var mSelectedText by remember { mutableStateOf("") }
//    if (mSelectedText.isEmpty()){
//        mSelectedText=label
//    }
//
//    val icon = if (mExpanded)
//        Icons.Filled.KeyboardArrowUp
//    else
//        Icons.Filled.KeyboardArrowDown
//
//
//
//    AnimatedContent(
//        targetState = loading,
//        transitionSpec = {
//            if(targetState && !initialState) {
//                upToBottom()
//            } else {
//                bottomToUp()
//            }
//        }
//    ) {
//        if (!it) {
//
//            Column() {
//                Surface(
//                    border = BorderStroke(1.dp, Color.Gray),
//                    shape = RoundedCornerShape(4.dp),
//                    //backgroundColor = Color.Yellow
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(50.dp)
//                    ) {
//
//                        Row(modifier = Modifier
//                            .clickable { mExpanded = !mExpanded }
//                            .fillMaxHeight()
//                            .weight(1f)
//                            .padding(start = 15.dp)) {
//                            Text(
//                                text = mSelectedText,
//                                //label = label,
//                                color = Color.DarkGray,
//                                fontSize = 17.sp,
//                                modifier = Modifier.align(Alignment.CenterVertically)
//                            )
//                        }
//                        Icon(icon, "contentDescription",
//                            Modifier
//                                .padding(end = 4.dp)
//                                .align(Alignment.CenterVertically)
//                                .clickable { mExpanded = !mExpanded })
//
//                    }
//                }
//
////        OutlinedTextField(
////            readOnly = true,
////            value = mSelectedText,
////            onValueChange = { mSelectedText = it },
////            modifier = Modifier.fillMaxWidth(),
////            label = {Text(label)},
////            trailingIcon = {
////                Icon(icon,"contentDescription",Modifier.clickable { mExpanded = !mExpanded })
////
////            }
////        )
////Card(
////    shape = RoundedCornerShape(10.dp),
////    elevation = 20.dp,
////    modifier = Modifier.padding(top = 30.dp, bottom = 30.dp)
////) {
//
//    DropdownMenu(
//        modifier= Modifier.height(250.dp),
//        expanded = mExpanded,
//
//        onDismissRequest = { mExpanded = false },
//    ) {
//        dataList.forEach {
//            DropdownMenuItem(onClick = {
//                mSelectedText = it.pincode
//                mExpanded = false
//                onSelect(mSelectedText)
//            }) {
//                Column{
//                    Text(text = it.pincode)
//                    Divider(color = GreyLight, thickness = 0.8.dp, modifier = Modifier.padding(top = 10.dp))
//                }
//            }
//        }
//    }//DropdownMenu
////}
//            }
//
//        } else {
//            CircularProgressIndicator(
//                modifier = Modifier
//                    .size(width = screenWidth * 0.15f, height = screenHeight * 0.15f)
//                    .padding(bottom = screenHeight * 0.05f),
//                color = GreenLight,
//                strokeWidth = 5.dp,
//            )
//        }
//    }

}
