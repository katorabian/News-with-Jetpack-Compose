package com.katorabian.compose_news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.katorabian.compose_news.ui.theme.ComposeNewsTheme
import com.katorabian.compose_news.ui.theme.InstagramProfileCard

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNewsTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                ) {
                    InstagramProfileCard()
                }
            }
        }
    }
}

@Preview
@Composable
fun TestText() {
    Text(
        text = buildAnnotatedString {
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Hello")
            }
            withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                append(" ")
            }
            withStyle(
                SpanStyle(
                    fontFamily = FontFamily.Cursive,
                    fontSize = 30.sp,
                    textDecoration = TextDecoration.LineThrough
                )
            ) {
                append("World")
                append("!")
            }
            append("\n OTHER WORDS")
        },
        // Накладывается на весь текст выше вставленный текст тоже
//        fontSize = 24.sp,
//        fontWeight = FontWeight.ExtraBold,
//        fontFamily = FontFamily.SansSerif,
//        textDecoration = TextDecoration.combine(
//            listOf(TextDecoration.LineThrough, TextDecoration.Underline)
//        ),

    )
}