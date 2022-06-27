package com.lyvetech.transnature.features.feed.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.lyvetech.transnature.core.data.local.TransNatureDatabase
import com.lyvetech.transnature.fakeTrailEntity
import com.lyvetech.transnature.fakeUpdatedTrailEntity
import com.lyvetech.transnature.features.feed.data.local.entity.TrailEntity
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
    fun insertTrail_getTrailByIdAndVerifyItsInserted() = runBlocking {
        systemUnderTest.insertTrails(listOf(fakeTrailEntity))

        val loaded = systemUnderTest.getTrailById(fakeTrailEntity.id.toString())

        assertThat(loaded as TrailEntity, notNullValue())
        assertThat(loaded.id, `is`(fakeTrailEntity.id))
        assertThat(loaded.tag, `is`(fakeTrailEntity.tag))
        assertThat(loaded.name, `is`(fakeTrailEntity.name))
        assertThat(loaded.desc, `is`(fakeTrailEntity.desc))
        assertThat(loaded.isFav, `is`(fakeTrailEntity.isFav))
        assertThat(loaded.warning, `is`(fakeTrailEntity.warning))
        assertThat(loaded.imgRefs, `is`(fakeTrailEntity.imgRefs))
        assertThat(loaded.location, `is`(fakeTrailEntity.location))
        assertThat(loaded.accession, `is`(fakeTrailEntity.accession))
        assertThat(loaded.endLatitude, `is`(fakeTrailEntity.endLatitude))
        assertThat(loaded.endLongitude, `is`(fakeTrailEntity.endLongitude))
        assertThat(loaded.startLatitude, `is`(fakeTrailEntity.startLatitude))
        assertThat(loaded.startLatitude, `is`(fakeTrailEntity.startLatitude))
        assertThat(loaded.difficultyLevel, `is`(fakeTrailEntity.difficultyLevel))
        assertThat(loaded.distanceInMeters, `is`(fakeTrailEntity.distanceInMeters))
        assertThat(loaded.peakPointInMeters, `is`(fakeTrailEntity.peakPointInMeters))
        assertThat(
            loaded.averageTimeInMillis, `is`(fakeTrailEntity.averageTimeInMillis)
        )
    }

    @Test
    fun deleteTrails_getTrailsVerifyItsEmpty() = runBlocking {
        systemUnderTest.insertTrails(listOf(fakeTrailEntity))
        systemUnderTest.deleteTrails(listOf(fakeTrailEntity.name))

        val allTrails = systemUnderTest.getAllTrails()

        assertThat(allTrails, `is`(emptyList()))
    }

    @Test
    fun updateTrail_getByIdAndVerifyItsUpdated() = runBlocking {
        systemUnderTest.insertTrails(listOf(fakeTrailEntity))
        systemUnderTest.updateTrail(fakeUpdatedTrailEntity)

        val loaded = systemUnderTest.getTrailById(fakeTrailEntity.id.toString())
        assertThat(loaded as TrailEntity, notNullValue())
        assertThat(loaded.desc, `is`("Updated desc"))
    }

    @Test
    fun getFavoriteTrails() = runBlocking {
        systemUnderTest.insertTrails(listOf(fakeTrailEntity))

        val favTrails = systemUnderTest.getFavoriteTrails()

        for (i in favTrails.indices) {
            assertThat(favTrails[i].isFav, `is`(true))
        }
    }
}
