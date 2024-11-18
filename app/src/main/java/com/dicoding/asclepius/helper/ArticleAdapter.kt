package com.dicoding.asclepius.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.ArticleItem

class ArticleAdapter : ListAdapter<ArticleItem, ArticleAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val articleItem = getItem(position)
        holder.bind(articleItem)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.articleTitle)
        private val tvDescription: TextView = itemView.findViewById(R.id.articleDescription)
        private val imgArticle: ImageView = itemView.findViewById(R.id.articleImage)

        fun bind(articleItem: ArticleItem) {
            tvTitle.text = articleItem.title
            tvDescription.text = articleItem.description
            // Load image using Glide
            if (!articleItem.imageUrl.isNullOrEmpty()) {
                Glide.with(itemView.context)
                    .load(articleItem.imageUrl)
                    .placeholder(R.drawable.ic_place_holder)
                    .error(R.drawable.ic_place_holder)
                    .into(imgArticle)
            } else {
                imgArticle.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_place_holder))
            }

            // Handle click on title to open URL
            tvTitle.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(articleItem.url))
                itemView.context.startActivity(intent)
            }
        }
    }

    // DiffUtil callback to optimize RecyclerView updates
    class DiffCallback : DiffUtil.ItemCallback<ArticleItem>() {
        override fun areItemsTheSame(oldItem: ArticleItem, newItem: ArticleItem): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: ArticleItem, newItem: ArticleItem): Boolean {
            return oldItem == newItem
        }
    }
}
