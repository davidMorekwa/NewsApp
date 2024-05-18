package com.example.newsapp.data.repositories.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.data.utils.Constants

@Entity(tableName = Constants.TABLE_RECENT_SEARCH)
data class RecentSearchEntity(
    @PrimaryKey
    val string: String
)