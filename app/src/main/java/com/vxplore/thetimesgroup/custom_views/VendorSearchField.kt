@file:OptIn(ExperimentalAnimationApi::class)

package com.vxplore.thetimesgroup.custom_views

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vxplore.thetimesgroup.R
import com.vxplore.thetimesgroup.viewModels.BillingScreenViewModel

//class MySearchField {}

@Composable
fun VendorSearchField(viewModel: BillingScreenViewModel) {
    Row(modifier = Modifier.fillMaxWidth()) {
        SearchPrescriptionSection(viewModel)
    }
}

@Composable
private fun RowScope.SearchPrescriptionSection(viewModel: BillingScreenViewModel) {

    val maxWidth = LocalConfiguration.current.screenWidthDp.dp

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
        Column(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = viewModel.searchVendorQuery,
                onValueChange = {
                    viewModel.updatePrescriptionQuery(it)
                },
                singleLine = true,
                trailingIcon = {
                    if (viewModel.searchVendorQuery.isNotEmpty()) {
                        Icon(painter = painterResource(id = R.drawable.ic_baseline_close_24),
                            contentDescription = null, modifier = Modifier.clickable {
                                viewModel.clearPrescriptionQuery()
                            })
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = null
                        )
                    }
                },
                placeholder = {
                    Text(text = "Search Vendor")
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth().padding(start = 15.dp, end = 15.dp)
            )
        }
        //PrescriptionDropDownSection(viewModel)

    }


}
