package dev.tonivecina.cleanarchitecture.activities.main

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import dev.tonivecina.cleanarchitecture.R
import dev.tonivecina.cleanarchitecture.entities.database.note.Note

/**
 * @author Toni Vecina on 6/25/17.
 */
class MainNoteAdapter : RecyclerView.Adapter<MainNoteViewHolder>() {

    private var context: Context? = null
    var notes: MutableList<Note> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MainNoteViewHolder {

        if (context == null) {
            context = parent?.context
        }

        val view = android.view.LayoutInflater
                .from(context)
                .inflate(R.layout.cardview_note, parent, false)

        return MainNoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainNoteViewHolder?, position: Int) {
        val note = notes[position]
        holder?.setView(note)
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}