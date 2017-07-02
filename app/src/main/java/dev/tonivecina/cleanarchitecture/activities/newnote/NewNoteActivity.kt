package dev.tonivecina.cleanarchitecture.activities.newnote

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import dev.tonivecina.cleanarchitecture.R

class NewNoteActivity : AppCompatActivity() {

    companion object {
        val BUNDLE_NOTE = "BUNDLE_NOTE"
        val REQUEST_CODE: Int = 10001
    }

    private var titleEditText: EditText? = null
    private var descriptionEditText: EditText? = null
    private var addButton: Button? = null

    private val processor: NewNoteProcessor by lazy {
        NewNoteProcessor(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)

        titleEditText = findViewById(R.id.activity_newNote_editText_title) as EditText
        descriptionEditText = findViewById(R.id.activity_newNote_editText_description) as EditText

        val onClickListener = processor.onClickListener

        addButton = findViewById(R.id.activity_newNote_button_add) as Button
        addButton?.setOnClickListener(onClickListener)

        processor.onCreate()
    }

    //region Getters
    fun title(): String? {
        return titleEditText?.text.toString()
    }

    fun description(): String? {
        return descriptionEditText?.text.toString()
    }
    //endregion
}
