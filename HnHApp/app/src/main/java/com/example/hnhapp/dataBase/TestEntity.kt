package com.example.hnhapp.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ENTITY_NAME")
data class TestEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val someString: String
)
