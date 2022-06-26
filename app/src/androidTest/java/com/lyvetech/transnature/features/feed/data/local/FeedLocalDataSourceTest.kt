package com.lyvetech.transnature.features.feed.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.lyvetech.transnature.core.data.local.TransNatureDatabase
import com.lyvetech.transnature.fakeTrailsList
import com.lyvetech.transnature.fakeUpdatedTrailEntity
import com.lyvetech.transnature.features.feed.data.local.dao.MainCoroutineRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class FeedLocalDataSourceTest {

    private lateinit var database: TransNatureDatabase
    private lateinit var systemUnderTest: FeedLocalDataSourceImpl

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TransNatureDatabase::class.java
        ).allowMainThreadQueries().build()

        systemUnderTest = FeedLocalDataSourceImpl(database.feedDao, Dispatchers.Main)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertTrail_verifyTrailsAreNotEmpty() = runBlocking {
        systemUnderTest.insertTrails(fakeTrailsList)

        val allTrails = systemUnderTest.getAllTrails()

        MatcherAssert.assertThat(allTrails, CoreMatchers.notNullValue())
        for (i in allTrails.indices) {
            MatcherAssert.assertThat(allTrails[i].id, `is`(fakeTrailsList[i].id))
            MatcherAssert.assertThat(allTrails[i].tag, `is`(fakeTrailsList[i].tag))
            MatcherAssert.assertThat(allTrails[i].name, `is`(fakeTrailsList[i].name))
            MatcherAssert.assertThat(allTrails[i].desc, `is`(fakeTrailsList[i].desc))
            MatcherAssert.assertThat(allTrails[i].isFav, `is`(fakeTrailsList[i].isFav))
            MatcherAssert.assertThat(allTrails[i].warning, `is`(fakeTrailsList[i].warning))
            MatcherAssert.assertThat(allTrails[i].imgRefs, `is`(fakeTrailsList[i].imgRefs))
            MatcherAssert.assertThat(allTrails[i].location, `is`(fakeTrailsList[i].location))
            MatcherAssert.assertThat(allTrails[i].accession, `is`(fakeTrailsList[i].accession))
            MatcherAssert.assertThat(allTrails[i].endLatitude, `is`(fakeTrailsList[i].endLatitude))
            MatcherAssert.assertThat(allTrails[i].endLongitude, `is`(fakeTrailsList[i].endLongitude))
            MatcherAssert.assertThat(allTrails[i].startLatitude, `is`(fakeTrailsList[i].startLatitude))
            MatcherAssert.assertThat(allTrails[i].startLatitude, `is`(fakeTrailsList[i].startLatitude))
            MatcherAssert.assertThat(allTrails[i].difficultyLevel, `is`(fakeTrailsList[i].difficultyLevel))
            MatcherAssert.assertThat(allTrails[i].distanceInMeters, `is`(fakeTrailsList[i].distanceInMeters))
            MatcherAssert.assertThat(allTrails[i].peakPointInMeters, `is`(
                fakeTrailsList[i].peakPointInMeters)
            )
            MatcherAssert.assertThat(
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

        MatcherAssert.assertThat(allTrails, `is`(emptyList()))
    }

    @Test
    fun updateTrail_verifyItsUpdated() = runBlocking {
        systemUnderTest.insertTrails(fakeTrailsList)
        systemUnderTest.updateTrail(fakeUpdatedTrailEntity)

        val updatedTrail = systemUnderTest.getAllTrails()[0]
        MatcherAssert.assertThat(updatedTrail.desc, `is`("Updated desc"))
    }

    @Test
    fun getFavoriteTrails() = runBlocking {
        systemUnderTest.insertTrails(fakeTrailsList)

        val favTrails = systemUnderTest.getFavoriteTrails()

        for (i in favTrails.indices) {
            MatcherAssert.assertThat(favTrails[i].isFav, `is`(true))
        }
    }
}