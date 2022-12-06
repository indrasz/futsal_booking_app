package org.d3ifcool.Myfutsal.data.provider.book

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import org.d3ifcool.Myfutsal.data.model.Book
import org.d3ifcool.Myfutsal.data.provider.user.UserProvider
import org.d3ifcool.Myfutsal.ui.book.BookFragment
import org.d3ifcool.Myfutsal.ui.book.booking.BookingActivity
import org.d3ifcool.Myfutsal.utils.Constants
import java.io.IOException

class BookProvider {

    private val mFireStore = FirebaseFirestore.getInstance()
    private val currentUserId = UserProvider()

    //untuk upload karya dari firebase
    fun bookingRequest(activity: BookingActivity, bookingInfo: Book) {

        try {
            mFireStore.collection(Constants.BOOK)
                .document()
                .set(bookingInfo, SetOptions.merge())
                .addOnSuccessListener {
                    activity.bookUploadSuccess()
                }
                .addOnFailureListener { e ->
                    activity.hideProgressDialog()
                    Log.e(
                        activity.javaClass.simpleName,
                        "Error while booking ground.",
                        e
                    )
                }
        } catch (e : IOException){
            e.printStackTrace()
        }
    }

    fun removeItemFromBook(fragment: Fragment, bookId: String) {

        // Cart items collection name
        mFireStore.collection(Constants.BOOK)
            .document(bookId) // cart id
            .delete()
            .addOnSuccessListener {

                // Notify the success result of the removed cart item from the list to the base class.
                when (fragment) {
                    is BookFragment -> {
                        fragment.itemRemovedSuccess()
                    }
                }
            }
            .addOnFailureListener { e ->

                // Hide the progress dialog if there is any error.
                when (fragment) {
                    is BookFragment -> {
                        fragment.hideProgressDialog()
                    }
                }
                Log.e(
                    fragment.javaClass.simpleName,
                    "Error while removing the item from the cart list.",
                    e
                )
            }
    }

    //untuk mendapatkan karya apa saja yang sudah dilike oleh user
    fun getBookList(): LiveData<ArrayList<Book>> {
        val fragment : Fragment = BookFragment()
        val bookData = MutableLiveData<ArrayList<Book>>()

        try {
            mFireStore.collection(Constants.BOOK)
                .whereEqualTo(Constants.USER_ID, currentUserId.getCurrentUserID())
                .get()
                .addOnSuccessListener{ document ->

                    Log.e(fragment.javaClass.simpleName, document.documents.toString())

                    val list: ArrayList<Book> = ArrayList()

                    for (i in document.documents) {

                        val book = i.toObject(Book::class.java)!!
                        book.bookId = i.id

                        list.add(book)
                    }

                    bookData.value = list

                }

                .addOnFailureListener { e ->
                    Log.e(fragment.javaClass.simpleName, "Error while getting favorite list items.", e)
                }

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return bookData
    }

    fun checkEmptyFavorite(fragment: BookFragment) {

        mFireStore.collection(Constants.BOOK)
            .whereEqualTo(Constants.USER_ID, currentUserId.getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->

                Log.e(fragment.javaClass.simpleName, document.documents.toString())

                if (document.documents.size > 0) {
                    fragment.ifBookListIsEmpty()
                } else {
                    fragment.hideProgressDialog()
                }
            }
            .addOnFailureListener { e ->
                // Hide the progress dialog if there is an error.
                fragment.hideProgressDialog()

                Log.e(
                    fragment.javaClass.simpleName,
                    "Error while checking the existing favorite list.",
                    e
                )

            }
    }
}