package dev.tonivecina.cleanarchitecture.activities.main

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import dev.tonivecina.cleanarchitecture.R
import dev.tonivecina.cleanarchitecture.entities.database.note.Note
import dev.tonivecina.cleanarchitecture.patterns.StringPattern
import java.util.*

/**
 * @author Toni Vecina on 6/25/17.
 */
class MainNoteViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    private var titleView: TextView? = null
    private var subtitleView: TextView? = null

    init {
        titleView = itemView?.findViewById(R.id.cardView_textView_title) as android.widget.TextView
        subtitleView = itemView.findViewById(R.id.cardView_textView_subtitle) as android.widget.TextView
    }

    fun setView(note: Note) {
        titleView?.text = note.title

        if (note.createdAtDate() != null) {
            subtitleView?.text = StringPattern.fromDate(note.createdAtDate() as Date, "EEE dd, MMM")
        }
    }
}