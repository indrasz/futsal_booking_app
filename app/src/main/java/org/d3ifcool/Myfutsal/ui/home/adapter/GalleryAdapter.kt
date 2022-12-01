package org.d3ifcool.Myfutsal.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3ifcool.Myfutsal.data.model.Around
import org.d3ifcool.Myfutsal.data.model.Gallery
import org.d3ifcool.Myfutsal.databinding.ItemAroundBinding
import org.d3ifcool.Myfutsal.databinding.ItemGalleryBinding

class GalleryAdapter
    (private var items: ArrayList<Gallery>, var handler: (Gallery) -> Unit)
    : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GalleryViewHolder(
        ItemGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int)= with(holder){
        bind(items[position])
        this.binding.root.setOnClickListener {
            handler(items[position])
        }
    }

    override fun getItemCount() = items.size

    class GalleryViewHolder(var binding: ItemGalleryBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(gallery: Gallery) = with(binding) {

            Glide.with(this.root)
                .load(gallery.image)
                .into(ivGallery)
        }
    }
}