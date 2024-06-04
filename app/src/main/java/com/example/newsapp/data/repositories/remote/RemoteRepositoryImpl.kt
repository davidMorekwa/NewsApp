package com.example.newsapp.data.repositories.remote

import android.util.Log
import com.example.newsapp.data.model.response.search.Doc
import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.data.repositories.local.NewsDatabase
import com.example.newsapp.data.repositories.remote.services.LatestNewsService
import com.example.newsapp.data.repositories.remote.services.TopHeadlineNewsService

const val TAG = "REMOTE REPOSITORY"
class RemoteRepositoryImpl(
    private val topHeadlineNewsService: TopHeadlineNewsService,
    private val latestNewsService: LatestNewsService,
    private val newsDatabase: NewsDatabase,
): RemoteRepository {
    val TAG = "REMOTE REPO IMPL"
    override suspend fun getTopStories(): List<NewsArticle> {
        Log.d(TAG, "Getting Top headlines articles")
        val response = topHeadlineNewsService.getTopStories()
        val body = response.body() ?: return emptyList()
        Log.d(TAG, "Response: ${body.results.size}")
        return body.results
    }
    override suspend fun getCategoryTopStories(category: String): List<NewsArticle> {
        Log.d(TAG, "Category selected: ${category}")
        val response = when(category){
            "Arts" -> {
                topHeadlineNewsService.getArtsTopStories()
            }
            "Automobiles"  ->{
                topHeadlineNewsService.getAutomobilesTopStories()
            }
            "Business" -> {
                topHeadlineNewsService.getBusinessTopStories()
            }
            "Fashion" ->{
                topHeadlineNewsService.getFashionTopStories()
            }
            "Food" ->{
                topHeadlineNewsService.getFoodTopStories()
            }
            "Health" -> {
                topHeadlineNewsService.getHealthTopStories()
            }
            "Home" ->{
                topHeadlineNewsService.getHomeTopStories()
            }
            "Insider" -> {
                topHeadlineNewsService.getInsiderTopStories()
            }
            "Magazine" -> {
                topHeadlineNewsService.getMagazineTopStories()
            }
            "Movies" -> {
                topHeadlineNewsService.getMoviesTopStories()
            }
            "Opinion" -> {
                topHeadlineNewsService.getOpinionTopStories()
            }
            "Politics" -> {
                topHeadlineNewsService.getPoliticsTopStories()
            }
            "Real Estate" -> {
                topHeadlineNewsService.getRealEstateTopStories()
            }
            "Science" -> {
                topHeadlineNewsService.getScienceTopStories()
            }
            "Sports" -> {
                topHeadlineNewsService.getSportsTopStories()
            }
            "Technology" -> {
                topHeadlineNewsService.getTechnologyTopStories()
            }
            "Travel" -> {
                topHeadlineNewsService.getTravelTopStories()
            }
            "US" -> {
                topHeadlineNewsService.getUSTopStories()
            }
            "World" -> {
                topHeadlineNewsService.getWorldTopStories()
            }
            else -> {
                topHeadlineNewsService.getTopStories()
            }
        }
        val articles = response.body() ?: return emptyList()
        return articles.results
    }
    override suspend fun searchArticle(query: String): List<Doc> {
        var response = topHeadlineNewsService.searchArticle(query = query)
        Log.d(TAG, "Response code: ${response.code()}")
        var body = response.body() ?: return emptyList()
        return body.response.docs
    }

    override suspend fun getLatestNews(): List<NewsArticle> {
        Log.d(TAG, "Getting Latest News articles")
        val response = latestNewsService.getLatestNews()
        Log.d(TAG, "Response ${response}")
        val body = response.body() ?: return emptyList()
        Log.d(TAG, "Response: ${body.results.size}")
//        return body.results.map { it -> it.toNewsArticle() }
        return body.results
    }

}