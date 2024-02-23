//package com.example.newsapp.roomDB
//
//import android.content.Context
//import androidx.test.core.app.ApplicationProvider
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.example.newsapp.data.repositories.local.NewsArticleDatabase
//import com.example.newsapp.data.repositories.local.dao.NewsDao
//import kotlinx.coroutines.runBlocking
//import kotlinx.coroutines.test.TestCoroutineDispatcher
//import org.junit.Assert
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//
//@RunWith(AndroidJUnit4::class)
//class RoomDBTest {
//    lateinit var newsDao: NewsDao
//    lateinit var database:NewsArticleDatabase
//
//    @get:Rule
//    val testCoroutine = TestCoroutineDispatcher()
//
//    @Before
//    fun before_test(){
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        database = NewsArticleDatabase.getDatabaseInstance(context)
//        newsDao = database.newsDao
//    }
//
////    @Test
////    fun insertNewsArticles_addArticles_verifyWriteOperation(){
////        runBlocking(testCoroutine) {
////            val articles = LocalDataSource.localNewsArticleEntities
////            val res = newsDao.insertNewsArticles(articles)
////            Assert.assertEquals(LocalDataSource.localNewsArticleEntities.size.toDouble(), res.size.toDouble())
////        }
////    }
//
//
//}