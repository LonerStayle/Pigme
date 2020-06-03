package com.example.wisesaying.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pigme(
    @PrimaryKey
    var textStory: String,
    var image: Int
)