package com.vxplore.thetimesgroup.screens.dashboard_content

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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
import com.vxplore.thetimesgroup.ui.theme.*

@Composable
fun DashboardMainScreen(openDrawer: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {

        Surface(
            //  color = Color(0xFFffd7d7.toInt()),
            modifier = Modifier.weight(1f)
        ) {
            Column(

                content = {
                    // Text(text = "DashBoardMain")
                    Box(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth(),

                    ) {
                        Row(
                            // modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(5.dp)
                            modifier = Modifier
                                .padding(13.dp)
                                .wrapContentSize()
                                .align(Alignment.Center)

                        ) {

                            Column(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .weight(1.0f, true)
                                    .background(GreenLight, shape = RoundedCornerShape(5.dp))
                            ) {
                                Text(
                                    text = "Today's Total",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(10.dp, 5.dp, 0.dp, 0.dp)
                                )
                                Text(
                                    text = "3500",
                                    style = MaterialTheme.typography.h5,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                )
                                Text(
                                    text = "ES - 2000 , TOI - 1000 , E.T - 500",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 5.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .wrapContentHeight()
                                        .fillMaxWidth()
                                        .background(
                                            Color.Black,
                                            shape = RoundedCornerShape(0.dp, 0.dp, 5.dp, 5.dp)
                                        ),
                                    contentAlignment = Alignment.CenterStart,
                                ) {
                                    Text(
                                        text = "This Month 450000",
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        modifier = Modifier.padding(
                                            horizontal = 10.dp,
                                            vertical = 5.dp
                                        )
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(width = 10.dp))


                            Column(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .weight(1.0f, true)
                                    .background(PinkLight, shape = RoundedCornerShape(5.dp))
                            ) {
                                Text(
                                    text = "Today's Return",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(10.dp, 5.dp, 0.dp, 0.dp)
                                )
                                Text(
                                    text = "450",
                                    style = MaterialTheme.typography.h5,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                )
                                Text(
                                    text = "ES - 100 , TOI - 100 , E.T - 50",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 5.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .wrapContentHeight()
                                        .fillMaxWidth()
                                        .background(
                                            Color.Black,
                                            shape = RoundedCornerShape(0.dp, 0.dp, 5.dp, 5.dp)
                                        ),
                                    contentAlignment = Alignment.CenterStart,
                                ) {
                                    Text(
                                        text = "This Month 450",
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        modifier = Modifier.padding(
                                            horizontal = 10.dp,
                                            vertical = 5.dp
                                        )
                                    )
                                }
                            }
                        }

                    }
                    DoughnutChart1()
                }
            )
        }
    }
}

@Composable
fun DoughnutChart1(
    values: List<Float> = listOf(15f, 40f, 25f),
    colors: List<Color> = listOf(
        DonutGreenLight,
        DonutGreenDark,
        DonutGreenMidium
    ),
    legend: List<String> = listOf("ES", "TOI", "E.T"),
    size: Dp = 150.dp,
    thickness: Dp = 40.dp
) {
    // Sum of all the values
    val sumOfValues = values.sum()
    // Calculate each proportion
    val proportions = values.map { it * 100 / sumOfValues }
    // Convert each proportion to angle
    val sweepAngles = proportions.map { 360 * it / 100 }
    Row(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(25.dp)) {
        Box(modifier = Modifier.fillMaxWidth().wrapContentHeight()
//            contentAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.wrapContentSize(),
                  //  verticalArrangement = Arrangement.Center,
            ) {
                for (i in values.indices) {
                    DisplayLegend(color = colors[i], legend = legend[i])
                }
            }

            Spacer(modifier = Modifier.width(32.dp))

            Canvas(
                modifier = Modifier.size(size = size).align(Alignment.TopEnd)) {
                var startAngle = -90f
                for (i in values.indices) {
                    drawArc( color = colors[i],startAngle = startAngle, sweepAngle = sweepAngles[i],useCenter = false,style = Stroke(width = thickness.toPx(), cap = StrokeCap.Butt))
                    startAngle += sweepAngles[i]
                }
            }
        }
    }
}

@Composable
fun DisplayLegend(color: Color, legend: String) {

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(color = color, shape = RectangleShape)
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = legend,
            color = Color.Black
        )
    }
}