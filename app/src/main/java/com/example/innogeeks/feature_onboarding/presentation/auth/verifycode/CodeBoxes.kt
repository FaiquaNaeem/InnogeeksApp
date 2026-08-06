package com.example.innogeeks.feature_onboarding.presentation.auth.verifycode

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.innogeeks.ui.theme.InnogeeksTheme

// Six boxes backed by ONE invisible text field stretched across them. Six real fields would
// need focus juggling for paste, backspace and autofill; one field gets all of that free.
@Composable
fun CodeBoxes(
    code: String,
    onCodeChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    length: Int = VerifyCodeState.CODE_LENGTH,
    isError: Boolean = false,
    enabled: Boolean = true
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(length) { index ->
                CodeBox(
                    char = code.getOrNull(index)?.toString() ?: "",
                    isFocused = enabled && index == code.length,
                    isError = isError
                )
            }
        }

        BasicTextField(
            // Selection pinned to the end so typing always appends, never overwrites.
            value = TextFieldValue(text = code, selection = TextRange(code.length)),
            onValueChange = { onCodeChange(it.text) },
            enabled = enabled,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            cursorBrush = androidx.compose.ui.graphics.SolidColor(Color.Transparent),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .alpha(0f)
        )
    }
}

@Composable
private fun CodeBox(char: String, isFocused: Boolean, isError: Boolean) {
    val borderColor = when {
        isError -> MaterialTheme.colorScheme.error
        isFocused -> MaterialTheme.colorScheme.secondary
        char.isNotEmpty() -> Color.White.copy(alpha = 0.45f)
        else -> Color.White.copy(alpha = 0.2f)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(width = 46.dp, height = 56.dp)
            .background(Color.White.copy(alpha = 0.06f), RoundedCornerShape(14.dp))
            .border(1.5.dp, borderColor, RoundedCornerShape(14.dp))
    ) {
        Text(
            text = char,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CodeBoxesEmptyPreview() {
    InnogeeksTheme {
        Box(Modifier.background(MaterialTheme.colorScheme.background)) {
            CodeBoxes(code = "", onCodeChange = {})
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CodeBoxesPartialPreview() {
    InnogeeksTheme {
        Box(Modifier.background(MaterialTheme.colorScheme.background)) {
            CodeBoxes(code = "012", onCodeChange = {})
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CodeBoxesErrorPreview() {
    InnogeeksTheme {
        Box(Modifier.background(MaterialTheme.colorScheme.background)) {
            CodeBoxes(code = "000000", onCodeChange = {}, isError = true)
        }
    }
}
