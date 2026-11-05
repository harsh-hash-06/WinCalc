package com.example.calculator

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

enum class BinaryOperator(val symbol: String) {
    ADD("+"),
    SUBTRACT("−"),
    MULTIPLY("×"),
    DIVIDE("÷")
}

data class CalculationResult(
    val expression: String,
    val result: String
)

data class CalculatorState(
    val currentInput: String = "0",
    val expressionText: String = "",
    val firstOperand: BigDecimal? = null,
    val pendingOperator: BinaryOperator? = null,
    val isNewInput: Boolean = true,
    val hasError: Boolean = false,
    val errorType: CalculatorError? = null,
    val lastCompletedCalculation: CalculationResult? = null
)

enum class CalculatorError {
    DIVIDE_BY_ZERO,
    INVALID_INPUT,
    OVERFLOW
}

class CalculatorEngine {
    private val mathContext = MathContext(16, RoundingMode.HALF_UP)
    private var state = CalculatorState()
    var isLithuanian: Boolean = false

    fun formatNumber(value: BigDecimal): String {
        return formatNumber(value, isLithuanian)
    }

    // For repeated '=' execution (Windows Calculator behavior: pressing = repeatedly repeats last op and second operand)
    private var lastOperator: BinaryOperator? = null
    private var lastSecondOperand: BigDecimal? = null

    fun getState(): CalculatorState = state

    fun setState(newState: CalculatorState) {
        state = newState
    }

    fun onDigit(digit: Char): CalculatorState {
        if (state.hasError) {
            onClear()
        }
        val current = if (state.isNewInput) "" else state.currentInput
        val updated = if (current == "0") digit.toString() else current + digit
        state = state.copy(
            currentInput = updated,
            isNewInput = false,
            lastCompletedCalculation = null
        )
        return state
    }

    fun onDecimal(): CalculatorState {
        if (state.hasError) {
            onClear()
        }
        val current = if (state.isNewInput) "0" else state.currentInput
        val updated = if (current.contains(".")) {
            current
        } else {
            if (current.isEmpty()) "0." else "$current."
        }
        state = state.copy(
            currentInput = updated,
            isNewInput = false,
            lastCompletedCalculation = null
        )
        return state
    }

    fun onBackspace(): CalculatorState {
        if (state.hasError) {
            return state
        }
        val current = state.currentInput
        val updated = if (current.length <= 1 || (current.length == 2 && current.startsWith("-"))) {
            "0"
        } else {
            current.dropLast(1)
        }
        state = state.copy(
            currentInput = updated,
            isNewInput = false,
            lastCompletedCalculation = null
        )
        return state
    }

    fun onClearEntry(): CalculatorState {
        if (state.hasError) {
            onClear()
            return state
        }
        state = state.copy(
            currentInput = "0",
            isNewInput = true
        )
        return state
    }

    fun onClear(): CalculatorState {
        state = CalculatorState()
        lastOperator = null
        lastSecondOperand = null
        return state
    }

    fun onNegate(): CalculatorState {
        if (state.hasError) return state
        val current = parseBigDecimal(state.currentInput) ?: return state
        if (current.compareTo(BigDecimal.ZERO) == 0) return state
        val negated = current.negate()
        state = state.copy(
            currentInput = formatNumber(negated),
            lastCompletedCalculation = null
        )
        return state
    }

