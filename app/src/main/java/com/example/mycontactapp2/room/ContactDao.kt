package com.example.mycontactapp2.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContactDao {

    @Query("SELECT * FROM contacts")
    fun getMyAllContacts(): List<Contact>

    @Insert(onConflict = OnConflictStrategy.NONE)
    fun addContact(contact: Contact)

    @Query("DELETE FROM contacts WHERE id = :Id")
    fun deleteContact(Id: Int)

    @Query("SELECT * FROM contacts WHERE name LIKE :name")
    fun findContactWithName(name: String): List<Contact>

    @Update
    fun updateContact(contact: Contact)

    @Query("DELETE FROM contacts")
    fun deleteAll()
}