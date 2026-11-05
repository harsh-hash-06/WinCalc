package com.example.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.calculator.CalculatorError

@Composable
fun CalculatorDisplay(
    expressionText: String,
    displayText: String,
    hasError: Boolean,
    errorType: CalculatorError?,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    LaunchedEffect(expressionText) {
        scrollState.scrollTo(scrollState.maxValue)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        // Expression preview line (e.g. "12 + 8 =") with horizontal scrolling
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = expressionText,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 15.sp,
                maxLines = 1,
                softWrap = false,
                modifier = Modifier
                    .testTag("expression_display")
            )
        }

        // Large Primary Result Display
        val displayTextResolved = if (hasError && errorType != null) {
            when (errorType) {
                CalculatorError.DIVIDE_BY_ZERO -> stringResource(R.string.error_cannot_divide_by_zero)
                CalculatorError.INVALID_INPUT -> stringResource(R.string.error_invalid_input)
                CalculatorError.OVERFLOW -> stringResource(R.string.error_overflow)
            }
        } else {
            displayText
        }

        // Dynamically scale text size based on character count so long inputs do not clip
        val fontSize = when {
            displayTextResolved.length > 16 -> 28.sp
            displayTextResolved.length > 12 -> 34.sp
            displayTextResolved.length > 9 -> 40.sp
            else -> 48.sp
        }

        Text(
            text = displayTextResolved,
            color = if (hasError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
            fontSize = fontSize,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("primary_display")
        )
    }
}