    fun onBinaryOperator(operator: BinaryOperator): CalculatorState {
        if (state.hasError) return state

        val currentVal = parseBigDecimal(state.currentInput) ?: BigDecimal.ZERO

        // If an operator was already pending and the user enters another number, evaluate first
        if (state.pendingOperator != null && !state.isNewInput && state.firstOperand != null) {
            try {
                val intermediate = executeOperation(state.firstOperand!!, currentVal, state.pendingOperator!!)
                val formattedIntermediate = formatNumber(intermediate)
                state = state.copy(
                    firstOperand = intermediate,
                    currentInput = formattedIntermediate,
                    pendingOperator = operator,
                    expressionText = "$formattedIntermediate ${operator.symbol}",
                    isNewInput = true
                )
            } catch (e: ArithmeticException) {
                return setError(CalculatorError.DIVIDE_BY_ZERO)
            }
        } else {
            val formatted = formatNumber(currentVal)
            state = state.copy(
                firstOperand = currentVal,
                pendingOperator = operator,
                expressionText = "$formatted ${operator.symbol}",
                isNewInput = true,
                lastCompletedCalculation = null
            )
        }
        lastOperator = null
        lastSecondOperand = null
        return state
    }

    fun onEquals(): CalculatorState {
        if (state.hasError) return state

        val currentVal = parseBigDecimal(state.currentInput) ?: BigDecimal.ZERO

        if (state.pendingOperator != null && state.firstOperand != null) {
            val op = state.pendingOperator!!
            val op1 = state.firstOperand!!
            val op2 = currentVal
            try {
                val result = executeOperation(op1, op2, op)
                val formattedResult = formatNumber(result)
                val fullExpression = "${formatNumber(op1)} ${op.symbol} ${formatNumber(op2)} ="
                lastOperator = op
                lastSecondOperand = op2
                state = state.copy(
                    currentInput = formattedResult,
                    expressionText = fullExpression,
                    firstOperand = null,
                    pendingOperator = null,
                    isNewInput = true,
                    lastCompletedCalculation = CalculationResult(fullExpression, formattedResult)
                )
            } catch (e: ArithmeticException) {
                return setError(CalculatorError.DIVIDE_BY_ZERO)
            }
        } else if (lastOperator != null && lastSecondOperand != null) {
            // Repeat last operation
            val op1 = currentVal
            val op2 = lastSecondOperand!!
            val op = lastOperator!!
            try {
                val result = executeOperation(op1, op2, op)
                val formattedResult = formatNumber(result)
                val fullExpression = "${formatNumber(op1)} ${op.symbol} ${formatNumber(op2)} ="
                state = state.copy(
                    currentInput = formattedResult,
                    expressionText = fullExpression,
                    isNewInput = true,
                    lastCompletedCalculation = CalculationResult(fullExpression, formattedResult)
                )
            } catch (e: ArithmeticException) {
                return setError(CalculatorError.DIVIDE_BY_ZERO)
            }
        } else {
            // No operation, just freeze expression
            val formatted = formatNumber(currentVal)
            state = state.copy(
                expressionText = "$formatted =",
                isNewInput = true
            )
        }
        return state
    }

