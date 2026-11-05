package com.example.data

import kotlinx.coroutines.flow.Flow

class HistoryRepository(private val historyDao: HistoryDao) {
    val allHistory: Flow<List<CalculationHistoryEntity>> = historyDao.getAllHistory()

    suspend fun addCalculation(expression: String, result: String) {
        historyDao.insertHistory(
            CalculationHistoryEntity(
                expression = expression,
                result = result,
                timestamp = System.currentTimeMillis()
            )
        )
    }

    suspend fun clearAll() {
        historyDao.clearAllHistory()
    }

    suspend fun deleteById(id: Long) {
        historyDao.deleteHistoryById(id)
    }
}
