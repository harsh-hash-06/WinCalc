package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.CalculationHistoryEntity
import com.example.ui.theme.WinCalcTextPrimary
import com.example.ui.theme.WinCalcTextSecondary

@Composable
fun HistoryPanel(
    historyItems: List<CalculationHistoryEntity>,
    onItemClick: (CalculationHistoryEntity) -> Unit,
    onClearAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showConfirmDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .testTag("history_panel"),
        color = Color(0xFFF9F9F9),
        shadowElevation = 12.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // Header with Title and Clear button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.history_title),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = WinCalcTextPrimary
                )
                if (historyItems.isNotEmpty()) {
                    IconButton(
                        onClick = { showConfirmDialog = true },
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("btn_clear_history")
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = stringResource(R.string.content_desc_trash),
                            tint = WinCalcTextSecondary
                        )
                    }
                }
            }

            if (historyItems.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.history_empty),
                        color = WinCalcTextSecondary,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.testTag("history_empty_text")
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    reverseLayout = false,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(historyItems, key = { it.id }) { item ->
                        HistoryItemRow(
                            item = item,
                            onClick = { onItemClick(item) }
                        )
                    }
                }
            }
        }
    }

    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = {
                Text(
                    text = stringResource(R.string.history_title),
                    fontWeight = FontWeight.SemiBold
                )
            },
            text = {
                Text(text = stringResource(R.string.confirm_clear_history))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showConfirmDialog = false
                        onClearAll()
                    },
                    modifier = Modifier.testTag("btn_confirm_clear")
                ) {
                    Text(
                        text = stringResource(R.string.clear),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showConfirmDialog = false }
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
            }
        )
    }
}

@Composable
private fun HistoryItemRow(
    item: CalculationHistoryEntity,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp, horizontal = 14.dp)
            .testTag("history_item_${item.id}"),
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = item.expression,
            color = WinCalcTextSecondary,
            fontSize = 13.sp,
            textAlign = TextAlign.End
        )
        Text(
            text = item.result,
            color = WinCalcTextPrimary,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End
        )
    }
}
