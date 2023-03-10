package com.hbaez.imagereveal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val kermit = ImageBitmap.imageResource(id = R.drawable.kermit)

            var circleCenter by remember {
                mutableStateOf(Offset.Zero)
            }
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(true) {
                        detectDragGestures { change, dragAmount ->
                            circleCenter = Offset(
                                x = change.position.x.coerceIn(
                                    minimumValue = 0f,
                                    maximumValue = this.size.width.toFloat()
                                ),
                                y = change.position.y.coerceIn(
                                    minimumValue = 800f,
                                    maximumValue = 800f + (500 * (kermit.width.toFloat() / kermit.height))
                                )
                            )
                        }
                    }
            ) {
                val circlePath = Path().apply {
                    addArc(
                        oval = Rect(circleCenter, 200f),
                        startAngleDegrees = 0f,
                        sweepAngleDegrees = 360f
                    )
                }
                drawImage(
                    image = kermit,
                    dstOffset = IntOffset(0, 800),
                    dstSize = IntSize(
                        this.size.width.toInt(),
                        (500 * (kermit.width.toFloat() / kermit.height)).toInt()
                    ),
                    colorFilter = ColorFilter.tint(Color.Black, BlendMode.Color)
                )
                clipPath(
                    path = circlePath,
                    clipOp = ClipOp.Intersect
                ) {
                    drawImage(
                        image = kermit,
                        dstOffset = IntOffset(0, 800),
                        dstSize = IntSize(
                            this.size.width.toInt(),
                            (500 * (kermit.width.toFloat() / kermit.height)).toInt()
                        ),
//                        blendMode = BlendMode.Overlay
                    )
                }
            }
        }
    }
}