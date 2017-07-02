package dev.tonivecina.cleanarchitecture.activities.main

import android.os.AsyncTask
import dev.tonivecina.cleanarchitecture.application.Configuration
import dev.tonivecina.cleanarchitecture.entities.database.note.Note
import java.util.*

/**
 * @author Toni Vecina on 6/28/17.
 */
class MainInteractorNotes {

    private val noteDao = Configuration.database.noteDao

    fun getAll(finished: (notes: List<Note>) -> Unit) {
        AsyncTask.execute {
            val notes = noteDao.getAll()
            Collections.reverse(notes)
            finished(notes)
        }
    }
}