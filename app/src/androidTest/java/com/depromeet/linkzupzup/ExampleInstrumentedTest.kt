package com.depromeet.linkzupzup

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.depromeet.linkzupzup.dataSources.roomdb.LinkDAO
import com.depromeet.linkzupzup.dataSources.roomdb.LinkVO
import com.depromeet.linkzupzup.dataSources.roomdb.RoomDB
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private lateinit var roomDB : RoomDB
    private lateinit var linkDAO: LinkDAO
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.depromeet.linkzupzup", appContext.packageName)
    }

    @Before
    fun setUp(){
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.depromeet.linkzupzup", appContext.packageName)
        roomDB = RoomDB.getInstance(appContext)
        linkDAO = roomDB.linkDAO()
    }

    @After
    fun closeDb() = roomDB.close()

    @Test
    fun insertAndGetLink() = runBlocking{
        val linkVO = LinkVO("www.google.com","구글입니다")

        linkDAO.insertLink(linkVO)

        val linksFromDb = linkDAO.getLinkList()
        assertEquals(1,linksFromDb.size)
    }
}