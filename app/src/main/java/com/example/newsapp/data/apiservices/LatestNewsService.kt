package com.example.newsapp.data.apiservices

import com.example.newsapp.data.model.response.category.CategoryResponse
import com.example.newsapp.data.model.response.latestnews.LatestNewsResponse
import com.example.newsapp.data.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LatestNewsService {
    @GET("all.json")
    suspend fun getLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>
    @GET("admin.json")
    suspend fun getAdminLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("arts.json")
    suspend fun getArtsLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("automobiles.json")
    suspend fun getAutomobilesLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("books.json")
    suspend fun getBooksLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("briefing.json")
    suspend fun getBriefingLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("business.json")
    suspend fun getBusinessLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("climate.json")
    suspend fun getClimateLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("corrections.json")
    suspend fun getCorrectionsLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("education.json")
    suspend fun getEducationLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("en-espanol.json")
    suspend fun getEnEspanolLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("fashion.json")
    suspend fun getFashionLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("food.json")
    suspend fun getFoodLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("gameplay.json")
    suspend fun getGameplayLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("guide.json")
    suspend fun getGuideLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("health.json")
    suspend fun getHealthLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("home-and-garden.json")
    suspend fun getHomeAndGardenLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("home-page.json")
    suspend fun getHomePageLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("job-market.json")
    suspend fun getJobMarketLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("the-learning-network.json")
    suspend fun getTheLearningNetworkLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("lens.json")
    suspend fun getLensLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("magazine.json")
    suspend fun getMagazineLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("movies.json")
    suspend fun getMoviesLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("multimedia-photos.json")
    suspend fun getMultimediaPhotosLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("new-york.json")
    suspend fun getNewYorkLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("obituaries.json")
    suspend fun getObituariesLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("opinion.json")
    suspend fun getOpinionLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("parenting.json")
    suspend fun getParentingLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("podcasts.json")
    suspend fun getPodcastsLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("reader-center.json")
    suspend fun getReaderCenterLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("real-estate.json")
    suspend fun getRealEstateLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("smarter-living.json")
    suspend fun getSmarterLivingLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("science.json")
    suspend fun getScienceLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("sports.json")
    suspend fun getSportsLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("style.json")
    suspend fun getStyleLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("sunday-review.json")
    suspend fun getSundayReviewLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("t-brand.json")
    suspend fun getTBrandLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("t-magazine.json")
    suspend fun getTMagazineLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("technology.json")
    suspend fun getTechnologyLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("theater.json")
    suspend fun getTheaterLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("times-insider.json")
    suspend fun getTimesInsiderLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("todays-paper.json")
    suspend fun getTodaysPaperLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("travel.json")
    suspend fun getTravelLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("us.json")
    suspend fun getUSLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("universal.json")
    suspend fun getUniversalLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("the-upshot.json")
    suspend fun getTheUpshotLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("video.json")
    suspend fun getVideoLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("the-weekly.json")
    suspend fun getTheWeeklyLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("well.json")
    suspend fun getWellLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("world.json")
    suspend fun getWorldLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>

    @GET("your-money.json")
    suspend fun getYourMoneyLatestNews(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<LatestNewsResponse>



    @GET("https://api.nytimes.com/svc/news/v3/content/section-list.json")
    suspend fun getCategoryList(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<CategoryResponse>
}