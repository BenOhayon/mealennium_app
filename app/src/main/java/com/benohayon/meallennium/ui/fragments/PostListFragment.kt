package com.benohayon.meallennium.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.benohayon.meallennium.R
import com.benohayon.meallennium.framework.models.Post
import com.benohayon.meallennium.ui.adapters.PostsListAdapter

class PostListFragment : Fragment() {

    private lateinit var progressBar: ProgressBar
    private lateinit var postsRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_post_list, container, false)

        progressBar = view.findViewById(R.id.postListFragmentProgressBar)
        postsRecyclerView = view.findViewById(R.id.postListFragmentRecyclerView)

        val layoutManager = LinearLayoutManager(activity!!)
        postsRecyclerView.layoutManager = layoutManager
        val postsListAdapter = PostsListAdapter(activity!!, listOf(
                Post("Post Title 1", "This is summery for post 1", "This is content for post 1", "logo url for post 1", "Author for post 1"),
                Post("Post Title 2", "This is summery for post 2", "This is content for post 2", "logo url for post 2", "Author for post 2"),
                Post("Post Title 3", "This is summery for post 3", "This is content for post 3", "logo url for post 3", "Author for post 3"),
                Post("Post Title 4", "This is summery for post 4", "This is content for post 4", "logo url for post 4", "Author for post 4"),
                Post("Post Title 5", "This is summery for post 5", "This is content for post 5", "logo url for post 5", "Author for post 5"),
                Post("Post Title 6", "This is summery for post 6", "This is content for post 6", "logo url for post 6", "Author for post 6"),
                Post("Post Title 7", "This is summery for post 7", "This is content for post 7", "logo url for post 7", "Author for post 7"),
                Post("Post Title 8", "This is summery for post 8", "This is content for post 8", "logo url for post 8", "Author for post 8"),
                Post("Post Title 9", "This is summery for post 9", "This is content for post 9", "logo url for post 9", "Author for post 9"),
                Post("Post Title 10", "This is summery for post 10", "This is content for post 10", "logo url for post 10", "Author for post 10"),
                Post("Post Title 11", "This is summery for post 11", "This is content for post 11", "logo url for post 11", "Author for post 11"),
                Post("Post Title 12", "This is summery for post 12", "This is content for post 12", "logo url for post 12", "Author for post 12"),
                Post("Post Title 13", "This is summery for post 13", "This is content for post 13", "logo url for post 13", "Author for post 13"),
                Post("Post Title 14", "This is summery for post 14", "This is content for post 14", "logo url for post 14", "Author for post 14")
        ))

        postsRecyclerView.adapter = postsListAdapter

        return view
    }
}
