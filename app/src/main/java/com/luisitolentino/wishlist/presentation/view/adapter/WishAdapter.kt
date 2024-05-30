package com.luisitolentino.wishlist.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.luisitolentino.wishlist.R
import com.luisitolentino.wishlist.databinding.ItemWishBinding
import com.luisitolentino.wishlist.domain.model.Wish

class WishAdapter(
    private val onWishClickListener: (Wish) -> Unit,
    private val onItemMenuClickListener: (Wish) -> Unit
) : androidx.recyclerview.widget.ListAdapter<Wish, WishAdapter.WishViewHolder>(differCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): WishViewHolder {
        val binding = ItemWishBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WishViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WishViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class WishViewHolder(private val binding: ItemWishBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(wish: Wish) {
            binding.apply {
                textTitle.text = wish.title
                textStatus.text = wish.status
                // Se o wish.image for uma URL ou URI, você precisará de uma biblioteca como Glide ou Picasso para carregar a imagem
                // Glide.with(imageWish.context).load(wish.image).placeholder(R.drawable.ic_placeholder).into(imageWish)
                imageWish.setImageResource(R.drawable.ic_placeholder) // Temporário, substitua com lógica de carregamento de imagem
                iconEdit.setOnClickListener { onItemMenuClickListener(wish) }
                root.setOnClickListener { onWishClickListener(wish) }
            }
        }
    }

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<Wish>() {
            override fun areItemsTheSame(oldItem: Wish, newItem: Wish): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Wish, newItem: Wish): Boolean {
                return oldItem == newItem
            }
        }
    }
}
