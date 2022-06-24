package com.lyvetech.transnature.features.feed.data.local.dao;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.lyvetech.transnature.core.data.local.TransNatureDatabase
import com.lyvetech.transnature.fakeTrailEntity
import com.lyvetech.transnature.fakeTrailsList
import com.lyvetech.transnature.fakeUpdatedTrailEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
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
    private lateinit var dao: FeedDao

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
        dao = database.feedDao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertTrails() = runBlocking {
        dao.insertTrails(fakeTrailsList)

        val allTrails = dao.getAllTrails()

        assertThat(allTrails).contains(fakeTrailEntity)
    }

    @Test
    fun deleteTrails() = runBlocking {
        dao.insertTrails(fakeTrailsList)
        dao.deleteTrails(listOf(fakeTrailsList[0].name))

        val allTrails = dao.getAllTrails()

        assertThat(allTrails).doesNotContain(fakeTrailEntity)
    }

    @Test
    fun updateTrail() = runBlocking {
        dao.insertTrails(fakeTrailsList)
        dao.updateTrail(fakeUpdatedTrailEntity)

        val allTrails = dao.getAllTrails()

        assertThat(allTrails).doesNotContain(fakeTrailEntity)
    }

    @Test
    fun getFavoriteTrails() = runBlocking {
        dao.insertTrails(fakeTrailsList)

        val favTrails = dao.getFavoriteTrails()

        assertThat(favTrails[0].isFav).isTrue()
    }
}
