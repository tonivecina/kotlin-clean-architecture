package dev.tonivecina.cleanarchitecture.activities.newnote

import android.view.View
import dev.tonivecina.cleanarchitecture.R

/**
 * @author Toni Vecina on 6/25/17.
 */
class NewNoteOnClickListener(val listener: NewNoteListeners.ActionListener): View.OnClickListener {

    override fun onClick(v: View?) {
        val id = v?.id

        when (id) {
            R.id.activity_newNote_button_add -> listener.onApplyNoteClick()
        }
    }
}