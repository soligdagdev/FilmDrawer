package com.soligdag.filmdrawer.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.soligdag.filmdrawer.R


val josefinSans = FontFamily(
    Font(R.font.montserrat_light, FontWeight.Light),
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.montserrat_bold, FontWeight.Medium),
    Font(R.font.montserrat_bold, FontWeight.Bold)
)
//val displayMedium: TextStyle = TypographyTokens.DisplayMedium,
//val displaySmall: TextStyle = TypographyTokens.DisplaySmall,
//val headlineLarge: TextStyle = TypographyTokens.HeadlineLarge,
//val headlineMedium: TextStyle = TypographyTokens.HeadlineMedium,
//val headlineSmall: TextStyle = TypographyTokens.HeadlineSmall,
//val titleLarge: TextStyle = TypographyTokens.TitleLarge,
//val titleMedium: TextStyle = TypographyTokens.TitleMedium,
//val titleSmall: TextStyle = TypographyTokens.TitleSmall,
//val bodyLarge: TextStyle = TypographyTokens.BodyLarge,
//val bodyMedium: TextStyle = TypographyTokens.BodyMedium,
//val bodySmall: TextStyle = TypographyTokens.BodySmall,
//val labelLarge: TextStyle = TypographyTokens.LabelLarge,
//val labelMedium: TextStyle = TypographyTokens.LabelMedium,
//val labelSmall: TextStyle = TypographyTokens.LabelSmall,
// Set of Material typography styles to start with
private val defaultTypography = Typography()
val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = josefinSans),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = josefinSans),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = josefinSans),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = josefinSans),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = josefinSans),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = josefinSans),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = josefinSans),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = josefinSans),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = josefinSans),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = josefinSans),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = josefinSans),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = josefinSans),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = josefinSans),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = josefinSans),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = josefinSans)
//    bodyLarge = TextStyle(
//        fontFamily = josefinSans,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
//    )

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

val myTypo = Typography(
        bodyLarge = TextStyle(
        fontFamily = josefinSans,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
            letterSpacing = 0.sp
    )
)