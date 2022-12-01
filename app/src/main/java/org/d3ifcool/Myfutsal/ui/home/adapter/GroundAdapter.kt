package org.d3ifcool.Myfutsal.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3ifcool.Myfutsal.data.model.Ground
import org.d3ifcool.Myfutsal.databinding.ItemGroundBinding

class GroundAdapter
    (private var items: ArrayList<Ground>, var handler: (Ground) -> Unit)
        : RecyclerView.Adapter<GroundAdapter.PopularSearchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PopularSearchViewHolder(
        ItemGroundBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: PopularSearchViewHolder, position: Int)= with(holder){
        bind(items[position])
        this.binding.root.setOnClickListener {
            handler(items[position])
        }
    }

    override fun getItemCount() = items.size

    class PopularSearchViewHolder(var binding: ItemGroundBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(ground: Ground) = with(binding) {

            Glide.with(this.root)
                .load(ground.image)
                .into(ivGround)
        }
    }
}