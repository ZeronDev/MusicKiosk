package com.example.mkiosk.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mkiosk.R

//폰트 설정(나눔고딕체)
val nanumGothic = FontFamily(
    Font(R.font.nanum_gothic, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.nanum_gothic_bold, FontWeight.Bold, FontStyle.Normal)
)

val Typography = Typography(
     titleLarge = TextStyle( //TopBar
        fontFamily = nanumGothic,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp, // 폰트크기 (sp : 안드로이드의 글자 크기 단위, dp와 똑같지만)
        lineHeight = 24.sp, //
        letterSpacing = 0.5.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = nanumGothic,
        fontWeight = FontWeight.Normal,
        fontSize = 35.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = nanumGothic,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodySmall = TextStyle(
        fontFamily = nanumGothic,
        fontWeight = FontWeight.Normal,
        fontSize = 25.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    labelLarge = TextStyle(
        fontFamily = nanumGothic,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
/* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)