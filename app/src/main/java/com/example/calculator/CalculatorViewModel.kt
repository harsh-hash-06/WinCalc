package com.example.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.CalculationHistoryEntity
import com.example.data.HistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal

data class CalculatorUiState(
    val displayValue: String = "0",
    val expressionValue: String = "",
    val hasError: Boolean = false,
    val errorType: CalculatorError? = null,
    val hasMemory: Boolean = false,
    val memoryItems: List<String> = emptyList(),
    val isHistoryOpen: Boolean = false,
    val isMemoryOpen: Boolean = false,
    val isLithuanian: Boolean = false,
    val historyItems: List<CalculationHistoryEntity> = emptyList()
)

class CalculatorViewModel(
    private val historyRepository: HistoryRepository
) : ViewModel() {

    private val engine = CalculatorEngine()
    private val memoryList = mutableListOf<BigDecimal>()

    private val _uiState = MutableStateFlow(CalculatorUiState())
    val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            historyRepository.allHistory.collect { history ->
                _uiState.update { it.copy(historyItems = history) }
            }
        }
    }

    private fun updateFromEngine(engineState: CalculatorState) {
        val displayVal = if (engineState.hasError) {
            engineState.currentInput
        } else {
            engine.formatInputDisplay(engineState.currentInput, _uiState.value.isLithuanian)
        }
        _uiState.update { current ->
            current.copy(
                displayValue = displayVal,
                expressionValue = engineState.expressionText,
                hasError = engineState.hasError,
                errorType = engineState.errorType
            )
        }
        engineState.lastCompletedCalculation?.let { result ->
            viewModelScope.launch {
                historyRepository.addCalculation(result.expression, result.result)
            }
        }
    }

    fun onDigit(digit: Char) {
        updateFromEngine(engine.onDigit(digit))
    }

    fun onDecimal() {
        updateFromEngine(engine.onDecimal())
    }

    fun onBackspace() {
        updateFromEngine(engine.onBackspace())
    }

    fun onClearEntry() {
        updateFromEngine(engine.onClearEntry())
    }

    fun onClear() {
        updateFromEngine(engine.onClear())
    }

    fun onNegate() {
        updateFromEngine(engine.onNegate())
    }

    fun onBinaryOperator(operator: BinaryOperator) {
        updateFromEngine(engine.onBinaryOperator(operator))
    }

    fun onEquals() {
        updateFromEngine(engine.onEquals())
    }

    fun onPercentage() {
        updateFromEngine(engine.onPercentage())
    }

    fun onReciprocal() {
        updateFromEngine(engine.onReciprocal())
    }

    fun onSquare() {
        updateFromEngine(engine.onSquare())
    }

    fun onSquareRoot() {
        updateFromEngine(engine.onSquareRoot())
    }

    // Memory operations
    fun onMemoryClear() {
        memoryList.clear()
        syncMemoryState()
    }

    fun onMemoryRecall() {
        if (memoryList.isNotEmpty()) {
            val recalled = CalculatorEngine.formatNumber(memoryList.first())
            val state = engine.getState().copy(
                currentInput = recalled,
                isNewInput = true
            )
            engine.setState(state)
            updateFromEngine(state)
        }
    }

    fun onMemoryAdd() {
        val currentVal = CalculatorEngine.parseBigDecimal(engine.getState().currentInput) ?: BigDecimal.ZERO
        if (memoryList.isEmpty()) {
            memoryList.add(currentVal)
        } else {
            val updated = memoryList[0].add(currentVal)
            memoryList[0] = updated
        }
        syncMemoryState()
    }

    fun onMemorySubtract() {
        val currentVal = CalculatorEngine.parseBigDecimal(engine.getState().currentInput) ?: BigDecimal.ZERO
        if (memoryList.isEmpty()) {
            memoryList.add(currentVal.negate())
        } else {
            val updated = memoryList[0].subtract(currentVal)
            memoryList[0] = updated
        }
        syncMemoryState()
    }

    fun onMemoryStore() {
        val currentVal = CalculatorEngine.parseBigDecimal(engine.getState().currentInput) ?: BigDecimal.ZERO
        if (memoryList.isEmpty()) {
            memoryList.add(currentVal)
        } else {
            memoryList.add(0, currentVal)
        }
        syncMemoryState()
    }

    fun toggleMemoryView() {
        _uiState.update { it.copy(isMemoryOpen = !it.isMemoryOpen, isHistoryOpen = false) }
    }

    fun toggleHistory() {
        _uiState.update { it.copy(isHistoryOpen = !it.isHistoryOpen, isMemoryOpen = false) }
    }

    fun onHistoryItemClick(item: CalculationHistoryEntity) {
        val parsedResult = CalculatorEngine.parseBigDecimal(item.result, _uiState.value.isLithuanian) ?: BigDecimal.ZERO
        val formattedResult = engine.formatNumber(parsedResult)
        engine.loadCalculation(item.expression, formattedResult)
        val state = engine.getState().copy(
            currentInput = formattedResult,
            expressionText = item.expression,
            isNewInput = true,
            hasError = false
        )
        engine.setState(state)
        updateFromEngine(state)
        _uiState.update { it.copy(isHistoryOpen = false) }
    }

    fun clearAllHistory() {
        viewModelScope.launch {
            historyRepository.clearAll()
        }
    }

    fun deleteHistoryItem(id: Long) {
        viewModelScope.launch {
            historyRepository.deleteById(id)
        }
    }

    fun toggleLanguage() {
        val newLithuanian = !_uiState.value.isLithuanian
        engine.isLithuanian = newLithuanian
        _uiState.update { it.copy(isLithuanian = newLithuanian) }
        
        val currentVal = CalculatorEngine.parseBigDecimal(_uiState.value.displayValue)
        if (currentVal != null) {
            val formatted = engine.formatNumber(currentVal)
            _uiState.update { it.copy(displayValue = formatted) }
        }
        syncMemoryState()
    }

    private fun syncMemoryState() {
        _uiState.update {
            it.copy(
                hasMemory = memoryList.isNotEmpty(),
                memoryItems = memoryList.map { num -> engine.formatNumber(num) }
            )
        }
    }

    class Factory(private val historyRepository: HistoryRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CalculatorViewModel::class.java)) {
                return CalculatorViewModel(historyRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
