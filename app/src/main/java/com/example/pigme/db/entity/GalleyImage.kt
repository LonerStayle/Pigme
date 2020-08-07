package com.example.pigme.db.entity

import androidx.room.Entity

import androidx.room.PrimaryKey

@Entity
data class GalleyImage (
    @PrimaryKey
    var galleryImage: String
)