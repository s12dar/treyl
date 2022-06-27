package com.lyvetech.transnature.features.track.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.lyvetech.transnature.core.data.local.TransNatureDatabase
import com.lyvetech.transnature.fakeSessionEntity
import com.lyvetech.transnature.features.feed.data.local.dao.MainCoroutineRule
import com.lyvetech.transnature.features.tracking.data.local.dao.TrackDao
import com.lyvetech.transnature.features.tracking.data.local.entity.SessionEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TrackDaoTest {

    private lateinit var database: TransNatureDatabase
    private lateinit var systemUnderTest: TrackDao

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), TransNatureDatabase::class.java
        ).allowMainThreadQueries().build()

        systemUnderTest = database.trackDao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertSession_getSessionByIdAndVerifyItsInserted() = runBlocking {
        systemUnderTest.insertSession(fakeSessionEntity)

        val loaded = systemUnderTest.getSessionById(fakeSessionEntity.id.toString())

        assertThat(loaded as SessionEntity, notNullValue())
        assertThat(loaded.id, `is`(fakeSessionEntity.id))
        assertThat(loaded.title, `is`(fakeSessionEntity.title))
        assertThat(loaded.timestamp, `is`(fakeSessionEntity.timestamp))
        assertThat(loaded.timeInMillis, `is`(fakeSessionEntity.timeInMillis))
        assertThat(loaded.averageSpeed, `is`(fakeSessionEntity.averageSpeed))
        assertThat(loaded.caloriesBurnt, `is`(fakeSessionEntity.caloriesBurnt))
        assertThat(loaded.distanceInMeters, `is`(fakeSessionEntity.distanceInMeters))
    }

    @Test
    fun deleteSessions_getSessionVerifyItEmpty() = runBlocking {
        systemUnderTest.insertSession(fakeSessionEntity)
        systemUnderTest.deleteAllSessions()

        val loaded = systemUnderTest.getAllSessions()

        assertThat(loaded, `is`(emptyList()))
    }

    @Test
    fun deleteSession_getSessionReturnNullValue() = runBlocking {
        systemUnderTest.insertSession(fakeSessionEntity)
        systemUnderTest.deleteSession(fakeSessionEntity)

        val loaded = systemUnderTest.getSessionById(fakeSessionEntity.id.toString())

        assertThat(loaded, `is`(nullValue()))
    }
}