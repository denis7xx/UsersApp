package com.test.users_app.presentation.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.annotation.LayoutRes
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.test.users_app.R
import com.test.users_app.databinding.UserItemBinding
import com.test.users_app.domain.model.User
import com.test.users_app.presentation.view.UserPostsActivity
import com.test.users_app.presentation.view.adapter.viewholders.UserViewHolder

class UserListAdapter(private var items: List<User>) : RecyclerView.Adapter<UserViewHolder>(),
    Filterable {
    private lateinit var context: Context
    private var itemsFiltered = emptyList<User>()
    private lateinit var adapterItemsCount: MutableLiveData<Int>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = parent.inflate(R.layout.user_item)
        val binding = UserItemBinding.bind(view)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = itemsFiltered[position]
        holder.binding.tvUserMail.text = user.email
        holder.binding.tvUserName.text = user.name
        holder.binding.tvUserPhone.text = user.phone
        holder.binding.tvPublications.setOnClickListener(View.OnClickListener {
            val selectedUser = itemsFiltered[position]
            val intent = Intent(context, UserPostsActivity::class.java)
            intent.putExtra("userId", selectedUser.id)
            intent.putExtra("name", selectedUser.name)
            intent.putExtra("phone", selectedUser.phone)
            intent.putExtra("email", selectedUser.email)
            context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return itemsFiltered.size
    }
    fun setContext(context: Context) {
         this.context = context
    }
    fun setItems(it: List<User>) {
        items = it
        itemsFiltered = it
    }

    fun setAdapterItemsCount(it: MutableLiveData<Int>) {
        adapterItemsCount = it
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                val itFiltered = ArrayList<User>()
                itemsFiltered = if (charString.isEmpty()) items else {
                    items.filter { user -> (user.name.startsWith(constraint!!, ignoreCase = true)) }
                        .forEach { itFiltered.add(it) }
                    itFiltered
                }
                return FilterResults().apply { values = itemsFiltered }
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                itemsFiltered = if (results?.values == null)
                    emptyList()
                else
                    results.values as ArrayList<User>
                notifyDataSetChanged()
                adapterItemsCount.postValue(itemsFiltered.size)
            }
        }
    }
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}