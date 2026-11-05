package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.CalculatorViewModel
import com.example.data.AppDatabase
import com.example.data.HistoryRepository
import com.example.ui.screens.CalculatorScreen
import com.example.ui.theme.WinCalcBg
import com.example.ui.theme.WinCalcTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext)
        val repository = HistoryRepository(database.historyDao())

        setContent {
            WinCalcTheme {
                val viewModel: CalculatorViewModel = viewModel(
                    factory = CalculatorViewModel.Factory(repository)
                )
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = WinCalcBg
                ) {
                    CalculatorScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WinCalcPreview() {
    WinCalcTheme {
        // Preview dummy surface
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = WinCalcBg
        ) {}
    }
}
