package com.example.newsapp.data.repository.remote

import android.util.Log
import com.example.newsapp.data.fakeDataSource.RemoteDataSource
import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.data.model.NewsCategoryItem
import com.example.newsapp.data.model.response.search.Doc
import com.example.newsapp.data.model.response.topheadlines.ApiResponse
import com.example.newsapp.data.repositories.remote.RemoteRepository
import retrofit2.Response

class FakeRemoteRepo: RemoteRepository {
    override suspend fun getTopStories(): List<NewsArticle> {
        val response = Response.success(
            200,
            ApiResponse(
                copyright = "Copyright",
                results = RemoteDataSource.newsArticles,
                num_results = RemoteDataSource.newsArticles.size,
                status = "OK"
            )
        )
        val articles: List<NewsArticle> = if (response.isSuccessful){
            val body = response.body()
            return body?.results!!
        } else {
            Log.e("FAKE REMOTE REPO", response.errorBody().toString())
            emptyList()
        }
        return articles
    }

    override suspend fun getCategoryTopStories(category: String): List<NewsArticle> {
        return RemoteDataSource.newsArticles.filter { article: NewsArticle ->
            article.section == category
        }
    }

    override suspend fun getCategoryLatestNews(category: String): List<NewsArticle> {
        TODO("Not yet implemented")
    }

    override suspend fun searchArticle(query: String): List<Doc> {
        TODO("Not yet implemented")

    }

    override suspend fun getLatestNews(): List<NewsArticle> {
        TODO("Not yet implemented")
    }

    override suspend fun getCatgories(): List<NewsCategoryItem> {
        TODO("Not yet implemented")
    }
}