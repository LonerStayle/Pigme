package com.example.wisesaying.db.dao
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.wisesaying.db.entity.GalleyImage
import com.example.wisesaying.db.entity.Pigme

@Dao
interface PigmeDao {
    @Query("SELECT*FROM Pigme")
    fun getAllPigmeList():LiveData<List<Pigme>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun allInsert(pigmelist: List<Pigme>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pigme: Pigme)

    @Delete
    fun delete(pigme: Pigme)
    @Delete
    fun listDelete(pigme: List<Pigme>)
    @Update
    fun allUpdate(pigmelist: List<Pigme>)

    @Query("SELECT * FROM GalleyImage ")
    fun getGalleyNewImageList():LiveData<List<GalleyImage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun galleyNewImageinsert(gellyimage:GalleyImage)


}