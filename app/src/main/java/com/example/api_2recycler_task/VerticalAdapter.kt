package com.example.api_2recycler_task


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.api_2recycler_task.API_Comments.CommentsResponseItem
import com.example.api_2recycler_task.API_Posts.PostsResponseItem


class VerticalAdapter(private var list: ArrayList<CommentsResponseItem>) :
    RecyclerView.Adapter<VerticalAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.text_rec_2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.shape_vertical, parent, false)
        )
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VerticalAdapter.ViewHolder, position: Int) {

        holder.textView.text =
            "PostID: " + list[position].postId + "\n" +
                    "ID: " + list[position].id + "\n" +
                    "Name: " + list[position].name + "\n" +
                    "Email: " + list[position].email + "\n" +
                    "Body: " + list[position].body

        holder.itemView.setOnClickListener {

        }
    }


    override fun getItemCount(): Int {
        return list.size
    }
}