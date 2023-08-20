package com.virua.simpleviralgamesassignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.widget.ImageView
//import kotlinx.android.synthetic.main.item_dog.view.*

//class DogsAdapter {
//}

class DogsAdapter(private var imageUrls: List<String>) : RecyclerView.Adapter<DogsAdapter.DogViewHolder>() {

    class DogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dogImageView: ImageView = itemView.findViewById(R.id.dogImageView)
        fun bind(imageUrl: String) {
            Glide.with(itemView.context).load(imageUrl).into(dogImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_dog, parent, false)
        return DogViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val imageUrl = imageUrls[position]
        holder.bind(imageUrl)
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }
    fun setItems(imageList: List<String>) {
        imageUrls=imageList
        notifyDataSetChanged()
    }
}