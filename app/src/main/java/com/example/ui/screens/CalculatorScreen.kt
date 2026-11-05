package com.example.ui.screens

import android.content.res.Configuration
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.OpenInNew
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.calculator.CalculatorViewModel
import com.example.ui.components.CalculatorDisplay
import com.example.ui.components.CalculatorKeypad
import com.example.ui.components.HistoryPanel
import com.example.ui.components.KeypadAction
import com.example.ui.components.MemoryBar
import com.example.ui.components.MemoryPanel
import com.example.ui.theme.WinCalcBg
import com.example.ui.theme.WinCalcBlue
import com.example.ui.theme.WinCalcTextPrimary
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val baseContext = LocalContext.current
    var showMenuDialog by remember { mutableStateOf(false) }

    // Support dynamic bilingual switching (English / Lithuanian)
    val targetLocale = if (uiState.isLithuanian) Locale("lt") else Locale("en")
    val localizedContext = remember(targetLocale) {
        val config = Configuration(baseContext.resources.configuration)
        config.setLocale(targetLocale)
        baseContext.createConfigurationContext(config)
    }

    CompositionLocalProvider(LocalContext provides localizedContext) {
        BackHandler(enabled = uiState.isHistoryOpen || uiState.isMemoryOpen) {
            if (uiState.isHistoryOpen) viewModel.toggleHistory()
            if (uiState.isMemoryOpen) viewModel.toggleMemoryView()
        }

        Surface(
            modifier = modifier
                .fillMaxSize()
                .background(WinCalcBg)
                .statusBarsPadding()
                .navigationBarsPadding(),
            color = WinCalcBg
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Top Header: Hamburger, "Standard" title, Keep-on-top, Language switch, History toggle
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { showMenuDialog = true },
                            modifier = Modifier
                                .size(40.dp)
                                .testTag("btn_menu")
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Menu,
                                contentDescription = stringResource(R.string.content_desc_menu),
                                tint = WinCalcTextPrimary
                            )
                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = stringResource(R.string.calc_mode_standard),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = WinCalcTextPrimary,
                            modifier = Modifier.testTag("app_title")
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        val pinMessage = stringResource(R.string.window_pinning_not_supported)
                        IconButton(
                            onClick = {
                                Toast.makeText(baseContext, pinMessage, Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier
                                .size(32.dp)
                                .testTag("btn_keep_on_top")
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.OpenInNew,
                                contentDescription = stringResource(R.string.content_desc_keep_on_top),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        // Language switch button (EN / LT)
                        TextButton(
                            onClick = { viewModel.toggleLanguage() },
                            modifier = Modifier.testTag("btn_language_switch")
                        ) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = if (uiState.isLithuanian) WinCalcBlue else Color.Transparent,
                                border = androidx.compose.foundation.BorderStroke(1.dp, WinCalcBlue)
                            ) {
                                Text(
                                    text = if (uiState.isLithuanian) "LT" else "EN",
                                    color = if (uiState.isLithuanian) Color.White else WinCalcBlue,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        // History Clock Button with badge if history items exist
                        IconButton(
                            onClick = { viewModel.toggleHistory() },
                            modifier = Modifier
                                .size(40.dp)
                                .testTag("btn_history_toggle")
                        ) {
                            if (uiState.historyItems.isNotEmpty()) {
                                BadgedBox(
                                    badge = {
                                        Badge(
                                            containerColor = WinCalcBlue,
                                            contentColor = Color.White
                                        ) {
                                            Text(text = uiState.historyItems.size.toString())
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.History,
                                        contentDescription = stringResource(R.string.content_desc_history),
                                        tint = if (uiState.isHistoryOpen) WinCalcBlue else WinCalcTextPrimary
                                    )
                                }
                            } else {
                                Icon(
                                    imageVector = Icons.Outlined.History,
                                    contentDescription = stringResource(R.string.content_desc_history),
                                    tint = if (uiState.isHistoryOpen) WinCalcBlue else WinCalcTextPrimary
                                )
                            }
                        }
                    }

                    // Expression and Primary Result Display Area
                    CalculatorDisplay(
                        expressionText = uiState.expressionValue,
                        displayText = uiState.displayValue,
                        hasError = uiState.hasError,
                        errorType = uiState.errorType,
                        modifier = Modifier.weight(0.9f)
                    )

                    // Memory Control Bar (MC, MR, M+, M-, MS, Mv)
                    MemoryBar(
                        hasMemory = uiState.hasMemory,
                        onMemoryClear = { viewModel.onMemoryClear() },
                        onMemoryRecall = { viewModel.onMemoryRecall() },
                        onMemoryAdd = { viewModel.onMemoryAdd() },
                        onMemorySubtract = { viewModel.onMemorySubtract() },
                        onMemoryStore = { viewModel.onMemoryStore() },
                        onMemoryViewToggle = { viewModel.toggleMemoryView() },
                        modifier = Modifier.padding(bottom = 2.dp)
                    )

                    // Bottom Content Area: Keypad
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(2.6f)
                    ) {
                        CalculatorKeypad(
                            onAction = { action ->
                                when (action) {
                                    is KeypadAction.Digit -> viewModel.onDigit(action.char)
                                    is KeypadAction.Decimal -> viewModel.onDecimal()
                                    is KeypadAction.Backspace -> viewModel.onBackspace()
                                    is KeypadAction.ClearEntry -> viewModel.onClearEntry()
                                    is KeypadAction.ClearAll -> viewModel.onClear()
                                    is KeypadAction.Negate -> viewModel.onNegate()
                                    is KeypadAction.Operator -> viewModel.onBinaryOperator(action.op)
                                    is KeypadAction.Equals -> viewModel.onEquals()
                                    is KeypadAction.Percentage -> viewModel.onPercentage()
                                    is KeypadAction.Reciprocal -> viewModel.onReciprocal()
                                    is KeypadAction.Square -> viewModel.onSquare()
                                    is KeypadAction.SquareRoot -> viewModel.onSquareRoot()
                                }
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                // Full-screen backdrop scrim for outside-tap dismissal
                if (uiState.isHistoryOpen || uiState.isMemoryOpen) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.32f))
                            .clickable(
                                indication = null,
                                interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
                            ) {
                                if (uiState.isHistoryOpen) viewModel.toggleHistory()
                                if (uiState.isMemoryOpen) viewModel.toggleMemoryView()
                            }
                    )
                }

                // Sliding History Panel
                androidx.compose.animation.AnimatedVisibility(
                    visible = uiState.isHistoryOpen,
                    enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                    exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .fillMaxHeight(0.75f)
                ) {
                    HistoryPanel(
                        historyItems = uiState.historyItems,
                        onItemClick = { item -> viewModel.onHistoryItemClick(item) },
                        onClearAll = { viewModel.clearAllHistory() },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // Sliding Memory View Panel
                androidx.compose.animation.AnimatedVisibility(
                    visible = uiState.isMemoryOpen,
                    enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                    exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .fillMaxHeight(0.75f)
                ) {
                    MemoryPanel(
                        memoryItems = uiState.memoryItems,
                        onItemClick = { value ->
                            val state = viewModel.uiState.value
                            viewModel.onClearEntry()
                            for (char in value) {
                                if (char == '.' || char == ',') viewModel.onDecimal()
                                else if (char in '0'..'9') viewModel.onDigit(char)
                            }
                            viewModel.toggleMemoryView()
                        },
                        onClearAll = {
                            viewModel.onMemoryClear()
                            viewModel.toggleMemoryView()
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            if (showMenuDialog) {
                AlertDialog(
                    onDismissRequest = { showMenuDialog = false },
                    title = {
                        Text(
                            text = stringResource(R.string.menu_title),
                            fontWeight = FontWeight.Bold
                        )
                    },
                    text = {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = "• " + stringResource(R.string.menu_standard) + " (" + stringResource(R.string.menu_active) + ")",
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                    Text(
                                        text = stringResource(R.string.menu_desc),
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }
                        }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = { showMenuDialog = false },
                            modifier = Modifier.testTag("btn_close_menu_dialog")
                        ) {
                            Text(text = stringResource(R.string.clear))
                        }
                    }
                )
            }
        }
    }
}
