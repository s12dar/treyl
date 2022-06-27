package com.lyvetech.transnature.features.feed.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.lyvetech.transnature.core.data.local.TransNatureDatabase
import com.lyvetech.transnature.fakeTrailEntity
import com.lyvetech.transnature.fakeUpdatedTrailEntity
import com.lyvetech.transnature.features.feed.data.local.dao.MainCoroutineRule
import com.lyvetech.transnature.features.feed.data.local.entity.TrailEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
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
    fun insertTrail_getTrailByIdAndVerifyItsInserted() = runBlocking {
        systemUnderTest.insertTrails(listOf(fakeTrailEntity))

        val loaded = systemUnderTest.getTrailById(fakeTrailEntity.id.toString())

        MatcherAssert.assertThat(loaded as TrailEntity, notNullValue())
        MatcherAssert.assertThat(loaded.id, `is`(fakeTrailEntity.id))
        MatcherAssert.assertThat(loaded.tag, `is`(fakeTrailEntity.tag))
        MatcherAssert.assertThat(loaded.name, `is`(fakeTrailEntity.name))
        MatcherAssert.assertThat(loaded.desc, `is`(fakeTrailEntity.desc))
        MatcherAssert.assertThat(loaded.isFav, `is`(fakeTrailEntity.isFav))
        MatcherAssert.assertThat(loaded.warning, `is`(fakeTrailEntity.warning))
        MatcherAssert.assertThat(loaded.imgRefs, `is`(fakeTrailEntity.imgRefs))
        MatcherAssert.assertThat(loaded.location, `is`(fakeTrailEntity.location))
        MatcherAssert.assertThat(loaded.accession, `is`(fakeTrailEntity.accession))
        MatcherAssert.assertThat(loaded.endLatitude, `is`(fakeTrailEntity.endLatitude))
        MatcherAssert.assertThat(loaded.endLongitude, `is`(fakeTrailEntity.endLongitude))
        MatcherAssert.assertThat(loaded.startLatitude, `is`(fakeTrailEntity.startLatitude))
        MatcherAssert.assertThat(loaded.startLatitude, `is`(fakeTrailEntity.startLatitude))
        MatcherAssert.assertThat(loaded.difficultyLevel, `is`(fakeTrailEntity.difficultyLevel))
        MatcherAssert.assertThat(loaded.distanceInMeters, `is`(fakeTrailEntity.distanceInMeters))
        MatcherAssert.assertThat(loaded.peakPointInMeters, `is`(fakeTrailEntity.peakPointInMeters))
        MatcherAssert.assertThat(
            loaded.averageTimeInMillis, `is`(fakeTrailEntity.averageTimeInMillis)
        )
    }

    @Test
    fun deleteTrails_getTrailsVerifyItsEmpty() = runBlocking {
        systemUnderTest.insertTrails(listOf(fakeTrailEntity))
        systemUnderTest.deleteTrails(listOf(fakeTrailEntity.name))

        val allTrails = systemUnderTest.getAllTrails()

        MatcherAssert.assertThat(allTrails, `is`(emptyList()))
    }

    @Test
    fun updateTrail_getByIdAndVerifyItsUpdated() = runBlocking {
        systemUnderTest.insertTrails(listOf(fakeTrailEntity))
        systemUnderTest.updateTrail(fakeUpdatedTrailEntity)

        val loaded = systemUnderTest.getTrailById(fakeTrailEntity.id.toString())
        MatcherAssert.assertThat(loaded as TrailEntity, notNullValue())
        MatcherAssert.assertThat(loaded.desc, `is`("Updated desc"))
    }

    @Test
    fun getFavoriteTrails() = runBlocking {
        systemUnderTest.insertTrails(listOf(fakeTrailEntity))

        val favTrails = systemUnderTest.getFavoriteTrails()

        for (i in favTrails.indices) {
            MatcherAssert.assertThat(favTrails[i].isFav, `is`(true))
        }
    }
}
