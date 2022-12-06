package org.d3ifcool.Myfutsal.ui.book

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.d3ifcool.Myfutsal.R
import org.d3ifcool.Myfutsal.data.model.Book
import org.d3ifcool.Myfutsal.data.provider.book.BookProvider
import org.d3ifcool.Myfutsal.databinding.FragmentBookBinding
import org.d3ifcool.Myfutsal.ui.book.booking.BookingActivity
import org.d3ifcool.Myfutsal.utils.BaseFragment

class BookFragment : BaseFragment() {

    private lateinit var binding : FragmentBookBinding
    private lateinit var bookAdapter: BookAdapter
    private var arrayOfBook = arrayListOf<Book>()

    private val viewModel: BookViewModel by lazy {
        ViewModelProvider(this)[BookViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBookBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkBooking()
        successFavoriteList()
        observeData()

        binding.apply {

            btnRequest.setOnClickListener{
                val intent = Intent(activity, BookingActivity::class.java)
                startActivity(intent)
            }
        }
    }

    @SuppressLint("Recycle")
    fun successFavoriteList() {
        bookAdapter = BookAdapter(arrayOfBook){

            bookAdapter.setOnClickListener( object : BookAdapter.OnClickListener{

                override fun onDelete(position: Int, book: Book) {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Konfirmasi Pembatalan")
                        .setMessage("Yakin ingin membatalkan sewa untuk lapangan ini ?")
                        .setNegativeButton("Batal") { _, _ ->
                        }
                        .setPositiveButton("Hapus") { _, _ ->
                            BookProvider().removeItemFromBook(
                                this@BookFragment,
                                book.bookId
                            )
                            showProgressDialog()
                        }.show()
                }
            })

        }
        with(binding.rvFavoriteList){
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = bookAdapter
        }
    }

    private fun observeData(){
        viewModel.initData().observe(viewLifecycleOwner) {
            bookAdapter.setListData(it)
        }
    }

    private fun checkBooking() {
        showProgressDialog()
        BookProvider().checkEmptyFavorite(this@BookFragment)
    }

    fun ifBookListIsEmpty() {
        hideProgressDialog()
        binding.rvFavoriteList.visibility = View.VISIBLE
        binding.frameEmptyFavorit.visibility = View.GONE
    }

    fun itemRemovedSuccess() {
        hideProgressDialog()
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.msg_item_removed_successfully),
            Toast.LENGTH_SHORT
        ).show()
        observeData()
    }

}