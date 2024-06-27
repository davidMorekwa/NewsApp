package com.example.newsapp.data.repositories.local_data.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.example.newsapp.data.repositories.local_data.entities.MultimediaEntity

@Dao
interface MultimediaDao {
    @Upsert
    suspend fun insertMultimedia(media: List<MultimediaEntity>): List<Long>
}