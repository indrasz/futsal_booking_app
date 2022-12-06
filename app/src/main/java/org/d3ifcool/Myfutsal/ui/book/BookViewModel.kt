package org.d3ifcool.Myfutsal.ui.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.d3ifcool.Myfutsal.data.model.Book
import org.d3ifcool.Myfutsal.data.provider.book.BookProvider

class BookViewModel  : ViewModel(){
    private val fireStore = BookProvider()

    fun initData() : LiveData<ArrayList<Book>> {
        val mutableData = MutableLiveData<ArrayList<Book>>()

        fireStore.getBookList().observeForever{ bookList ->
            mutableData.value = bookList
        }

        return mutableData
    }
}