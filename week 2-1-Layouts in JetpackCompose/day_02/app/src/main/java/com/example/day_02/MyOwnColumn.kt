package com.example.day_02

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints

@Composable
fun MyOwnColumn(
    //
    modifier: Modifier = Modifier,
    // slot
    content: @Composable () -> Unit
) {
    //
    Layout(content = content,
        modifier = modifier,
        measurePolicy = { measurables: List<Measurable>,
                          constraints: Constraints ->

            // 전체 높이
            var containerHeight = 0
            // 전체 가로
            var containerWidth = 0

            // 아이템들 가로 리스트
            val itemWidthList = IntArray(measurables.count()){ 0 }

            // measurable 즉 측정 가능한 애들을 placeable 로 변환
            val placeables : List<Placeable> = measurables.mapIndexed { index, aMeasurable ->
                // Measure each child
                val aPlaceable = aMeasurable.measure(constraints)

                containerHeight += aPlaceable.height

                // 각각 아이템의 가로를 넣는다.
                itemWidthList[index] = aPlaceable.width

                aPlaceable
            }

            // Track the y co-ord we have placed children up to
            var yPosition = 0

            // 가장 가로가 긴 아이템을 가져온다
            containerWidth = itemWidthList.maxOrNull() ?: constraints.maxWidth

            // Set the size of the layout as big as it can
            // 최종 크기
            layout(containerWidth, containerHeight) {

                // Place children in the parent layout
                placeables.forEach { placeable ->

                    // Position item on the screen
                    placeable.placeRelative(x = 0, y = yPosition)

                    // Record the y co-ord placed up to
                    yPosition += placeable.height
                }
            }
        })
}
