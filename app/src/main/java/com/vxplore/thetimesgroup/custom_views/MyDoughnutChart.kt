package com.vxplore.thetimesgroup.custom_views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vxplore.thetimesgroup.ui.theme.DonutGreenDark
import com.vxplore.thetimesgroup.ui.theme.DonutGreenLight
import com.vxplore.thetimesgroup.ui.theme.DonutGreenMidium

@Composable
fun MyDoughnutChart(
    values: List<Float> = listOf(15f, 40f, 25f),
    colors: List<Color> = listOf(
        DonutGreenLight, DonutGreenDark, DonutGreenMidium
    ),
    legend: List<String> = listOf("ES - 350000 (20%)", "TOI - 250000 (30%)", "E.T - 500000 (40%)"),
    size: Dp = 130.dp,
    thickness: Dp = 45.dp
) {
    // Sum of all the values
    val sumOfValues = values.sum()
    // Calculate each proportion
    val proportions = values.map { it * 100 / sumOfValues }
    // Convert each proportion to angle
    val sweepAngles = proportions.map { 360 * it / 100 }

    Row(
        modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(25.dp)) {
        Box(modifier = Modifier. weight(1f,true), ) {
            Column(
                modifier = Modifier.wrapContentSize().padding(0.dp,40.dp,0.dp,0.dp),
                //  verticalArrangement = Arrangement.Center,
            ) {
                for (i in values.indices) {
                    DisplayLegend(color = colors[i], legend = legend[i])
                }
            }
        }

        Spacer(modifier = Modifier.width(32.dp))

        Box( modifier = Modifier.weight(1f,true).padding(0.dp,0.dp,7.dp,0.dp),contentAlignment = Alignment.Center) {
            Canvas(modifier = Modifier.size(size = size).align(Alignment.TopEnd)) {
                var startAngle = -90f
                for (i in values.indices) {
                    drawArc(color = colors[i],startAngle = startAngle,sweepAngle = sweepAngles[i],useCenter = false,style = Stroke(width = thickness.toPx(), cap = StrokeCap.Butt))
                    startAngle += sweepAngles[i]
                }
            }
            Column(modifier = Modifier.wrapContentSize()) {
                Text(text = "750000", modifier = Modifier.align(Alignment.CenterHorizontally),fontWeight = FontWeight.Bold,fontSize = 17.sp)
                Text(text = "Total", modifier = Modifier.align(Alignment.CenterHorizontally),color = Color.Gray,)
            }
        }//box
    }
}

@Composable
fun DisplayLegend(color: Color, legend: String) {

    Row(
        horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(12.dp).background(color = color, shape = RectangleShape) )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = legend,color = Color.Black,fontSize = 13.sp,)
    }
}