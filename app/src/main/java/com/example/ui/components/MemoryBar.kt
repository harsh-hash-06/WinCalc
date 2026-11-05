package com.example.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.WinCalcMemoryDisabled
import com.example.ui.theme.WinCalcMemoryEnabled

@Composable
fun MemoryBar(
    hasMemory: Boolean,
    onMemoryClear: () -> Unit,
    onMemoryRecall: () -> Unit,
    onMemoryAdd: () -> Unit,
    onMemorySubtract: () -> Unit,
    onMemoryStore: () -> Unit,
    onMemoryViewToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(38.dp)
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MemoryButton(
            text = stringResource(R.string.btn_mc),
            contentDesc = stringResource(R.string.desc_mc),
            enabled = hasMemory,
            onClick = onMemoryClear,
            tag = "btn_mc",
            modifier = Modifier.weight(1f)
        )
        MemoryButton(
            text = stringResource(R.string.btn_mr),
            contentDesc = stringResource(R.string.desc_mr),
            enabled = hasMemory,
            onClick = onMemoryRecall,
            tag = "btn_mr",
            modifier = Modifier.weight(1f)
        )
        MemoryButton(
            text = stringResource(R.string.btn_m_plus),
            contentDesc = stringResource(R.string.desc_m_plus),
            enabled = true,
            onClick = onMemoryAdd,
            tag = "btn_m_plus",
            modifier = Modifier.weight(1f)
        )
        MemoryButton(
            text = stringResource(R.string.btn_m_minus),
            contentDesc = stringResource(R.string.desc_m_minus),
            enabled = true,
            onClick = onMemorySubtract,
            tag = "btn_m_minus",
            modifier = Modifier.weight(1f)
        )
        MemoryButton(
            text = stringResource(R.string.btn_ms),
            contentDesc = stringResource(R.string.desc_ms),
            enabled = true,
            onClick = onMemoryStore,
            tag = "btn_ms",
            modifier = Modifier.weight(1f)
        )
        MemoryButton(
            text = stringResource(R.string.btn_mv),
            contentDesc = stringResource(R.string.desc_mv),
            enabled = hasMemory,
            onClick = onMemoryViewToggle,
            tag = "btn_mv",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun MemoryButton(
    text: String,
    contentDesc: String,
    enabled: Boolean,
    onClick: () -> Unit,
    tag: String,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .height(36.dp)
            .semantics {
                this.contentDescription = contentDesc
            }
            .then(
                if (enabled) {
                    Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = ripple(bounded = true),
                        role = Role.Button,
                        onClick = onClick
                    )
                } else {
                    Modifier.alpha(0.38f)
                }
            )
            .testTag(tag),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (enabled) WinCalcMemoryEnabled else WinCalcMemoryDisabled,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
    }
}
