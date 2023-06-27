package com.example.mycontactapp2.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "name") var contactName: String,
    var surname: String? = null,
    @ColumnInfo(name = "phone") var phoneNumber: String
)