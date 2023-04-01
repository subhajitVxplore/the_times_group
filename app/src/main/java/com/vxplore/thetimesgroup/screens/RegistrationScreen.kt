@file:Suppress("DEPRECATION")

package com.vxplore.thetimesgroup.screens

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vxplore.core.helpers.AppStore
import com.vxplore.thetimesgroup.R
import com.vxplore.thetimesgroup.custom_views.MyDropdown
import com.vxplore.thetimesgroup.custom_views.MyPinCodeDropdown
import com.vxplore.thetimesgroup.ui.theme.GreyLight
import com.vxplore.thetimesgroup.viewModels.RegisterViewModel

@Composable
fun RegistrationScreen(viewModel: RegisterViewModel = hiltViewModel()) {
    //val keyboardController = LocalSoftwareKeyboardController.current
    // val mContext = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),

        ) {
        val activity = LocalContext.current as Activity
        Image(painter = painterResource(id = R.drawable.ic_baseline_keyboard_backspace_24),
            contentDescription = "countryImage",
            modifier = Modifier.clickable {
                activity.onBackPressed()
//                activity.finishAffinity()
//                activity.finish()
            })

        Text(
            text = "Complete your registration",
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.h5,
        )
        Text(
            text = "Please fill all mandatory information",
            // style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(10.dp, 7.dp, 0.dp, 0.dp), color = Color.Gray
        )

        OutlinedTextField(
            value = viewModel.yourNameText.value,
            onValueChange = { viewModel.yourNameText.value = it },
            label = { Text("Your Name") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Gray, unfocusedBorderColor = Color.Gray
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = viewModel.emailAddressText.value,
            onValueChange = { viewModel.emailAddressText.value = it },
            label = { Text("Email Address") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Gray, unfocusedBorderColor = Color.Gray
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Email),
            )
//        OutlinedTextField(
//            value = viewModel.mobileText.value,
//            onValueChange = { viewModel.mobileText.value = it },
//            label = { Text("Mobile No.") },
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                focusedBorderColor = Color.Gray, unfocusedBorderColor = Color.Gray
//            ),
//            modifier = Modifier.fillMaxWidth(),
//            singleLine = true,
//            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Email),
//            )
        OutlinedTextField(
            value = viewModel.addressText.value,
            onValueChange = { viewModel.addressText.value = it },
            label = { Text("Address") },
            colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Color.Gray, unfocusedBorderColor = Color.Gray),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
//        OutlinedTextField(
//            value = viewModel.pincodeText.value,
//            onValueChange = { viewModel.pincodeText.value = it },
//            label = { Text("Pincode") },
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                focusedBorderColor = Color.Gray,
//                unfocusedBorderColor = Color.Gray
//            ),
//            modifier = Modifier.fillMaxWidth(),
//            singleLine = true,
//            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
//        )
//        OutlinedTextField(
//            value = viewModel.districtText.value,
//            onValueChange = { viewModel.districtText.value = it },
//            label = { Text("District") },
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                focusedBorderColor = Color.Gray,
//                unfocusedBorderColor = Color.Gray
//            ),
//            modifier = Modifier.fillMaxWidth(),
//            singleLine = true
//        )
//        OutlinedTextField(
//            value = viewModel.stateText.value,
//            onValueChange = { viewModel.stateText.value = it },
//            label = { Text("State") },
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                focusedBorderColor = Color.Gray,
//                unfocusedBorderColor = Color.Gray
//            ),
//            modifier = Modifier.width(200.dp),
//            singleLine = true
//        )

        val context = LocalContext.current

        Spacer(modifier = Modifier.height(7.dp))
//        Card(
//            shape = RoundedCornerShape(10.dp),
//            elevation=20.dp,
//            modifier = Modifier.fillMaxWidth().padding(top = 30.dp, bottom = 30.dp)
//        ) {
            MyDropdown("State",
                viewModel.stateLoading.value,
                viewModel.states.collectAsState().value,
                onSelect = {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    viewModel.selectedState.value = it
                    viewModel.getDistrictByState(it)
                })

//        }


        Spacer(modifier = Modifier.height(7.dp))
        MyDropdown("District",
            viewModel.districtLoading.value,
            viewModel.districts.collectAsState().value,
            onSelect = {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                viewModel.selectedDistrict.value = it
                viewModel.getPincodeByDistrict(it)
            })

        Spacer(modifier = Modifier.height(7.dp))
//        Row(modifier = Modifier.width(200.dp)) {
            MyPinCodeDropdown("Pincode",
                viewModel.stateLoading.value,
                viewModel.pincodes.collectAsState().value,
                onSelect = {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    viewModel.selectedPincode.value = it
                })
//        }


//        OutlinedTextField(
//            value = viewModel.pincodeText.value,
//            onValueChange = { viewModel.pincodeText.value = it },
//            label = { Text("Pincode") },
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                focusedBorderColor = Color.Gray,
//                unfocusedBorderColor = Color.Gray
//            ),
//            modifier = Modifier.width(200.dp),
//            singleLine = true
//        )

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.BottomCenter)
            ) {
                Row(
                    // modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(5.dp)
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(0.dp, 0.dp, 0.dp, 7.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_round_check_circle_24),
                        contentDescription = "check",
                        modifier = Modifier
                            .width(35.dp)
                            .height(35.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Text(
                        text = "I agree with all terms and conditions.",
                        // style = MaterialTheme.typography.h3,
                        modifier = Modifier.align(Alignment.CenterVertically), color = Color.Gray
                    )
                }


                Button(
                    onClick = {
                        Toast.makeText(context, "continue:", Toast.LENGTH_SHORT).show()
                        //onContinueClick()
                        //  navController.navigate(Routes.Settings.route + "/$counter")
                              viewModel.register()

                    },
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
                ) {
                    Text(
                        text = "Continue",
                        color = Color.White,
                        style = MaterialTheme.typography.h6,
                    )
                }

                Text(
                    text = "We respect your privacy. We'll only share informations that's important for you - no spam!",
                    // style = MaterialTheme.typography.h3,
                    modifier = Modifier
                        .padding(7.dp, 7.dp, 0.dp, 0.dp)
                        .align(Alignment.CenterHorizontally),
                    color = Color.Gray
                )
            }
        }
    }
}