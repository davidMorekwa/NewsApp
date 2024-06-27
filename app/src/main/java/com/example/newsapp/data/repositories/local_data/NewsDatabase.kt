package com.example.newsapp.data.repositories.local_data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsapp.data.repositories.local_data.dao.MultimediaDao
import com.example.newsapp.data.repositories.local_data.dao.NewsCategoryDao
import com.example.newsapp.data.repositories.local_data.dao.NewsDao
import com.example.newsapp.data.repositories.local_data.dao.RecentSearchDao
import com.example.newsapp.data.repositories.local_data.entities.MultimediaEntity
import com.example.newsapp.data.repositories.local_data.entities.NewsArticleEntity
import com.example.newsapp.data.repositories.local_data.entities.NewsCategoryEntity
import com.example.newsapp.data.repositories.local_data.entities.RecentSearchEntity
import com.example.newsapp.data.utils.Constants

@Database(
    entities = [NewsArticleEntity::class, MultimediaEntity::class, RecentSearchEntity::class, NewsCategoryEntity::class],
    version = 7,
    exportSchema = false
)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun multimediaDao(): MultimediaDao
    abstract fun recentSearchDao(): RecentSearchDao
    abstract fun newsCategoryDao():NewsCategoryDao
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