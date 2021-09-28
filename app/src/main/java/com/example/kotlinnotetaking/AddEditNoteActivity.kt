package com.example.kotlinnotetaking

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.kotlinnotetaking.databinding.ActivityAddNoteBinding
import com.example.kotlinnotetaking.databinding.ActivityMainBinding

class AddEditNoteActivity : AppCompatActivity() {


    companion object {
        const val EXTRA_TITLE = "com.example.kotlinnotetaking.EXTRA_TITLE"
        const val EXTRA_DESCRIPTION = "com.example.kotlinnotetaking.EXTRA_DESCRIPTION"
        const val EXTRA_PRIORITY = "com.example.kotlinnotetaking.EXTRA_PRIORITY"
        const val EXTRA_ID = "com.example.kotlinnotetaking.EXTRA_ID"
    }

    private lateinit var binding: ActivityAddNoteBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.setTitleTextColor(Color.BLACK)
        setSupportActionBar(toolbar)

        binding.numberPickerPriority.minValue = 1
        binding.numberPickerPriority.maxValue = 10



        val intent = intent
        if (intent.hasExtra(EXTRA_ID)) {
            binding.tvToolbarTitle.text = "Edit Note"
            binding.btnSubmit.text = "Save"
            binding.editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE))
            binding.editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
            binding.numberPickerPriority.value = intent.getIntExtra(EXTRA_PRIORITY, 1)

        } else {
            binding.tvToolbarTitle.text = "Add Note"
            binding.btnSubmit.text = "Submit"
        }

        binding.btnSubmit.setOnClickListener {
            saveNote()
        }

        binding.ibClose.setOnClickListener {
            finish()
        }
    }

    private fun saveNote() {

        var title = binding.editTextTitle.text.toString()
        val desc = binding.editTextDescription.text.toString()
        val priority = binding.numberPickerPriority.value

        if (TextUtils.isEmpty(title.trim())) {
            title = "New Note"
        }


        val data = Intent()
        data.putExtra(EXTRA_TITLE, title)
        data.putExtra(EXTRA_DESCRIPTION, desc)
        data.putExtra(EXTRA_PRIORITY, priority)

        //only put id into result intent if it's not -1, means only when updating. Cause -1 will never appear in db
        val id = intent.getIntExtra(EXTRA_ID, -1)
        if (id != -1) {
            data.putExtra(EXTRA_ID, id)
        }

        setResult(RESULT_OK, data)
        finish()
    }


}