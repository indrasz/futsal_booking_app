package org.d3ifcool.Myfutsal.ui.book

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.d3ifcool.Myfutsal.data.model.Book
import org.d3ifcool.Myfutsal.databinding.ItemBookBinding

class BookAdapter (private var items: ArrayList<Book>, var handler: (Book) -> Unit) :
    RecyclerView.Adapter<BookAdapter.WishlistViewHolder>() {

    private var onClickListener: OnClickListener? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setListData(data : ArrayList<Book>){
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : WishlistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBookBinding.inflate(inflater, parent, false)
        return WishlistViewHolder(binding)
    }


    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) = with(holder) {
        bind(items[position])

        this.binding.root.setOnClickListener {
            handler(items[position])
            if (onClickListener != null) {
                onClickListener!!.onDelete(position, items[position])
            }
        }
    }

    override fun getItemCount() = items.size

    class WishlistViewHolder(
        var binding: ItemBookBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) = with(binding) {
            tvGround.text = book.groundRental
            tvTime.text = book.hoursRental
            tvDate.text = book.dateRental

        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
    interface OnClickListener {
        fun onDelete(position: Int, book: Book)
    }
}