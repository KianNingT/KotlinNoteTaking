package com.example.kotlinnotetaking.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinnotetaking.R
import com.example.kotlinnotetaking.entity.Note


class NoteKotlinAdapter : ListAdapter<Note, NoteKotlinAdapter.NoteKotlinHolder>(DiffUtilNote()) {

    private var mListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteKotlinAdapter.NoteKotlinHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteKotlinHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteKotlinAdapter.NoteKotlinHolder, position: Int) {
        val currentNote = getItem(position)
        holder.textViewTitle.text = currentNote.title
        //holder.textViewDescription.text = currentNote.description
        holder.textViewPriority.text = currentNote.priority.toString()
    }

     inner class NoteKotlinHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

         val textViewTitle: TextView = itemView.findViewById(R.id.text_view_title)
         //val textViewDescription: TextView = itemView.findViewById(R.id.text_view_description)
         val textViewPriority: TextView = itemView.findViewById(R.id.text_view_priority)

        init {
            itemView.setOnClickListener {
                if (mListener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    mListener?.onItemClick(getItem(adapterPosition))
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }

    fun setOnNoteClickListener(listener: OnItemClickListener) {
        mListener = listener
    }


    fun getNotesAt(position: Int): Note = getItem(position)


    private class DiffUtilNote : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.description == newItem.description && oldItem.priority == newItem.priority
        }
    }
}