    fun onPercentage(): CalculatorState {
        if (state.hasError) return state
        val currentVal = parseBigDecimal(state.currentInput) ?: BigDecimal.ZERO
        val hundred = BigDecimal(100)

        if (state.firstOperand != null && state.pendingOperator != null) {
            val op1 = state.firstOperand!!
            when (state.pendingOperator!!) {
                BinaryOperator.ADD, BinaryOperator.SUBTRACT -> {
                    val percentVal = op1.multiply(currentVal, mathContext).divide(hundred, mathContext)
                    val formattedPercent = formatNumber(percentVal)
                    state = state.copy(
                        currentInput = formattedPercent,
                        expressionText = "${formatNumber(op1)} ${state.pendingOperator!!.symbol} $formattedPercent",
                        isNewInput = false,
                        lastCompletedCalculation = null
                    )
                }
                BinaryOperator.MULTIPLY -> {
                    val factor = currentVal.divide(hundred, mathContext)
                    val result = op1.multiply(factor, mathContext)
                    val formattedResult = formatNumber(result)
                    val fullExpr = "${formatNumber(op1)} ${state.pendingOperator!!.symbol} ${formatNumber(currentVal)}% ="
                    state = state.copy(
                        currentInput = formattedResult,
                        expressionText = fullExpr,
                        firstOperand = null,
                        pendingOperator = null,
                        isNewInput = true,
                        lastCompletedCalculation = CalculationResult(fullExpr, formattedResult)
                    )
                }
                BinaryOperator.DIVIDE -> {
                    val divisor = currentVal.divide(hundred, mathContext)
                    if (divisor.compareTo(BigDecimal.ZERO) == 0) {
                        return setError(CalculatorError.DIVIDE_BY_ZERO)
                    }
                    val result = op1.divide(divisor, mathContext)
                    val formattedResult = formatNumber(result)
                    val fullExpr = "${formatNumber(op1)} ${state.pendingOperator!!.symbol} ${formatNumber(currentVal)}% ="
                    state = state.copy(
                        currentInput = formattedResult,
                        expressionText = fullExpr,
                        firstOperand = null,
                        pendingOperator = null,
                        isNewInput = true,
                        lastCompletedCalculation = CalculationResult(fullExpr, formattedResult)
                    )
                }
            }
        } else {
            // Standalone percentage: 50% = 0.5
            val percentVal = currentVal.divide(hundred, mathContext)
            val formatted = formatNumber(percentVal)
            state = state.copy(
                currentInput = formatted,
                expressionText = "$formatted",
                isNewInput = true,
                lastCompletedCalculation = null
            )
        }
        return state
    }

    fun formatInputDisplay(input: String, isLithuanian: Boolean): String {
        if (input.isEmpty()) return input
        val hasTrailingDot = input.endsWith(".") || input.endsWith(",")
        val numericPart = input.trimEnd('.', ',')
        val bd = parseBigDecimal(numericPart) ?: return input
        val formattedNumber = formatNumber(bd, isLithuanian)
        return if (hasTrailingDot) {
            val decSep = if (isLithuanian) ',' else '.'
            "$formattedNumber$decSep"
        } else {
            formattedNumber
        }
    }

    fun onReciprocal(): CalculatorState {
        if (state.hasError) return state
        val currentVal = parseBigDecimal(state.currentInput) ?: BigDecimal.ZERO
        if (currentVal.compareTo(BigDecimal.ZERO) == 0) {
            return setError(CalculatorError.DIVIDE_BY_ZERO)
        }
        try {
            val result = BigDecimal.ONE.divide(currentVal, mathContext)
            val formattedInput = formatNumber(currentVal)
            val formattedResult = formatNumber(result)
            val expr = if (state.pendingOperator != null && state.firstOperand != null) {
                "${formatNumber(state.firstOperand!!)} ${state.pendingOperator!!.symbol} 1/( $formattedInput )"
            } else {
                "1/( $formattedInput )"
            }
            state = state.copy(
                currentInput = formattedResult,
                expressionText = expr,
                isNewInput = false,
                lastCompletedCalculation = if (state.pendingOperator == null) {
                    CalculationResult("1/( $formattedInput )", formattedResult)
                } else null
            )
        } catch (e: ArithmeticException) {
            return setError(CalculatorError.DIVIDE_BY_ZERO)
        }
        return state
    }

    fun onSquare(): CalculatorState {
        if (state.hasError) return state
        val currentVal = parseBigDecimal(state.currentInput) ?: BigDecimal.ZERO
        val result = currentVal.multiply(currentVal, mathContext)
        val formattedInput = formatNumber(currentVal)
        val formattedResult = formatNumber(result)
        val expr = if (state.pendingOperator != null && state.firstOperand != null) {
            "${formatNumber(state.firstOperand!!)} ${state.pendingOperator!!.symbol} sqr( $formattedInput )"
        } else {
            "sqr( $formattedInput )"
        }
        state = state.copy(
            currentInput = formattedResult,
            expressionText = expr,
            isNewInput = false,
            lastCompletedCalculation = if (state.pendingOperator == null) {
                CalculationResult("sqr( $formattedInput )", formattedResult)
            } else null
        )
        return state
    }

