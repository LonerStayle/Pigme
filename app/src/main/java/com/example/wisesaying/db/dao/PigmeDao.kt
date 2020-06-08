package com.example.wisesaying.db.dao
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.wisesaying.db.entity.Pigme

@Dao
interface PigmeDao {
    @Query("SELECT*FROM Pigme")
    fun getAllPigmeList():LiveData<List<Pigme>>
    @Insert
    fun insert(pigme: Pigme)
    @Delete
    fun delete(pigme: Pigme)

}