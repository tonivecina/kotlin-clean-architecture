package dev.tonivecina.cleanarchitecture.activities.main

import android.view.View
import dev.tonivecina.cleanarchitecture.R

/**
 * @author Toni Vecina on 6/25/17.
 */
class MainOnClickListener(val listener: MainListeners.ActionListener): View.OnClickListener {

    override fun onClick(v: android.view.View?) {
        val id = v?.id

        when (id) {
            R.id.activity_main_button_add -> listener.onAddNoteClick()
        }
    }
}