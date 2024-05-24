package com.example.newsapp.data.repositories.remote

import android.util.Log
import androidx.room.withTransaction
import com.example.newsapp.data.model.Doc
import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.data.repositories.local.LocalRepository
import com.example.newsapp.data.repositories.local.NewsDatabase

const val TAG = "REMOTE REPOSITORY"
class RemoteRepositoryImpl(
    private val newsApiService: NewsApiService,
    private val newsDatabase: NewsDatabase,
): RemoteRepository {
    val TAG = "REMOTE REPO IMPL"
    override suspend fun getTopStories(): List<NewsArticle> {
        Log.d(TAG, "Getting articles")
        val response = newsApiService.getTopStories()
        val body = response.body()
        if(body==null){
            return emptyList()
        }
        Log.d(TAG, "Response: ${body.results.size}")
        return body.results
    }

    override suspend fun getCategoryTopStories(category: String): List<NewsArticle> {
        Log.d(TAG, "Category selected: ${category}")
        val response = when(category){
            "Arts" -> {
                newsApiService.getArtsTopStories()
            }
            "Automobiles"  ->{
                newsApiService.getAutomobilesTopStories()
            }
            "Business" -> {
                newsApiService.getBusinessTopStories()
            }
            "Fashion" ->{
                newsApiService.getFashionTopStories()
            }
            "Food" ->{
                newsApiService.getFoodTopStories()
            }
            "Health" -> {
                newsApiService.getHealthTopStories()
            }
            "Home" ->{
                newsApiService.getHomeTopStories()
            }
            "Insider" -> {
                newsApiService.getInsiderTopStories()
            }
            "Magazine" -> {
                newsApiService.getMagazineTopStories()
            }
            "Movies" -> {
                newsApiService.getMoviesTopStories()
            }
            "Opinion" -> {
                newsApiService.getOpinionTopStories()
            }
            "Politics" -> {
                newsApiService.getPoliticsTopStories()
            }
            "Real Estate" -> {
                newsApiService.getRealEstateTopStories()
            }
            "Science" -> {
                newsApiService.getScienceTopStories()
            }
            "Sports" -> {
                newsApiService.getSportsTopStories()
            }
            "Technology" -> {
                newsApiService.getTechnologyTopStories()
            }
            "Travel" -> {
                newsApiService.getTravelTopStories()
            }
            "US" -> {
                newsApiService.getUSTopStories()
            }
            "World" -> {
                newsApiService.getWorldTopStories()
            }
            else -> {
                newsApiService.getTopStories()
            }
        }
        val articles = response.body() ?: return emptyList()
        return articles.results
    }

    override suspend fun searchArticle(query: String): List<Doc> {
        var response = newsApiService.searchArticle(query = query)
        Log.d(TAG, "Response code: ${response.code()}")
        var body = response.body() ?: return emptyList()
        return body.response.docs
    }
}