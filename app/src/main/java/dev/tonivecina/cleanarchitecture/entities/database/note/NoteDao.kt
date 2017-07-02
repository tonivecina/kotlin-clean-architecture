package dev.tonivecina.cleanarchitecture.entities.database.note

import android.arch.persistence.room.*

/**
 * @author Toni Vecina on 6/25/17.
 */
@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAll(): List<Note>

    @Insert
    fun insert(note: Note)
}