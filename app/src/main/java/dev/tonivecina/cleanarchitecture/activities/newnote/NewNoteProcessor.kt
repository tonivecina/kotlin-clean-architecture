package dev.tonivecina.cleanarchitecture.activities.newnote

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import dev.tonivecina.cleanarchitecture.entities.database.note.Note

/**
 * @author Toni Vecina on 6/25/17.
 */
class NewNoteProcessor(val view: NewNoteActivity): NewNoteListeners.ActionListener {

    val noteInteractor: NewNoteInteractorNote by lazy {
        NewNoteInteractorNote()
    }

    val onClickListener: NewNoteOnClickListener by lazy {
        NewNoteOnClickListener(this)
    }

    fun onCreate() {
        // empty function
    }

    private fun addNote() {
        val title = view.title()
        val description = view.description()

        if (title == null || title.isEmpty()) {
            Toast.makeText(view, "Title is empty.", Toast.LENGTH_SHORT).show()
            return
        }

        if (description == null || description.isEmpty()) {
            Toast.makeText(view, "Description is empty.", Toast.LENGTH_SHORT).show()
            return
        }

        noteInteractor.addNote(title, description, {
            note: Note ->

            val intent = Intent()
            intent.putExtra(NewNoteActivity.BUNDLE_NOTE, note)

            view.setResult(Activity.RESULT_OK, intent)
            view.finish()
        })
    }

    override fun onApplyNoteClick() {
        addNote()
    }
    //endregion
}