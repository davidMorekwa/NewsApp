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
        newsDatabase.withTransaction {
            newsDatabase.newsDao().clearArticles()
            var index = 1
            val newsArticleEntityList = body.results.map { article: NewsArticle ->
                newsDatabase.newsDao().insertArticles(article.toNewsArticleEntity())
                val media = article.multimedia
                media?.let { mediaList ->
                    val mediaEntityList = mediaList.map { it ->
                        it.toMultimediaEntity(index)
                    }
                    newsDatabase.multimediaDao().insertMultimedia(mediaEntityList)
                }
                index++
            }
//            val multimediaList = body.results.map { article: NewsArticle ->
//                article.multimedia?.get(0)?.toMultimediaEntity()!!
//            }
//            newsDatabase.multimediaDao().insertMultimedia(multimediaList)
//            Log.d(TAG, "Inserting articles into room db")
//            newsArticleEntityList.forEach { it ->
//
//
//            }
//            newsDatabase.newsDao().insertArticles(newsArticleEntityList)
        }
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
        val articles = response.body()
        if (articles == null) {
            return emptyList()
        }
        return articles.results
    }

    override suspend fun searchArticle(query: String): List<Doc> {
        var response = newsApiService.searchArticle(query = query)
        Log.d(TAG, "Response code: ${response.code()}")
        var body = response.body() ?: return emptyList()
        return body.response.docs
    }
}