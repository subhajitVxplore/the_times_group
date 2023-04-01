package com.vxplore.thetimesgroup.custom_views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
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
fun MySearchField(viewModel: BillingScreenViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth(.9f)
    ) {

        SearchPrescriptionSection(viewModel)
        Spacer(modifier = Modifier.width(10.dp))
        SortPrescriptionSection(viewModel)
    }
}

@Composable
fun RowScope.SortPrescriptionSection(viewModel: BillingScreenViewModel) {

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {

        Row {
            Image(painter = painterResource(id = R.drawable.ic_baseline_sort_24),
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        viewModel.openDropDown()
                    }
                    .width(20.dp)
                    .height(20.dp)
            )
        }

        PrescriptionDropDownSection(viewModel)
    }

}

@Composable
private fun PrescriptionDropDownSection(viewModel: BillingScreenViewModel) {
    DropdownMenu(
        expanded = viewModel.expandDropDown,
        onDismissRequest = { viewModel.expandDropDown = false },
    ) {
//        viewModel.prescriptionSortParameters.forEachIndexed { index, sortParameter ->
//            DropdownMenuItem(onClick = { viewModel.sortPrescriptions(sortParameter) }) {
//                Text(text = sortParameter.name)
//            }
//        }
    }
}

@Composable
private fun RowScope.SearchPrescriptionSection(viewModel: BillingScreenViewModel) {

    val maxWidth = LocalConfiguration.current.screenWidthDp.dp

    Column(modifier = Modifier.width(maxWidth * 0.75f)) {
        OutlinedTextField(
            value = "viewModel.prescriptionQuery",
            onValueChange = {
               // viewModel.updatePrescriptionQuery(it)
            },
            singleLine = true,
            trailingIcon = {
//                if (viewModel.prescriptionQuery.isNotEmpty()) {
//                    Icon(painter = painterResource(id = R.drawable.ic_baseline_close_24),
//                        contentDescription = null, modifier = Modifier.clickable {
//                            //viewModel.clearPrescriptionQuery()
//                        })
//                } else {
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_search),
//                        contentDescription = null
//                    )
//                }
            },
            placeholder = {
                Text(text = "R.string.search_by_name.string()")
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }


}

