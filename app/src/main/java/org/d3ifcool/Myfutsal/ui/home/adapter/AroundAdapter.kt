package org.d3ifcool.Myfutsal.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3ifcool.Myfutsal.data.model.Around
import org.d3ifcool.Myfutsal.databinding.ItemAroundBinding

class AroundAdapter
    (private var items: ArrayList<Around>, var handler: (Around) -> Unit)
    : RecyclerView.Adapter<AroundAdapter.AroundViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setListData(data : ArrayList<Around>){
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AroundViewHolder(
        ItemAroundBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: AroundViewHolder, position: Int)= with(holder){
        bind(items[position])
        this.binding.root.setOnClickListener {
            handler(items[position])
        }
    }

    override fun getItemCount() = items.size

    class AroundViewHolder(var binding: ItemAroundBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(ground: Around) = with(binding) {

            Glide.with(this.root)
                .load(ground.image)
                .into(ivAround)
        }
    }
}