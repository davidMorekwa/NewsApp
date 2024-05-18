package com.example.newsapp.data.repositories.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.data.repositories.local.entities.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchDao {
    @Query("SELECT * FROM tbl_recent_search")
    fun getRecentSearch(): Flow<List<RecentSearchEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(searchEntity: RecentSearchEntity)
    @Query("DELETE FROM tbl_recent_search")
    suspend fun clearRecentSearch()
    @Query("DELETE FROM tbl_recent_search where string = :string")
    suspend fun deleteSearchTerm(string: String)
}