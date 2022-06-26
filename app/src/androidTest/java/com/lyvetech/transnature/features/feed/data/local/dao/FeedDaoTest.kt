package com.lyvetech.transnature.features.feed.data.local.dao;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.lyvetech.transnature.core.data.local.TransNatureDatabase
import com.lyvetech.transnature.fakeTrailsList
import com.lyvetech.transnature.fakeUpdatedTrailEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class FeedDaoTest {

    private lateinit var database: TransNatureDatabase
    private lateinit var systemUnderTest: FeedDao

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TransNatureDatabase::class.java
        ).allowMainThreadQueries().build()

        systemUnderTest = database.feedDao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertTrail_verifyTrailsAreNotEmpty() = runBlocking {
        systemUnderTest.insertTrails(fakeTrailsList)

        val allTrails = systemUnderTest.getAllTrails()

        assertThat(allTrails, notNullValue())
        for (i in allTrails.indices) {
            assertThat(allTrails[i].id, `is`(fakeTrailsList[i].id))
            assertThat(allTrails[i].tag, `is`(fakeTrailsList[i].tag))
            assertThat(allTrails[i].name, `is`(fakeTrailsList[i].name))
            assertThat(allTrails[i].desc, `is`(fakeTrailsList[i].desc))
            assertThat(allTrails[i].isFav, `is`(fakeTrailsList[i].isFav))
            assertThat(allTrails[i].warning, `is`(fakeTrailsList[i].warning))
            assertThat(allTrails[i].imgRefs, `is`(fakeTrailsList[i].imgRefs))
            assertThat(allTrails[i].location, `is`(fakeTrailsList[i].location))
            assertThat(allTrails[i].accession, `is`(fakeTrailsList[i].accession))
            assertThat(allTrails[i].endLatitude, `is`(fakeTrailsList[i].endLatitude))
            assertThat(allTrails[i].endLongitude, `is`(fakeTrailsList[i].endLongitude))
            assertThat(allTrails[i].startLatitude, `is`(fakeTrailsList[i].startLatitude))
            assertThat(allTrails[i].startLatitude, `is`(fakeTrailsList[i].startLatitude))
            assertThat(allTrails[i].difficultyLevel, `is`(fakeTrailsList[i].difficultyLevel))
            assertThat(allTrails[i].distanceInMeters, `is`(fakeTrailsList[i].distanceInMeters))
            assertThat(allTrails[i].peakPointInMeters, `is`(fakeTrailsList[i].peakPointInMeters))
            assertThat(
                allTrails[i].averageTimeInMillis,
                `is`(fakeTrailsList[i].averageTimeInMillis)
            )
        }
    }

    @Test
    fun deleteTrails_getTrailsReturnEmptyList() = runBlocking {
        systemUnderTest.insertTrails(fakeTrailsList)
        systemUnderTest.deleteTrails(listOf(fakeTrailsList[0].name))

        val allTrails = systemUnderTest.getAllTrails()

        assertThat(allTrails, `is`(emptyList()))
    }

    @Test
    fun updateTrail_verifyItsUpdated() = runBlocking {
        systemUnderTest.insertTrails(fakeTrailsList)
        systemUnderTest.updateTrail(fakeUpdatedTrailEntity)

        val updatedTrail = systemUnderTest.getAllTrails()[0]
        assertThat(updatedTrail.desc, `is`("Updated desc"))
    }

    @Test
    fun getFavoriteTrails() = runBlocking {
        systemUnderTest.insertTrails(fakeTrailsList)

        val favTrails = systemUnderTest.getFavoriteTrails()

        for (i in favTrails.indices) {
            assertThat(favTrails[i].isFav, `is`(true))
        }
    }
}
