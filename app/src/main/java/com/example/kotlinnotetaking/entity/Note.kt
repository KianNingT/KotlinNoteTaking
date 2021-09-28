package com.example.kotlinnotetaking.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_kotlin_room")
data class Note(
 @ColumnInfo(name = "note_title")
 var title:String,
 @ColumnInfo(name = "note_description")
 var description:String,
 @ColumnInfo(name = "note_priority")
 var priority:Int) {

 @PrimaryKey(autoGenerate = true)
  var id:Int = 0

}