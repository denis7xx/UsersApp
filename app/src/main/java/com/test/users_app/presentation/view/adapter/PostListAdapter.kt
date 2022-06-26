package com.test.users_app.presentation.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.users_app.R
import com.test.users_app.databinding.PostItemBinding
import com.test.users_app.domain.model.Post
import com.test.users_app.presentation.view.adapter.viewholders.PostViewHolder

class PostListAdapter(private var items: List<Post>) : RecyclerView.Adapter<PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = parent.inflate(R.layout.post_item)
        val binding = PostItemBinding.bind(view)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = items[position]
        holder.binding.tvTitle.text = post.title
        holder.binding.tvBody.text = post.body
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(it: List<Post>) {
        items = it
    }
}
