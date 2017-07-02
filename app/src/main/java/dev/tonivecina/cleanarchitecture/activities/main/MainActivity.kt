package dev.tonivecina.cleanarchitecture.activities.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import dev.tonivecina.cleanarchitecture.R
import dev.tonivecina.cleanarchitecture.activities.newnote.NewNoteActivity
import dev.tonivecina.cleanarchitecture.entities.database.note.Note

class MainActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var addButton: Button? = null

    private val processor = MainProcessor(this)
    private val noteAdapter = MainNoteAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.activity_main_recyclerView) as RecyclerView
        recyclerView?.adapter = noteAdapter
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.itemAnimator = DefaultItemAnimator()

        val onClickListener = processor.onClickListener

        addButton = findViewById(R.id.activity_main_button_add) as Button
        addButton?.setOnClickListener(onClickListener)

        processor.onCreate()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            NewNoteActivity.REQUEST_CODE -> {
                processor.onNewNoteActivityResult(resultCode, data)
            }
        }
    }

    fun addNote(note: Note) {
        runOnUiThread {
            noteAdapter.notes.add(0, note)
            noteAdapter.notifyDataSetChanged()
        }
    }

    fun setNotes(notes: List<Note>) {
        runOnUiThread {
            noteAdapter.notes.clear()
            noteAdapter.notes.addAll(notes)
            noteAdapter.notifyDataSetChanged()
        }
    }
}
