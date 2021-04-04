package com.ijniclohot.ticketnativechallenge.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ijniclohot.ticketnativechallenge.R
import com.ijniclohot.ticketnativechallenge.model.GithubUser
import de.hdodenhof.circleimageview.CircleImageView

class MainAdapter(private var githubUsers: ArrayList<GithubUser>, private val context: Context) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var avatar: CircleImageView = view.findViewById(R.id.userAvatarImageView)
        var username: TextView = view.findViewById(R.id.usernameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.github_user_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(githubUsers[position].avatarURL)
            .into(holder.avatar)
        holder.username.text = githubUsers[position].login
    }

    override fun getItemCount(): Int {
        return githubUsers.size
    }

    fun refreshData(githubUsers: ArrayList<GithubUser>) {
        this.githubUsers = githubUsers
        notifyDataSetChanged()
    }

    fun addDataList(githubUsers: ArrayList<GithubUser>) {
        this.githubUsers.addAll(githubUsers)
        notifyDataSetChanged()
    }
}