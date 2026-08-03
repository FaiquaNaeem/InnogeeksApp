package com.example.innogeeks.feature_home.presentation.home.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.innogeeks.ui.theme.InnogeeksTheme

@Composable
fun HomeHero(modifier: Modifier = Modifier) {
    // buildAnnotatedString lets one Text carry two colours in a single line.
    val headline: AnnotatedString = buildAnnotatedString {
        append("BUILD ")
        withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
            append("THE FUTURE")
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
    ) {
        Text(
            text = headline,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            lineHeight = 34.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "Every idea starts as a sketch, and every sketch gets pulled into something real.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeHeroPreview() {
    InnogeeksTheme {
        HomeHero()
    }
}
