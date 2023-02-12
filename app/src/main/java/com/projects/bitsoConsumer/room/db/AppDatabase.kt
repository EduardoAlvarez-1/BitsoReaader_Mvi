package com.projects.bitsoConsumer.room.db

import androidx.room.*
import com.example.readbitso.ds.room.dao.entity.AskBids
import com.example.readbitso.ds.room.dao.entity.OperationsTrades
import com.projects.bitsoConsumer.room.dao.AskBidsDao
import com.projects.bitsoConsumer.room.dao.TradesDao

@Database(entities = [ OperationsTrades::class, AskBids::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tradesDao(): TradesDao
    abstract fun askBidsDao(): AskBidsDao
}
