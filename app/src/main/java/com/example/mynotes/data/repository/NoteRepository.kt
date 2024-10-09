package com.example.mynotes.data.repository
import com.example.mynotes.data.db.Note
import com.example.mynotes.data.db.NoteDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor (val noteDao: NoteDao) {
    suspend fun getAll() = noteDao.getAll()
    suspend fun getFavorites() = noteDao.getFavorites()
    suspend fun insert(note: Note) = noteDao.insert(note)
    suspend fun update(note: Note) = noteDao.update(note)
    suspend fun delete(note: Note) = noteDao.delete(note)
    suspend fun getById(id: Int): Note? = noteDao.getById(id)

}