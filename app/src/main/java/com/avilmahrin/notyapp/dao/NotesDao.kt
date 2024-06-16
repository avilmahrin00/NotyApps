package com.avilmahrin.notyapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.avilmahrin.notyapp.model.Notes

@Dao
interface NotesDao {

    @Query("SELECT * FROM Notes")
    fun getNotes(): LiveData<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserNotes(notes: Notes)

    @Query("DELETE FROM Notes WHERE id=:id")
    fun deleteNotes(id: Int)

    @Update
    fun updateNotes(notes: Notes)

    @Query("SELECT * FROM Notes")
    fun getNotesSynchronously(): List<Notes> // Metode sinkron untuk pengambilan data
}
