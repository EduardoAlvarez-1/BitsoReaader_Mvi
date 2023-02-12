package com.projects.bitsoConsumer.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.readbitso.ds.room.dao.entity.AskBids

@Dao
interface AskBidsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<AskBids>)

    @Query("SELECT * FROM AskBids")
    suspend fun getAll(): List<AskBids>

    @Query("SELECT * FROM AskBids Where Book =:book")
    suspend fun getOne(book: String): AskBids
}
