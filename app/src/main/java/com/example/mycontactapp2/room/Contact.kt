package com.example.mycontactapp2.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "name") var contactName: String,
    var surname: String? = null,
    @ColumnInfo(name = "phone") var phoneNumber: String,
    var state: Int = 0
) :Parcelable