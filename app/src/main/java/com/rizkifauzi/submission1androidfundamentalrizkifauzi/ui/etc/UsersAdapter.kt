package com.rizkifauzi.submission1androidfundamentalrizkifauzi.ui.etc

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.data.remote.response.ItemsItem
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.databinding.ItemUsersBinding
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.ui.mActivity.DetailActivity

//class UsersAdapter : ListAdapter<ItemsItem, UsersAdapter.MyViewHolder>(DIFF_CALLBACK) {
class UsersAdapter(private val context: Context) : ListAdapter<ItemsItem, UsersAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)

        // Mengirim data Username ke DetailActivity saat item diklik
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("username", user.login)
            intent.putExtra("avatarurl", user.avatarUrl)
            context.startActivity(intent)
        }
    }
    class MyViewHolder(private val binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem) {
            binding.tvItem.text = user.login // Menampilkan username
            // Menggunakan library Glide atau Picasso, Anda dapat menampilkan avatar
            // menggunakan URL avatar dari user
            Glide.with(binding.avatarImageView.context)
                .load(user.avatarUrl)
                .into(binding.avatarImageView)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem.login == newItem.login // Sesuaikan dengan properti yang unik di item Anda
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem // Anda dapat membandingkan keseluruhan item jika kontennya sama
            }
        }
    }
}