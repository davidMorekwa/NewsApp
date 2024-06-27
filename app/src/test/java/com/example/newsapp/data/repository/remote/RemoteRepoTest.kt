package com.example.newsapp.data.repository.remote

import com.example.newsapp.data.fakeDataSource.RemoteDataSource
import com.example.newsapp.data.repositories.remote_data.RemoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class RemoteRepoTest {
    lateinit var remoteRepo: RemoteRepository

    @Before
    fun before_test(){
        remoteRepo = FakeRemoteRepo()
    }

    @Test
    fun remoteRepository_getTopStories_assertArticles(){
        runBlocking {
            val actual = remoteRepo.getTopStories()
            val expected = RemoteDataSource.newsArticles
            Assert.assertEquals(expected, actual)
        }
    }

    @Test
    fun remoteRepository_getCategoryTopStories_assertArticles(){
        runBlocking {
            val actual = remoteRepo.getCategoryTopStories("Section 1").first()
            val expected = RemoteDataSource.newsArticles.get(0)
            Assert.assertEquals(expected, actual)
        }
    }

}