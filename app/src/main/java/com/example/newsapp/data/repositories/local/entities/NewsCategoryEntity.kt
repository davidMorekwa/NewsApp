package com.example.newsapp.data.repositories.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.data.utils.Constants
import java.util.UUID

@Entity(tableName = Constants.TABLE_NEWS_CATEGORY)
data class NewsCategoryEntity (
    @PrimaryKey
    val id: UUID,
    val name: String
) {

}