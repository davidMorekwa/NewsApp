package com.example.newsapp.data.repositories.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsapp.data.repositories.local.dao.NewsDao
import com.example.newsapp.data.repositories.local.dao.MultimediaDao
import com.example.newsapp.data.repositories.local.dao.RecentSearchDao
import com.example.newsapp.data.repositories.local.entities.ArticleAndMedia
import com.example.newsapp.data.repositories.local.entities.MultimediaEntity
import com.example.newsapp.data.repositories.local.entities.NewsArticleEntity
import com.example.newsapp.data.repositories.local.entities.RecentSearchEntity
import com.example.newsapp.data.utils.Constants

@Database(
    entities = [NewsArticleEntity::class, MultimediaEntity::class, RecentSearchEntity::class],
    version = 4,
    exportSchema = false
)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun multimediaDao(): MultimediaDao
    abstract fun recentSearchDao(): RecentSearchDao
    companion object{
        @Volatile
        var instance:NewsDatabase? = null
        fun getDatabaseInstance(context: Context):NewsDatabase{
            return instance?: synchronized(this){
                Room.databaseBuilder(
                    context = context,
                    klass = NewsDatabase::class.java,
                    name = Constants.DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }

    }
}