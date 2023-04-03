package com.vxplore.thetimesgroup.custom_views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vxplore.core.common.DropDownCommonInterface
import com.vxplore.core.domain.model.SearchVendor
import com.vxplore.thetimesgroup.R
import com.vxplore.thetimesgroup.viewModels.BillingScreenViewModel

//class MySearchField {}

@Composable
fun MySearchField(viewModel: BillingScreenViewModel) {
    Row(modifier = Modifier.fillMaxWidth()) {
        SearchPrescriptionSection(viewModel)
    }
}



@Composable
private fun PrescriptionDropDownSection(dataList: List<SearchVendor>,viewModel: BillingScreenViewModel) {
    DropdownMenu(
        expanded = viewModel.expandDropDown,
        onDismissRequest = { viewModel.expandDropDown = false },
    ) {

        dataList.forEach {
            DropdownMenuItem(onClick = {
//                mSelectedText = it.name
//                viewModel.expandDropDown = false
            }){
                Column{
                Text(text = it.name)
                    }
            }

        }
//        viewModel.prescriptionSortParameters.forEachIndexed { index, selectedItem ->
//            DropdownMenuItem(onClick = { viewModel.suggestion }) {
//                Text(text = selectedItem.name)
//            }
//        }
    }
}

@Composable
private fun RowScope.SearchPrescriptionSection(viewModel: BillingScreenViewModel) {

    val maxWidth = LocalConfiguration.current.screenWidthDp.dp

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {

        Column(modifier = Modifier.width(maxWidth * 0.75f)) {
            OutlinedTextField(
                value = viewModel.prescriptionQuery,
                onValueChange = {
                    viewModel.updatePrescriptionQuery(it)
                },
                singleLine = true,
                trailingIcon = {
                    if (viewModel.prescriptionQuery.isNotEmpty()) {
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
                modifier = Modifier.fillMaxWidth()
            )


        }
        PrescriptionDropDownSection(viewModel.suggestion.collectAsState().value, viewModel)
    }


}

