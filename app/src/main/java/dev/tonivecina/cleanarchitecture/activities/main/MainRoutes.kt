package dev.tonivecina.cleanarchitecture.activities.main

import android.content.Intent
import dev.tonivecina.cleanarchitecture.activities.newnote.NewNoteActivity

/**
 * @author Toni Vecina on 6/25/17.
 */
class MainRoutes(val view: MainActivity) {

    fun intentForNoteAddActivity() {
        val intent = Intent(view, NewNoteActivity::class.java)
        view.startActivityForResult(intent, NewNoteActivity.REQUEST_CODE, null)
    }
}