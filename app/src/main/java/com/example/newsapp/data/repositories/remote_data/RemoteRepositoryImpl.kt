package com.example.newsapp.data.repositories.remote_data

import android.util.Log
import com.example.newsapp.data.apiservices.LatestNewsService
import com.example.newsapp.data.apiservices.TopHeadlineNewsService
import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.data.model.NewsCategoryItem
import com.example.newsapp.data.model.response.search.Doc
import com.example.newsapp.data.repositories.local_data.NewsDatabase
import java.time.Duration
import java.time.OffsetDateTime

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
    override suspend fun getCategoryLatestNews(category: String): List<NewsArticle> {
        Log.d(TAG, "Category selected: ${category}")
        val response = when(category) {
            "Admin" -> {
                Log.d(TAG, "Admin Latest News")
                latestNewsService.getAdminLatestNews()
            }
            "Arts" -> {
                Log.d(TAG, "Arts Latest News")
                latestNewsService.getArtsLatestNews()
            }
            "Automobiles" -> {
                Log.d(TAG, "Automobiles Latest News")
                latestNewsService.getAutomobilesLatestNews()
            }
            "Books" -> {
                Log.d(TAG, "Books Latest News")
                latestNewsService.getBooksLatestNews()
            }
            "Briefing" -> {
                Log.d(TAG, "Briefing Latest News")
                latestNewsService.getBriefingLatestNews()
            }
            "Business" -> {
                Log.d(TAG, "Business Latest News")
                latestNewsService.getBusinessLatestNews()
            }
            "Climate" -> {
                Log.d(TAG, "Climate Latest News")
                latestNewsService.getClimateLatestNews()
            }
            "Corrections" -> {
                Log.d(TAG, "Corrections Latest News")
                latestNewsService.getCorrectionsLatestNews()
            }
            "Education" -> {
                Log.d(TAG, "Education Latest News")
                latestNewsService.getEducationLatestNews()
            }
            "En español" -> {
                Log.d(TAG, "En español Latest News")
                latestNewsService.getEnEspanolLatestNews()
            }
            "Fashion" -> {
                Log.d(TAG, "Fashion Latest News")
                latestNewsService.getFashionLatestNews()
            }
            "Food" -> {
                Log.d(TAG, "Food Latest News")
                latestNewsService.getFoodLatestNews()
            }
            "Gameplay" -> {
                Log.d(TAG, "Gameplay Latest News")
                latestNewsService.getGameplayLatestNews()
            }
            "Guide" -> {
                Log.d(TAG, "Guide Latest News")
                latestNewsService.getGuideLatestNews()
            }
            "Health" -> {
                Log.d(TAG, "Health Latest News")
                latestNewsService.getHealthLatestNews()
            }
            "Home & Garden" -> {
                Log.d(TAG, "Home & Garden Latest News")
                latestNewsService.getHomeAndGardenLatestNews()
            }
            "Home Page" -> {
                Log.d(TAG, "Home Page Latest News")
                latestNewsService.getHomePageLatestNews()
            }
            "Job Market" -> {
                Log.d(TAG, "Job Market Latest News")
                latestNewsService.getJobMarketLatestNews()
            }
            "The Learning Network" -> {
                Log.d(TAG, "The Learning Network Latest News")
                latestNewsService.getTheLearningNetworkLatestNews()
            }
            "Lens" -> {
                Log.d(TAG, "Lens Latest News")
                latestNewsService.getLensLatestNews()
            }
            "Magazine" -> {
                Log.d(TAG, "Magazine Latest News")
                latestNewsService.getMagazineLatestNews()
            }
            "Movies" -> {
                Log.d(TAG, "Movies Latest News")
                latestNewsService.getMoviesLatestNews()
            }
            "Multimedia/Photos" -> {
                Log.d(TAG, "Multimedia/Photos Latest News")
                latestNewsService.getMultimediaPhotosLatestNews()
            }
            "New York" -> {
                Log.d(TAG, "New York Latest News")
                latestNewsService.getNewYorkLatestNews()
            }
            "Obituaries" -> {
                Log.d(TAG, "Obituaries Latest News")
                latestNewsService.getObituariesLatestNews()
            }
            "Opinion" -> {
                Log.d(TAG, "Opinion Latest News")
                latestNewsService.getOpinionLatestNews()
            }
            "Parenting" -> {
                Log.d(TAG, "Parenting Latest News")
                latestNewsService.getParentingLatestNews()
            }
            "Podcasts" -> {
                Log.d(TAG, "Podcasts Latest News")
                latestNewsService.getPodcastsLatestNews()
            }
            "Reader Center" -> {
                Log.d(TAG, "Reader Center Latest News")
                latestNewsService.getReaderCenterLatestNews()
            }
            "Real Estate" -> {
                Log.d(TAG, "Real Estate Latest News")
                latestNewsService.getRealEstateLatestNews()
            }
            "Smarter Living" -> {
                Log.d(TAG, "Smarter Living Latest News")
                latestNewsService.getSmarterLivingLatestNews()
            }
            "Science" -> {
                Log.d(TAG, "Science Latest News")
                latestNewsService.getScienceLatestNews()
            }
            "Sports" -> {
                Log.d(TAG, "Sports Latest News")
                latestNewsService.getSportsLatestNews()
            }
            "Style" -> {
                Log.d(TAG, "Style Latest News")
                latestNewsService.getStyleLatestNews()
            }
            "Sunday Review" -> {
                Log.d(TAG, "Sunday Review Latest News")
                latestNewsService.getSundayReviewLatestNews()
            }
            "T Brand" -> {
                Log.d(TAG, "T Brand Latest News")
                latestNewsService.getTBrandLatestNews()
            }
            "T Magazine" -> {
                Log.d(TAG, "T Magazine Latest News")
                latestNewsService.getTMagazineLatestNews()
            }
            "Technology" -> {
                Log.d(TAG, "Technology Latest News")
                latestNewsService.getTechnologyLatestNews()
            }
            "Theater" -> {
                Log.d(TAG, "Theater Latest News")
                latestNewsService.getTheaterLatestNews()
            }
            "Times Insider" -> {
                Log.d(TAG, "Times Insider Latest News")
                latestNewsService.getTimesInsiderLatestNews()
            }
            "Today’s Paper" -> {
                Log.d(TAG, "Today’s Paper Latest News")
                latestNewsService.getTodaysPaperLatestNews()
            }
            "Travel" -> {
                Log.d(TAG, "Travel Latest News")
                latestNewsService.getTravelLatestNews()
            }
            "U.S." -> {
                Log.d(TAG, "U.S. Latest News")
                latestNewsService.getUSLatestNews()
            }
            "Universal" -> {
                Log.d(TAG, "Universal Latest News")
                latestNewsService.getUniversalLatestNews()
            }
            "The Upshot" -> {
                Log.d(TAG, "The Upshot Latest News")
                latestNewsService.getTheUpshotLatestNews()
            }
            "Video" -> {
                Log.d(TAG, "Video Latest News")
                latestNewsService.getVideoLatestNews()
            }
            "The Weekly" -> {
                Log.d(TAG, "The Weekly Latest News")
                latestNewsService.getTheWeeklyLatestNews()
            }
            "Well" -> {
                Log.d(TAG, "Well Latest News")
                latestNewsService.getWellLatestNews()
            }
            "World" -> {
                Log.d(TAG, "World Latest News")
                latestNewsService.getWorldLatestNews()
            }
            "Your Money" -> {
                Log.d(TAG, "Your Money Latest News")
                latestNewsService.getYourMoneyLatestNews()
            }
            else -> {
                Log.d(TAG, "Alternative Latest News")
                latestNewsService.getBriefingLatestNews()
            }
        }

        val articles = response.body() ?: return emptyList()
        val results = articles.results.filter { it ->
            val timeDifference = getDuration(OffsetDateTime.now(), it.publishedDate)
            timeDifference <= Duration.ofHours(24)
        }
        return results
    }
    fun getDuration(currentTime: OffsetDateTime, publishedDate: String): Duration {
        val published = OffsetDateTime.parse(publishedDate)
        val timeDifference = Duration.between(published,currentTime)
        return timeDifference
    }
    override suspend fun searchArticle(query: String): List<Doc> {
        val response = topHeadlineNewsService.searchArticle(query = query)
        Log.d(TAG, "Response code: ${response.code()}")
        val body = response.body() ?: return emptyList()
        return body.response.docs
    }
    override suspend fun getLatestNews(): List<NewsArticle> {
        Log.d(TAG, "Getting Latest News articles")
        val response = latestNewsService.getLatestNews()
        Log.d(TAG, "Response ${response}")
        val body = response.body() ?: return emptyList()
        Log.d(TAG, "Response: ${body.results.size}")
        val articles = response.body() ?: return emptyList()
        articles.results.filter { it ->
            val timeDifference = getDuration(OffsetDateTime.now(), it.publishedDate)
            timeDifference <= Duration.ofHours(24)
        }
//        return body.results.map { it -> it.toNewsArticle() }
        return body.results
    }

    override suspend fun getCatgories(): List<NewsCategoryItem> {
        Log.d(TAG, "Getting News Categories")
        val response = latestNewsService.getCategoryList()
        val body = response.body() ?: return emptyList()
        Log.d(TAG, "Response: ${body.results.size}")
        return body.results.mapIndexed { index, it ->
            Log.d(TAG, "${it.section} about to be converted")
            it.toNewsCategoryItem(id = index+1)
        }
    }
}