package com.example.newsapp.data.repositories.local_data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.newsapp.data.repositories.local_data.entities.NewsCategoryEntity

@Dao
interface NewsCategoryDao {
    @Insert
    fun addCategory(categoryEntity: NewsCategoryEntity)
    @Query("SELECT * FROM tbl_news_category")
    fun getCategories(): List<NewsCategoryEntity>
    @Delete
    fun deleteCategory(categoryEntity: NewsCategoryEntity)
    @Query("DELETE FROM tbl_news_category")
    fun clearCategories()
}