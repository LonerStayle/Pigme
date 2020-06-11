package com.example.wisesaying.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * FIXME: image:Int 모두 Bitmap 자료형으로 변환 예정
 */
@Entity
data class Pigme(
    @PrimaryKey
    var textStory: String,
    var image: Int,
    var imageUri : String?
)