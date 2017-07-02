package dev.tonivecina.cleanarchitecture.activities.main

import android.app.Activity
import android.content.Intent
import dev.tonivecina.cleanarchitecture.DLog
import dev.tonivecina.cleanarchitecture.activities.newnote.NewNoteActivity
import dev.tonivecina.cleanarchitecture.entities.database.note.Note

/**
 * @author Toni Vecina on 6/25/17.
 */

class MainProcessor(val view: MainActivity): MainListeners.ActionListener {

    val routes: MainRoutes by lazy {
        MainRoutes(view)
    }

    val onClickListener: MainOnClickListener by lazy {
        MainOnClickListener(this)
    }

    val notesInteractor: MainInteractorNotes by lazy {
        MainInteractorNotes()
    }

    fun onCreate() {
        getNotes()
    }

    private fun getNotes() {
        notesInteractor.getAll {
            notes: List<Note> -> view.setNotes(notes)
        }
    }

    fun onNewNoteActivityResult(resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_CANCELED -> {
                DLog.info("User cancelled the action.")
            }

            Activity.RESULT_OK -> {
                val note: Note? = data?.getSerializableExtra(NewNoteActivity.BUNDLE_NOTE) as? Note

                if (note == null) {
                    DLog.warning("Note not found")
                    return
                }

                view.addNote(note)
            }
        }
    }

    //region ActionListener
    override fun onAddNoteClick() {
        routes.intentForNoteAddActivity()
    }
    //endregion
}