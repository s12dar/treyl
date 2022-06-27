package com.lyvetech.transnature.features.track.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.lyvetech.transnature.core.data.local.TransNatureDatabase
import com.lyvetech.transnature.fakeSessionEntity
import com.lyvetech.transnature.features.tracking.data.local.TrackLocalDataSourceImpl
import com.lyvetech.transnature.features.tracking.data.local.entity.SessionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class TrackLocalDataSourceTest {

    private lateinit var database: TransNatureDatabase
    private lateinit var systemUnderTest: TrackLocalDataSourceImpl

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), TransNatureDatabase::class.java
        ).allowMainThreadQueries().build()

        systemUnderTest = TrackLocalDataSourceImpl(database.trackDao, Dispatchers.Main)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertSession_getSessionByIdAndVerifyItsInserted() = runBlocking {
        systemUnderTest.insertSession(fakeSessionEntity)

        val loaded = systemUnderTest.getSessionById(fakeSessionEntity.id.toString())

        MatcherAssert.assertThat(loaded as SessionEntity, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(loaded.id, CoreMatchers.`is`(fakeSessionEntity.id))
        MatcherAssert.assertThat(loaded.title, CoreMatchers.`is`(fakeSessionEntity.title))
        MatcherAssert.assertThat(loaded.timestamp, CoreMatchers.`is`(fakeSessionEntity.timestamp))
        MatcherAssert.assertThat(
            loaded.timeInMillis,
            CoreMatchers.`is`(fakeSessionEntity.timeInMillis)
        )
        MatcherAssert.assertThat(
            loaded.averageSpeed,
            CoreMatchers.`is`(fakeSessionEntity.averageSpeed)
        )
        MatcherAssert.assertThat(
            loaded.caloriesBurnt,
            CoreMatchers.`is`(fakeSessionEntity.caloriesBurnt)
        )
        MatcherAssert.assertThat(
            loaded.distanceInMeters,
            CoreMatchers.`is`(fakeSessionEntity.distanceInMeters)
        )
    }

    @Test
    fun deleteSessions_getSessionVerifyItEmpty() = runBlocking {
        systemUnderTest.insertSession(fakeSessionEntity)
        systemUnderTest.deleteAllSessions()

        val loaded = systemUnderTest.getAllSessions()

        MatcherAssert.assertThat(loaded, CoreMatchers.`is`(emptyList()))
    }

    @Test
    fun deleteSession_getSessionReturnNullValue() = runBlocking {
        systemUnderTest.insertSession(fakeSessionEntity)
        systemUnderTest.deleteSession(fakeSessionEntity)

        val loaded = systemUnderTest.getSessionById(fakeSessionEntity.id.toString())

        MatcherAssert.assertThat(loaded, CoreMatchers.`is`(CoreMatchers.nullValue()))
    }
}