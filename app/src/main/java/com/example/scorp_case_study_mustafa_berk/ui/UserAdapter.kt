package com.example.scorp_case_study_mustafa_berk.ui

import Person
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scorp_case_study_mustafa_berk.databinding.TvUserListItemBinding

class UserAdapter(private val context: Context, private val users:List<Person>): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return  UserViewHolder(
            TvUserListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        with(users[position]){
            holder.tv.text = "$fullName (${id})"
        }
    }

    class UserViewHolder(item: TvUserListItemBinding): RecyclerView.ViewHolder(item.root) {
         val tv = item.root
    }

}

