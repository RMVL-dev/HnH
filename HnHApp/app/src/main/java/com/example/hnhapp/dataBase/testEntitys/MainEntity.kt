package com.example.hnhapp.dataBase.testEntitys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Main_Entity")
data class MainEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val product:ProductEntity
)
