package com.test.users_app.presentation.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.users_app.R
import com.test.users_app.databinding.ActivityMainBinding
import com.test.users_app.presentation.view.adapter.UserListAdapter
import com.test.users_app.presentation.viewmodel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), TextWatcher {
    private lateinit var binding: ActivityMainBinding
    private val usersViewModel: UsersViewModel by viewModels()
    private val userListAdapter = UserListAdapter(emptyList())
    private val adapterItemsCount = MutableLiveData<Int>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        usersViewModel.onCreate()
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvUserList.layoutManager = linearLayoutManager
        binding.rvUserList.adapter = userListAdapter
        usersViewModel.isLoading.observe(this, Observer {
            binding.pbFinduser.isVisible = it
        })
        usersViewModel.message.observe(this, Observer {
            binding.tvMessage.text = it
            binding.tvMessage.isVisible = true
            binding.rvUserList.isVisible = false
        })
        usersViewModel.users.observe(this, Observer { users ->
            binding.rvUserList.isVisible = users.isNotEmpty()
            binding.tvMessage.isVisible = users.isEmpty()
            if (users.isNotEmpty()) {
                with(userListAdapter) {
                    setItems(users)
                    notifyDataSetChanged()
                }
            } else {
                binding.tvMessage.text = getText(R.string.empty_list)
            }
        })
        adapterItemsCount.observe(this, Observer {
            runOnUiThread {
                var isEmpty = userListAdapter.itemCount < 1
                binding.rvUserList.isVisible = !isEmpty
                binding.tvMessage.isVisible = isEmpty
            }
        })
        userListAdapter.setContext(this)
        userListAdapter.setAdapterItemsCount(adapterItemsCount)
        binding.edFindUser.addTextChangedListener(this)

    }

    override fun afterTextChanged(editable: Editable?) {
        userListAdapter.filter.filter(editable.toString())
    }

    override fun beforeTextChanged(editable: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(editable: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

}