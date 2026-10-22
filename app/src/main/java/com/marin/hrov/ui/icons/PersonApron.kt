package com.marin.hrov.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Suppress("CheckReturnValue")
public val PersonApron: ImageVector
    get() {
        if (_PersonApron != null) {
            return _PersonApron!!
        }
        _PersonApron =
            ImageVector.Builder(
                name = "person_apron",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.Companion.NonZero,
                    ) {
                        moveTo(9.18f, 10.83f)
                        quadTo(8f, 9.65f, 8f, 8f)
                        reflectiveQuadTo(9.18f, 5.18f)
                        reflectiveQuadTo(12f, 4f)
                        reflectiveQuadToRelative(2.83f, 1.18f)
                        reflectiveQuadTo(16f, 8f)
                        reflectiveQuadToRelative(-1.17f, 2.82f)
                        reflectiveQuadTo(12f, 12f)
                        reflectiveQuadTo(9.18f, 10.83f)
                        close()
                        moveTo(4f, 20f)
                        verticalLineTo(17.2f)
                        quadTo(4f, 16.35f, 4.43f, 15.64f)
                        quadTo(4.85f, 14.93f, 5.6f, 14.55f)
                        quadTo(7.1f, 13.8f, 8.71f, 13.4f)
                        reflectiveQuadTo(12f, 13f)
                        reflectiveQuadToRelative(3.29f, 0.4f)
                        reflectiveQuadToRelative(3.11f, 1.15f)
                        quadToRelative(0.75f, 0.38f, 1.18f, 1.09f)
                        reflectiveQuadTo(20f, 17.2f)
                        verticalLineTo(20f)
                        horizontalLineTo(4f)
                        close()
                        moveTo(13.41f, 9.41f)
                        quadTo(14f, 8.82f, 14f, 8f)
                        reflectiveQuadTo(13.41f, 6.59f)
                        reflectiveQuadTo(12f, 6f)
                        reflectiveQuadTo(10.59f, 6.59f)
                        quadTo(10f, 7.18f, 10f, 8f)
                        reflectiveQuadToRelative(0.59f, 1.41f)
                        reflectiveQuadTo(12f, 10f)
                        reflectiveQuadTo(13.41f, 9.41f)
                        close()
                        moveTo(16f, 15.7f)
                        verticalLineTo(18f)
                        horizontalLineToRelative(2f)
                        verticalLineTo(17.2f)
                        quadToRelative(0f, -0.27f, -0.13f, -0.5f)
                        quadTo(17.75f, 16.48f, 17.5f, 16.35f)
                        quadToRelative(-0.35f, -0.2f, -0.74f, -0.36f)
                        quadTo(16.38f, 15.83f, 16f, 15.7f)
                        close()
                        moveTo(10f, 15.18f)
                        verticalLineTo(16.5f)
                        horizontalLineToRelative(4f)
                        verticalLineTo(15.18f)
                        quadToRelative(-0.5f, -0.1f, -1f, -0.14f)
                        reflectiveQuadTo(12f, 15f)
                        reflectiveQuadToRelative(-1f, 0.04f)
                        reflectiveQuadToRelative(-1f, 0.14f)
                        close()
                        moveTo(6f, 18f)
                        horizontalLineTo(8f)
                        verticalLineTo(15.7f)
                        quadTo(7.63f, 15.83f, 7.24f, 15.99f)
                        reflectiveQuadTo(6.5f, 16.35f)
                        quadTo(6.25f, 16.48f, 6.13f, 16.7f)
                        quadTo(6f, 16.93f, 6f, 17.2f)
                        verticalLineTo(18f)
                        close()
                        moveToRelative(10f, 0f)
                        quadToRelative(-0.5f, 0f, -1f, 0f)
                        reflectiveQuadToRelative(-1f, 0f)
                        horizontalLineTo(10f)
                        quadTo(9.5f, 18f, 9f, 18f)
                        reflectiveQuadTo(8f, 18f)
                        horizontalLineToRelative(8f)
                        close()
                        moveTo(12f, 8f)
                        close()
                    }
                }
                .build()
        return _PersonApron!!
    }

private var _PersonApron: ImageVector? = null