package com.example.newsapp.data.repository.remote

import android.util.Log
import com.example.newsapp.data.fakeDataSource.RemoteDataSource
import com.example.newsapp.data.model.Doc
import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.data.model.response.ApiResponse
import com.example.newsapp.data.repositories.remote.RemoteRepository
import retrofit2.Response

class FakeRemoteRepo: RemoteRepository {
    override suspend fun getTopStories(): List<NewsArticle> {
        val response = Response.success(
            200,
            ApiResponse(
                copyright = "Copyright",
                results = RemoteDataSource.newsArticles,
                last_updated = "Last update",
                num_results = RemoteDataSource.newsArticles.size,
                section = "Section",
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

    override suspend fun searchArticle(query: String): List<Doc> {
        TODO("Not yet implemented")

    }
}