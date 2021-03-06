package kr.loner.pigme.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kr.loner.pigme.db.dao.PigmeDao
import kr.loner.pigme.db.entity.GalleyImage
import kr.loner.pigme.db.entity.Pigme


@Database(entities = [Pigme::class, GalleyImage::class],exportSchema = false,version = 1)
abstract class PigmeDatabase:RoomDatabase()  {
    abstract val pigmeDao: PigmeDao

    companion object {
        @Volatile
        private var INSTANCE: PigmeDatabase? = null
        fun getInstance(context: Context): PigmeDatabase = synchronized(this) {
           INSTANCE?: Room.databaseBuilder(
               context,
               PigmeDatabase::class.java,
               "pigme_database"
           ).fallbackToDestructiveMigration()
               .build() .also {
                   INSTANCE = it
               }
        }
    }
}