    fun onSquareRoot(): CalculatorState {
        if (state.hasError) return state
        val currentVal = parseBigDecimal(state.currentInput) ?: BigDecimal.ZERO
        if (currentVal < BigDecimal.ZERO) {
            return setError(CalculatorError.INVALID_INPUT)
        }
        try {
            val result = computeSqrt(currentVal)
            val formattedInput = formatNumber(currentVal)
            val formattedResult = formatNumber(result)
            val expr = if (state.pendingOperator != null && state.firstOperand != null) {
                "${formatNumber(state.firstOperand!!)} ${state.pendingOperator!!.symbol} √( $formattedInput )"
            } else {
                "√( $formattedInput )"
            }
            state = state.copy(
                currentInput = formattedResult,
                expressionText = expr,
                isNewInput = false,
                lastCompletedCalculation = if (state.pendingOperator == null) {
                    CalculationResult("√( $formattedInput )", formattedResult)
                } else null
            )
        } catch (e: Exception) {
            return setError(CalculatorError.INVALID_INPUT)
        }
        return state
    }

    private fun computeSqrt(value: BigDecimal): BigDecimal {
        if (value.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO
        var x0 = BigDecimal(Math.sqrt(value.toDouble()))
        if (x0.compareTo(BigDecimal.ZERO) == 0) x0 = BigDecimal("0.000000000000001")
        val two = BigDecimal(2)
        for (i in 0..25) {
            val x1 = x0.add(value.divide(x0, mathContext), mathContext).divide(two, mathContext)
            if (x0.subtract(x1).abs().compareTo(BigDecimal("1e-15")) < 0) {
                return x1
            }
            x0 = x1
        }
        return x0
    }

    private fun executeOperation(op1: BigDecimal, op2: BigDecimal, op: BinaryOperator): BigDecimal {
        return when (op) {
            BinaryOperator.ADD -> op1.add(op2, mathContext)
            BinaryOperator.SUBTRACT -> op1.subtract(op2, mathContext)
            BinaryOperator.MULTIPLY -> op1.multiply(op2, mathContext)
            BinaryOperator.DIVIDE -> {
                if (op2.compareTo(BigDecimal.ZERO) == 0) {
                    throw ArithmeticException("Cannot divide by zero")
                }
                op1.divide(op2, mathContext)
            }
        }
    }

    private fun setError(error: CalculatorError): CalculatorState {
        state = state.copy(
            hasError = true,
            errorType = error,
            isNewInput = true
        )
        return state
    }

    fun loadCalculation(expression: String, result: String) {
        state = CalculatorState(
            currentInput = result,
            expressionText = expression,
            isNewInput = true,
            hasError = false
        )
    }

    companion object {
        fun parseBigDecimal(text: String, isLithuanian: Boolean = false): BigDecimal? {
            return try {
                val trimmed = text.trim()
                val cleaned = if (isLithuanian) {
                    trimmed.replace(" ", "").replace("\u00A0", "").replace(",", ".")
                } else {
                    trimmed.replace(",", "").replace(" ", "")
                }
                BigDecimal(cleaned)
            } catch (e: Exception) {
                null
            }
        }

        fun formatNumber(value: BigDecimal, isLithuanian: Boolean = false): String {
            val symbols = DecimalFormatSymbols(if (isLithuanian) Locale.forLanguageTag("lt") else Locale.US).apply {
                if (isLithuanian) {
                    groupingSeparator = ' '
                    decimalSeparator = ','
                } else {
                    groupingSeparator = ','
                    decimalSeparator = '.'
                }
            }
            val formatter = DecimalFormat("#,##0.##########", symbols).apply {
                isGroupingUsed = true
                roundingMode = RoundingMode.HALF_UP
            }
            val formatted = formatter.format(value)
            return if (formatted == "-0") "0" else formatted
        }
    }
}
