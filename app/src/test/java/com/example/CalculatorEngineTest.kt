package com.example

import com.example.calculator.BinaryOperator
import com.example.calculator.CalculatorEngine
import com.example.calculator.CalculatorError
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CalculatorEngineTest {

    private lateinit var engine: CalculatorEngine

    @Before
    fun setup() {
        engine = CalculatorEngine()
    }

    @Test
    fun testAddition_12Plus8Equals20() {
        // Screenshot example: 12 + 8 = 20
        engine.onDigit('1')
        engine.onDigit('2')
        engine.onBinaryOperator(BinaryOperator.ADD)
        engine.onDigit('8')
        val state = engine.onEquals()

        assertEquals("20", state.currentInput)
        assertEquals("12 + 8 =", state.expressionText)
        assertFalse(state.hasError)
        assertNotNull(state.lastCompletedCalculation)
    }

    @Test
    fun testSubtraction_25Minus9Equals16() {
        engine.onDigit('2')
        engine.onDigit('5')
        engine.onBinaryOperator(BinaryOperator.SUBTRACT)
        engine.onDigit('9')
        val state = engine.onEquals()

        assertEquals("16", state.currentInput)
        assertEquals("25 − 9 =", state.expressionText)
    }

    @Test
    fun testMultiplication_7Times6Equals42() {
        engine.onDigit('7')
        engine.onBinaryOperator(BinaryOperator.MULTIPLY)
        engine.onDigit('6')
        val state = engine.onEquals()

        assertEquals("42", state.currentInput)
        assertEquals("7 × 6 =", state.expressionText)
    }

    @Test
    fun testDivision_100DividedBy4Equals25() {
        engine.onDigit('1')
        engine.onDigit('0')
        engine.onDigit('0')
        engine.onBinaryOperator(BinaryOperator.DIVIDE)
        engine.onDigit('4')
        val state = engine.onEquals()

        assertEquals("25", state.currentInput)
        assertEquals("100 ÷ 4 =", state.expressionText)
    }

    @Test
    fun testReciprocal_1DividedBy8Equals0125() {
        // Screenshot example: 1/( 8 ) = 0.125
        engine.onDigit('8')
        val state = engine.onReciprocal()

        assertEquals("0.125", state.currentInput)
        assertEquals("1/( 8 )", state.expressionText)
        assertFalse(state.hasError)
    }

    @Test
    fun testSquare_5SquaredEquals25() {
        engine.onDigit('5')
        val state = engine.onSquare()

        assertEquals("25", state.currentInput)
        assertEquals("sqr( 5 )", state.expressionText)
    }

    @Test
    fun testSquareRoot_144SqrtEquals12() {
        engine.onDigit('1')
        engine.onDigit('4')
        engine.onDigit('4')
        val state = engine.onSquareRoot()

        assertEquals("12", state.currentInput)
        assertEquals("√( 144 )", state.expressionText)
    }

    @Test
    fun testPercentage_200Plus10PercentEquals220() {
        // Assignment explicit test case: 200 + 10% = 220
        engine.onDigit('2')
        engine.onDigit('0')
        engine.onDigit('0')
        engine.onBinaryOperator(BinaryOperator.ADD)
        engine.onDigit('1')
        engine.onDigit('0')
        engine.onPercentage()
        val state = engine.onEquals()

        assertEquals("220", state.currentInput)
        assertEquals("200 + 20 =", state.expressionText)
    }

    @Test
    fun testPercentage_200Minus10PercentEquals180() {
        // Assignment explicit test case: 200 - 10% = 180
        engine.onDigit('2')
        engine.onDigit('0')
        engine.onDigit('0')
        engine.onBinaryOperator(BinaryOperator.SUBTRACT)
        engine.onDigit('1')
        engine.onDigit('0')
        engine.onPercentage()
        val state = engine.onEquals()

        assertEquals("180", state.currentInput)
        assertEquals("200 − 20 =", state.expressionText)
    }

    @Test
    fun testPercentage_Standalone50PercentEquals05() {
        engine.onDigit('5')
        engine.onDigit('0')
        val state = engine.onPercentage()

        assertEquals("0.5", state.currentInput)
    }

    @Test
    fun testPercentage_200Times10PercentEquals20() {
        engine.onDigit('2')
        engine.onDigit('0')
        engine.onDigit('0')
        engine.onBinaryOperator(BinaryOperator.MULTIPLY)
        engine.onDigit('1')
        engine.onDigit('0')
        val state = engine.onPercentage()

        assertEquals("20", state.currentInput)
    }

    @Test
    fun testPercentage_200Divide10PercentEquals2000() {
        engine.onDigit('2')
        engine.onDigit('0')
        engine.onDigit('0')
        engine.onBinaryOperator(BinaryOperator.DIVIDE)
        engine.onDigit('1')
        engine.onDigit('0')
        val state = engine.onPercentage()

        assertEquals("2,000", state.currentInput)
    }

    @Test
    fun testZeroDividedByZero_HandledSafely() {
        engine.onDigit('0')
        engine.onBinaryOperator(BinaryOperator.DIVIDE)
        engine.onDigit('0')
        val state = engine.onEquals()

        assertTrue(state.hasError)
        assertEquals(CalculatorError.DIVIDE_BY_ZERO, state.errorType)
    }

    @Test
    fun testLithuanianNumberFormattingAndParsing() {
        engine.isLithuanian = true
        val bd = java.math.BigDecimal("1234567.89")
        val formatted = engine.formatNumber(bd)
        assertEquals("1 234 567,89", formatted)

        val parsed = CalculatorEngine.parseBigDecimal("1 234 567,89", true)
        assertEquals(0, bd.compareTo(parsed))
    }

    @Test
    fun testMultiplicationWithThousandsSeparator() {
        // Parse "10,000" (English grouping)
        val op1 = CalculatorEngine.parseBigDecimal("10,000")!!
        assertEquals(0, java.math.BigDecimal("10000").compareTo(op1))
    }

    @Test
    fun testBackspaceAfterEquals() {
        engine.onDigit('1')
        engine.onDigit('2')
        engine.onDigit('3')
        engine.onBinaryOperator(BinaryOperator.ADD)
        engine.onDigit('1')
        engine.onEquals() // result is 124
        
        engine.onBackspace() // should remove last digit of 124 -> 12
        assertEquals("12", engine.getState().currentInput)
    }

    @Test
    fun testSignChange_TogglePositiveNegative() {
        engine.onDigit('5')
        val negated = engine.onNegate()
        assertEquals("-5", negated.currentInput)

        val restored = engine.onNegate()
        assertEquals("5", restored.currentInput)
    }

    @Test
    fun testDecimalInput_PreventMultipleDecimals() {
        engine.onDigit('3')
        engine.onDecimal()
        engine.onDigit('1')
        engine.onDecimal() // Second decimal must be ignored
        engine.onDigit('4')

        assertEquals("3.14", engine.getState().currentInput)
    }

    @Test
    fun testBackspace_RemovesLastDigit() {
        engine.onDigit('1')
        engine.onDigit('2')
        engine.onDigit('3')
        engine.onBackspace()

        assertEquals("12", engine.getState().currentInput)

        engine.onBackspace()
        assertEquals("1", engine.getState().currentInput)

        engine.onBackspace()
        assertEquals("0", engine.getState().currentInput)
    }

    @Test
    fun testClearEntry_PreservesPendingOperator() {
        engine.onDigit('5')
        engine.onBinaryOperator(BinaryOperator.ADD)
        engine.onDigit('9')
        engine.onClearEntry()

        assertEquals("0", engine.getState().currentInput)
        assertEquals(BinaryOperator.ADD, engine.getState().pendingOperator)

        engine.onDigit('3')
        val state = engine.onEquals()
        assertEquals("8", state.currentInput)
    }

    @Test
    fun testClear_ResetsEntireCalculator() {
        engine.onDigit('5')
        engine.onBinaryOperator(BinaryOperator.ADD)
        engine.onDigit('9')
        engine.onClear()

        assertEquals("0", engine.getState().currentInput)
        assertEquals("", engine.getState().expressionText)
        assertEquals(null, engine.getState().pendingOperator)
    }

    @Test
    fun testDivisionByZero_HandledSafely() {
        engine.onDigit('1')
        engine.onBinaryOperator(BinaryOperator.DIVIDE)
        engine.onDigit('0')
        val state = engine.onEquals()

        assertTrue(state.hasError)
        assertEquals(CalculatorError.DIVIDE_BY_ZERO, state.errorType)

        // Recovers on clear
        val recovered = engine.onClear()
        assertFalse(recovered.hasError)
        assertEquals("0", recovered.currentInput)
    }

    @Test
    fun testNegativeSquareRoot_HandledSafely() {
        engine.onDigit('9')
        engine.onNegate()
        val state = engine.onSquareRoot()

        assertTrue(state.hasError)
        assertEquals(CalculatorError.INVALID_INPUT, state.errorType)
    }

    @Test
    fun testRepeatedEquals_RepeatsLastOperation() {
        // 5 + 2 = 7, then = again -> 9
        engine.onDigit('5')
        engine.onBinaryOperator(BinaryOperator.ADD)
        engine.onDigit('2')
        val res1 = engine.onEquals()
        assertEquals("7", res1.currentInput)

        val res2 = engine.onEquals()
        assertEquals("9", res2.currentInput)

        val res3 = engine.onEquals()
        assertEquals("11", res3.currentInput)
    }
}
