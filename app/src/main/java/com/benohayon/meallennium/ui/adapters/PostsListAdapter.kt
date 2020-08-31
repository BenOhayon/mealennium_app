package com.benohayon.meallennium.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.benohayon.meallennium.R
import com.benohayon.meallennium.framework.models.*
import com.benohayon.meallennium.ui.activities.PostDetailsActivity

class PostsListAdapter(val context: Context, private val list: List<Post>) : RecyclerView.Adapter<PostsListAdapter.PostsListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsListViewHolder {
        return PostsListViewHolder(LayoutInflater.from(context).inflate(R.layout.posts_list_item, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PostsListViewHolder, position: Int) {
        val postForPosition = list[position]
        holder.postAuthorName.text = postForPosition.author
        holder.postSummery.text = postForPosition.summery
        holder.postTitle.text = postForPosition.title

        holder.itemView.setOnClickListener {
            val toPostDetailsActivity = Intent(context, PostDetailsActivity::class.java)
            toPostDetailsActivity.putExtra(POST_AUTHOR_KEY, postForPosition.author)
            toPostDetailsActivity.putExtra(POST_TITLE_KEY, postForPosition.title)
            toPostDetailsActivity.putExtra(POST_SUMMERY_KEY, postForPosition.summery)
            toPostDetailsActivity.putExtra(POST_CONTENT_KEY, postForPosition.content)
            context.startActivity(toPostDetailsActivity)
        }
    }

    class PostsListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val postTitle: TextView = view.findViewById(R.id.postListItemPostTitle)
        val postAuthorName: TextView = view.findViewById(R.id.postListItemAuthorName)
        val postSummery: TextView = view.findViewById(R.id.postListItemPostContent)

    }
}