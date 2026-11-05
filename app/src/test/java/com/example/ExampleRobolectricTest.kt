package com.example

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.AppDatabase
import com.example.data.CalculationHistoryEntity
import com.example.data.HistoryRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    private lateinit var db: AppDatabase
    private lateinit var repository: HistoryRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        repository = HistoryRepository(db.historyDao())
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun testStringResources_AppName() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("WinCalc", appName)
    }

    @Test
    fun testRoomDatabase_InsertAndRetrieveHistory() = runBlocking {
        repository.addCalculation("12 + 8 =", "20")
        repository.addCalculation("1/( 8 )", "0.125")

        val list = repository.allHistory.first()
        assertEquals(2, list.size)
        // Most recent first
        assertEquals("0.125", list[0].result)
        assertEquals("20", list[1].result)
    }

    @Test
    fun testRoomDatabase_ClearHistory() = runBlocking {
        repository.addCalculation("12 + 8 =", "20")
        repository.clearAll()

        val list = repository.allHistory.first()
        assertTrue(list.isEmpty())
    }
}
