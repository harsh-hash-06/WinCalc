package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.calculator.BinaryOperator
import com.example.ui.theme.WinCalcBlue
import com.example.ui.theme.WinCalcBorder
import com.example.ui.theme.WinCalcNumBtn
import com.example.ui.theme.WinCalcOpBtn
import com.example.ui.theme.WinCalcTextPrimary

sealed class KeypadAction {
    data class Digit(val char: Char) : KeypadAction()
    data object Decimal : KeypadAction()
    data object Backspace : KeypadAction()
    data object ClearEntry : KeypadAction()
    data object ClearAll : KeypadAction()
    data object Negate : KeypadAction()
    data class Operator(val op: BinaryOperator) : KeypadAction()
    data object Equals : KeypadAction()
    data object Percentage : KeypadAction()
    data object Reciprocal : KeypadAction()
    data object Square : KeypadAction()
    data object SquareRoot : KeypadAction()
}

@Composable
fun CalculatorKeypad(
    onAction: (KeypadAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 2.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        // Row 1: % | CE | C | Backspace
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            CalcButton(
                text = stringResource(R.string.btn_percent),
                contentDesc = stringResource(R.string.desc_percent),
                isNumber = false,
                isEquals = false,
                tag = "btn_percent",
                onClick = { onAction(KeypadAction.Percentage) },
                modifier = Modifier.weight(1f)
            )
            CalcButton(
                text = stringResource(R.string.btn_ce),
                contentDesc = stringResource(R.string.desc_ce),
                isNumber = false,
                isEquals = false,
                tag = "btn_ce",
                onClick = { onAction(KeypadAction.ClearEntry) },
                modifier = Modifier.weight(1f)
            )
            CalcButton(
                text = stringResource(R.string.btn_c),
                contentDesc = stringResource(R.string.desc_c),
                isNumber = false,
                isEquals = false,
                tag = "btn_c",
                onClick = { onAction(KeypadAction.ClearAll) },
                modifier = Modifier.weight(1f)
            )
            CalcButton(
                text = stringResource(R.string.btn_backspace),
                contentDesc = stringResource(R.string.desc_backspace),
                isNumber = false,
                isEquals = false,
                tag = "btn_backspace",
                onClick = { onAction(KeypadAction.Backspace) },
                modifier = Modifier.weight(1f)
            )
        }

        // Row 2: 1/x | x² | √x | ÷
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            CalcButton(
                text = stringResource(R.string.btn_reciprocal),
                contentDesc = stringResource(R.string.desc_reciprocal),
                isNumber = false,
                isEquals = false,
                tag = "btn_reciprocal",
                onClick = { onAction(KeypadAction.Reciprocal) },
                modifier = Modifier.weight(1f)
            )
            CalcButton(
                text = stringResource(R.string.btn_square),
                contentDesc = stringResource(R.string.desc_square),
                isNumber = false,
                isEquals = false,
                tag = "btn_square",
                onClick = { onAction(KeypadAction.Square) },
                modifier = Modifier.weight(1f)
            )
            CalcButton(
                text = stringResource(R.string.btn_sqrt),
                contentDesc = stringResource(R.string.desc_sqrt),
                isNumber = false,
                isEquals = false,
                tag = "btn_sqrt",
                onClick = { onAction(KeypadAction.SquareRoot) },
                modifier = Modifier.weight(1f)
            )
            CalcButton(
                text = stringResource(R.string.btn_divide),
                contentDesc = stringResource(R.string.desc_divide),
                isNumber = false,
                isEquals = false,
                tag = "btn_divide",
                onClick = { onAction(KeypadAction.Operator(BinaryOperator.DIVIDE)) },
                modifier = Modifier.weight(1f)
            )
        }

        // Row 3: 7 | 8 | 9 | ×
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            CalcButton(
                text = "7",
                contentDesc = stringResource(R.string.desc_digit, "7"),
                isNumber = true,
                isEquals = false,
                tag = "btn_7",
                onClick = { onAction(KeypadAction.Digit('7')) },
                modifier = Modifier.weight(1f)
            )
            CalcButton(
                text = "8",
                contentDesc = stringResource(R.string.desc_digit, "8"),
                isNumber = true,
                isEquals = false,
                tag = "btn_8",
                onClick = { onAction(KeypadAction.Digit('8')) },
                modifier = Modifier.weight(1f)
            )
            CalcButton(
                text = "9",
                contentDesc = stringResource(R.string.desc_digit, "9"),
                isNumber = true,
                isEquals = false,
                tag = "btn_9",
                onClick = { onAction(KeypadAction.Digit('9')) },
                modifier = Modifier.weight(1f)
            )
            CalcButton(
                text = stringResource(R.string.btn_multiply),
                contentDesc = stringResource(R.string.desc_multiply),
                isNumber = false,
                isEquals = false,
                tag = "btn_multiply",
                onClick = { onAction(KeypadAction.Operator(BinaryOperator.MULTIPLY)) },
                modifier = Modifier.weight(1f)
            )
        }

        // Row 4: 4 | 5 | 6 | −
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            CalcButton(
                text = "4",
                contentDesc = stringResource(R.string.desc_digit, "4"),
                isNumber = true,
                isEquals = false,
                tag = "btn_4",
                onClick = { onAction(KeypadAction.Digit('4')) },
                modifier = Modifier.weight(1f)
            )
            CalcButton(
                text = "5",
                contentDesc = stringResource(R.string.desc_digit, "5"),
                isNumber = true,
                isEquals = false,
                tag = "btn_5",
                onClick = { onAction(KeypadAction.Digit('5')) },
                modifier = Modifier.weight(1f)
            )
            CalcButton(
                text = "6",
                contentDesc = stringResource(R.string.desc_digit, "6"),
                isNumber = true,
                isEquals = false,
                tag = "btn_6",
                onClick = { onAction(KeypadAction.Digit('6')) },
                modifier = Modifier.weight(1f)
            )
            CalcButton(
                text = stringResource(R.string.btn_subtract),
                contentDesc = stringResource(R.string.desc_subtract),
                isNumber = false,
                isEquals = false,
                tag = "btn_subtract",
                onClick = { onAction(KeypadAction.Operator(BinaryOperator.SUBTRACT)) },
                modifier = Modifier.weight(1f)
            )
        }

        // Row 5: 1 | 2 | 3 | +
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            CalcButton(
                text = "1",
                contentDesc = stringResource(R.string.desc_digit, "1"),
                isNumber = true,
                isEquals = false,
                tag = "btn_1",
                onClick = { onAction(KeypadAction.Digit('1')) },
                modifier = Modifier.weight(1f)
            )
            CalcButton(
                text = "2",
                contentDesc = stringResource(R.string.desc_digit, "2"),
                isNumber = true,
                isEquals = false,
                tag = "btn_2",
                onClick = { onAction(KeypadAction.Digit('2')) },
                modifier = Modifier.weight(1f)
            )
            CalcButton(
                text = "3",
                contentDesc = stringResource(R.string.desc_digit, "3"),
                isNumber = true,
                isEquals = false,
                tag = "btn_3",
                onClick = { onAction(KeypadAction.Digit('3')) },
                modifier = Modifier.weight(1f)
            )
            CalcButton(
                text = stringResource(R.string.btn_add),
                contentDesc = stringResource(R.string.desc_add),
                isNumber = false,
                isEquals = false,
                tag = "btn_add",
                onClick = { onAction(KeypadAction.Operator(BinaryOperator.ADD)) },
                modifier = Modifier.weight(1f)
            )
        }

        // Row 6: +/− | 0 | . | =
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            CalcButton(
                text = stringResource(R.string.btn_negate),
                contentDesc = stringResource(R.string.desc_negate),
                isNumber = true,
                isEquals = false,
                tag = "btn_negate",
                onClick = { onAction(KeypadAction.Negate) },
                modifier = Modifier.weight(1f)
            )
            CalcButton(
                text = "0",
                contentDesc = stringResource(R.string.desc_digit, "0"),
                isNumber = true,
                isEquals = false,
                tag = "btn_0",
                onClick = { onAction(KeypadAction.Digit('0')) },
                modifier = Modifier.weight(1f)
            )
            CalcButton(
                text = stringResource(R.string.btn_decimal),
                contentDesc = stringResource(R.string.desc_decimal),
                isNumber = true,
                isEquals = false,
                tag = "btn_decimal",
                onClick = { onAction(KeypadAction.Decimal) },
                modifier = Modifier.weight(1f)
            )
            CalcButton(
                text = stringResource(R.string.btn_equals),
                contentDesc = stringResource(R.string.desc_equals),
                isNumber = false,
                isEquals = true,
                tag = "btn_equals",
                onClick = { onAction(KeypadAction.Equals) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun CalcButton(
    text: String,
    contentDesc: String,
    isNumber: Boolean,
    isEquals: Boolean,
    tag: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor = when {
        isEquals -> WinCalcBlue
        isNumber -> WinCalcNumBtn
        else -> WinCalcOpBtn
    }

    val textColor = when {
        isEquals -> Color.White
        else -> WinCalcTextPrimary
    }

    val fontSize = when {
        isEquals -> 22.sp
        isNumber -> 20.sp
        text.length > 2 -> 16.sp
        else -> 18.sp
    }

    val fontWeight = when {
        isEquals || isNumber -> FontWeight.SemiBold
        else -> FontWeight.Normal
    }

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxHeight()
            .semantics { this.contentDescription = contentDesc }
            .testTag(tag),
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = bgColor,
            contentColor = textColor
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = if (isEquals) 1.dp else 0.5.dp,
            pressedElevation = 0.dp
        ),
        border = if (!isEquals) BorderStroke(0.8.dp, WinCalcBorder) else null,
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = fontWeight,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}
