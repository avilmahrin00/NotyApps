package com.avilmahrin.notyapp.repository

import androidx.lifecycle.LiveData
import androidx.room.Database
import com.avilmahrin.notyapp.dao.NotesDao
import com.avilmahrin.notyapp.model.Notes

class NotesRepository(val dao: NotesDao) {
    fun getallNotes(): LiveData<List<Notes>> {
        return dao.getNotes()
    }

    fun insertNotes(notes: Notes) {
        dao.inserNotes(notes)
    }

    fun deleteNotes(id: Int) {
        dao.deleteNotes(id)
    }

    fun updateNotes(notes: Notes) {
        dao.updateNotes(notes)
    }
}