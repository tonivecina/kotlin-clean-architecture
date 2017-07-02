package dev.tonivecina.cleanarchitecture.activities.newnote

import android.os.AsyncTask
import dev.tonivecina.cleanarchitecture.application.Configuration
import dev.tonivecina.cleanarchitecture.entities.database.note.Note
import java.util.*

/**
 * @author Toni Vecina on 6/26/17.
 */
class NewNoteInteractorNote {

    fun addNote(title: String, description: String, finished: (note: Note) -> Unit) {
        val note = Note(title, description, Date())

        AsyncTask.execute {
            Configuration
                    .database
                    .noteDao
                    .insert(note)

            finished(note)
        }
    }
}