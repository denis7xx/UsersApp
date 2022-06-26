package com.test.users_app.presentation.view

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.users_app.R
import com.test.users_app.databinding.ActivityUserPostsBinding
import com.test.users_app.domain.model.User
import com.test.users_app.presentation.view.adapter.PostListAdapter
import com.test.users_app.presentation.viewmodel.PostsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserPostsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserPostsBinding
    private val postsViewModel: PostsViewModel by viewModels()
    private val postListAdapter = PostListAdapter(emptyList())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserPostsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = User(
            intent.getIntExtra("userId", 0),
            intent.getStringExtra("name")!!,
            "",
            intent.getStringExtra("phone")!!,
            intent.getStringExtra("email")!!
        )
        binding.tvUserName.text = user.name
        binding.tvUserPhone.text = user.phone
        binding.tvUserMail.text = user.email
        postsViewModel.onCreate(user.id)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvPostList.layoutManager = linearLayoutManager
        binding.rvPostList.adapter = postListAdapter
        postsViewModel.isLoading.observe(this, Observer{
            binding.pbLoadPost.isVisible = it
        })
        postsViewModel.message.observe(this, Observer {
            binding.tvMessage.text = it
            binding.tvMessage.isVisible = true
            binding.rvPostList.isVisible = false
        })
        postsViewModel.posts.observe(this, Observer { users ->
            binding.rvPostList.isVisible = users.isNotEmpty()
            binding.tvMessage.isVisible = users.isEmpty()
            if (users.isNotEmpty()) {
                with(postListAdapter) {
                    setItems(users)
                    notifyDataSetChanged()
                }
            } else {
                binding.tvMessage.text = getText(R.string.empty_list)
            }
        })
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}