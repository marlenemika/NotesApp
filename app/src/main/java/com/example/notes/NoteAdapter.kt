package com.example.notes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(private val context : Context, private val dataSet : ArrayList<Note>, private val mOnItemClickListener : OnItemClickListener) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup : ViewGroup, viewType : Int) : ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_layout, viewGroup, false)

        return ViewHolder(context, view, mOnItemClickListener)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder : ViewHolder, position : Int) {

        // use this when only rendering items that are visible or something like this?! LUL
        when (viewHolder){
            is ViewHolder -> viewHolder.bind(dataSet[position])
        }
    }

    // Return the size of your data set (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(private val context : Context, view : View, private val onItemClickListener : OnItemClickListener) : RecyclerView.ViewHolder(view), View.OnClickListener {

        // always needed
        val txtTitle : TextView = view.findViewById(R.id.title)
        val txtContent : TextView = view.findViewById(R.id.content)
        val card : CardView = view.findViewById(R.id.colour)

        fun bind (note : Note) {
            txtTitle.text = note.title
            txtContent.text = note.content
            card.setCardBackgroundColor(ContextCompat.getColor(context, getNoteColour(note)))
        }

        // below for click listener
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(view : View) {
            onItemClickListener.onItemClick(adapterPosition)
        }

        fun getNoteColour(note : Note) : Int {
            return when(note.colour)   {
                Colour.BLUE -> R.color.card_blue
                Colour.GREEN -> R.color.card_green
                Colour.ORANGE -> R.color.card_orange
                Colour.PINK -> R.color.card_pink
                Colour.GREY -> R.color.card_grey
                Colour.PURPLE -> R.color.card_purple
                Colour.YELLOW -> R.color.card_yellow
            }
        }
    }

    interface OnItemClickListener {

        fun onItemClick(position : Int)
    }
}