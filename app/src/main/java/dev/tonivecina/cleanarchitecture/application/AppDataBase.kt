package dev.tonivecina.cleanarchitecture.application

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import dev.tonivecina.cleanarchitecture.entities.database.note.Note
import dev.tonivecina.cleanarchitecture.entities.database.note.NoteDao

/**
 * @author Toni Vecina on 6/25/17.
 */
@Database(entities = arrayOf(Note::class), version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract val noteDao: NoteDao
}