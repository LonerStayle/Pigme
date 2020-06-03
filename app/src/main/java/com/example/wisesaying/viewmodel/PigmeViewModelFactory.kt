package com.example.wisesaying.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wisesaying.db.dao.PigmeDao


class PigmeViewModelFactory(private val pigmeDao:PigmeDao):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PigmeViewModel(pigmeDao) as T
    }
}

