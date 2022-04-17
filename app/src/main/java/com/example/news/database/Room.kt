package com.example.news.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.*

//RoomDB quires used to perform action on the cached data
@Dao
interface NewsDao{
    @Query("select * from DatabaseNews")
    fun getNews(): LiveData<List<DatabaseNews>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(news: List<DatabaseNews>)

    @Query("DELETE FROM DatabaseNews")
   suspend fun clear()

   @Query("SELECT * FROM DATABASENEWS")
   fun getNewsPaging(): PagingSource <Int, DatabaseNews>

}

//Implementation of the RoomDatabase
@Database(entities = [DatabaseNews::class], version = 1, exportSchema = false)
    abstract class NewsDatabase:RoomDatabase()  {
        //To access dao methods
        abstract fun newsDao() : NewsDao

        companion object {
            @Volatile
            private var INSTANCE: NewsDatabase? = null

            fun getDatabase(context: Context): NewsDatabase{
             return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                context.applicationContext,
                NewsDatabase::class.java,
                "NewsList"
                ).build()
                INSTANCE = instance
                return instance
                }
            }
        }


    }