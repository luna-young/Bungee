package com.dev.luna.bungee.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable

private fun borderBG(

        borderColor: String = "#1F000000",
        bgColor: String = "#FFFFFF",
        borderLeftWidth: Int = 0,
        borderTopWidth: Int = 0,
        borderRightWidth: Int = 0,
        borderBottomWidth: Int = 0

) : LayerDrawable {

    val layerDrawable = arrayOf<Drawable> (
        ColorDrawable(Color.parseColor(borderColor)), //ColorDrawable: 사각형 영역을 특정 색으로 채워주는 Drawable 클래스
        ColorDrawable(Color.parseColor(bgColor))
    ).let ( ::LayerDrawable )

    layerDrawable.setLayerInset(
            1, //원하는 drawable 객체의 인덱스
            borderLeftWidth,
            borderTopWidth,
            borderRightWidth,
            borderBottomWidth
    )
    return layerDrawable
}

fun borderLeft (
        color: String = "#1F000000",
        width: Int
) = borderBG (
        borderColor = color,
        borderLeftWidth = width
)

fun borderTop (
        color: String = "#1F000000",
        width: Int
) = borderBG (
        borderColor = color,
        borderTopWidth = width

)

fun borderRight (
        color: String = "#1F000000",
        width: Int
) = borderBG (
        borderColor = color,
        borderRightWidth = width
)


fun borderBottom (
        color: String = "#1F000000",
        width: Int
) = borderBG (
        borderColor = color,
        borderBottomWidth = width
)