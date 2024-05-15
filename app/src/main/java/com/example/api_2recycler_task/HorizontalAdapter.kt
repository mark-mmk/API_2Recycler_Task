package com.example.api_2recycler_task

import android.annotation.SuppressLint
import com.example.api_2recycler_task.API_Posts.PostsResponse
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HorizontalAdapter(var list: PostsResponse?, val listener: myClickListener) :
    RecyclerView.Adapter<HorizontalAdapter.ViewHolder>() {
    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val textView: TextView = itemview.findViewById(R.id.text_rec_1)

        init {
            itemview.setOnClickListener {
                val index = adapterPosition
                listener.onClick(index)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.shape_horizontal, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text =
            "UserID: " + list!![position].userId + "\n" +
                    "ID: " + list!![position].id + "\n" +
                    "Title: " + list!![position].title + "\n" +
                    "Body: " + list!![position].body
    }


    interface myClickListener {
        fun onClick(position: Int)
    }
}