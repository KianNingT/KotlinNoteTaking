package com.example.kotlinnotetaking

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinnotetaking.adapter.NoteKotlinAdapter
import com.example.kotlinnotetaking.databinding.ActivityAddNoteBinding
import com.example.kotlinnotetaking.databinding.ActivityMainBinding
import com.example.kotlinnotetaking.entity.Note
import com.example.kotlinnotetaking.viewModel.NoteKotlinViewModel
import com.example.kotlinnotetaking.viewModel.NoteKotlinViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
                                 //instance here refers to our kodein
    private val factory: NoteKotlinViewModelFactory by instance()

    private lateinit var binding: ActivityMainBinding

    private lateinit var noteViewModel : NoteKotlinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.toolbar.setTitleTextColor(Color.BLACK)
        setSupportActionBar(binding.toolbar)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)

        val noteAdapter = NoteKotlinAdapter()
        binding.recyclerView.adapter = noteAdapter

        //android system will destroy this view model when the activity is finished
        noteViewModel = ViewModelProvider(this, factory).get(NoteKotlinViewModel::class.java)


        noteViewModel.getAllNotes().observe(this, {
            noteAdapter.submitList(it)
        })

        binding.buttonAddNote.setOnClickListener {
            val addNoteIntent = Intent(this, AddEditNoteActivity::class.java)
            //startActivityForResult(intent, ADD_NOTE_REQUEST)
            resultAddNoteLauncher.launch(addNoteIntent)
        }


                                                   //0 cause we're not using drag & drop
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.delete(noteAdapter.getNotesAt(viewHolder.adapterPosition))
                Toast.makeText(this@MainActivity, "Note deleted", Toast.LENGTH_SHORT).show()
            }

        }).attachToRecyclerView(binding.recyclerView)



        noteAdapter.setOnNoteClickListener(object: NoteKotlinAdapter.OnItemClickListener {
            override fun onItemClick(note: Note) {
                val editNoteIntent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
                editNoteIntent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.title)
                editNoteIntent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.description)
                editNoteIntent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.priority)
                editNoteIntent.putExtra(AddEditNoteActivity.EXTRA_ID, note.id)
                //startActivityForResult(intent, EDIT_NOTE_REQUEST)
                resultEditNoteLauncher.launch(editNoteIntent)
            }
        })

    } //onCreate ends here



    private var resultAddNoteLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data

            val title = data?.getStringExtra(AddEditNoteActivity.EXTRA_TITLE)
            val description = data?.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION)
            val priority = data?.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1)

            val note = title?.let { description?.let { it1 -> priority?.let { it2 ->
                Note(it, it1, it2)
            } } }
            note?.let { noteViewModel.insert(it) }
        }
    }

    private var resultEditNoteLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data

            val title = data?.getStringExtra(AddEditNoteActivity.EXTRA_TITLE)
            val description = data?.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION)
            val priority = data?.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1)
            val noteId = data?.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1)

            if (noteId == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show()
                return@registerForActivityResult
            }

            val note = title?.let { description?.let { it1 -> priority?.let { it2 ->
                Note(it, it1,
                    it2
                )
            } } }
            if (noteId != null) {
                note?.id = noteId
            }
            note?.let { noteViewModel.update(it) }

            Toast.makeText(this, "Note updated successfully", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all_notes -> {
                noteViewModel.deleteAllNotes()
                true
            }
            else ->  super.onOptionsItemSelected(item)
        }
    }

}