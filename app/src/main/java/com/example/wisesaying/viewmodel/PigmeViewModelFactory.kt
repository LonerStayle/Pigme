package com.example.wisesaying.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wisesaying.db.dao.PigmeDao


class PigmeViewModelFactory(private val pigmeDao:PigmeDao):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("unchecked_cast")
            return PigmeViewModel(pigmeDao) as T
    }
}

