package org.d3ifcool.Myfutsal.ui.book.booking

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import org.d3ifcool.Myfutsal.R
import org.d3ifcool.Myfutsal.data.model.Book
import org.d3ifcool.Myfutsal.data.provider.book.BookProvider
import org.d3ifcool.Myfutsal.data.provider.user.UserProvider
import org.d3ifcool.Myfutsal.databinding.ActivityBookingBinding
import org.d3ifcool.Myfutsal.ui.book.BookFragment
import org.d3ifcool.Myfutsal.utils.BaseActivity
import org.d3ifcool.Myfutsal.utils.Constants
import java.text.SimpleDateFormat
import java.util.*

class BookingActivity : BaseActivity() {

    private lateinit var binding : ActivityBookingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val rentalHours = resources.getStringArray(R.array.rental_hours)
        val ground = resources.getStringArray(R.array.ground_name_array)

        binding.apply {
            edtDate.transformIntoDatePicker(this@BookingActivity, "MM/dd/yyyy")
            edtDate.transformIntoDatePicker(this@BookingActivity, "MM/dd/yyyy", Date())

            val adapter = ArrayAdapter(this@BookingActivity,
                android.R.layout.simple_spinner_item, rentalHours)
            edtTime.adapter = adapter

            val adapterGround = ArrayAdapter(this@BookingActivity,
                android.R.layout.simple_spinner_item, ground)
            edtGround.adapter = adapterGround

            btnBook.setOnClickListener {
                if(validateCreationDetails())
                    showProgressDialog()
                    bookingDetail()
            }

        }
    }

    private fun validateCreationDetails() : Boolean{
        return when {

            TextUtils.isEmpty(binding.edtDate.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_date), true)
                false
            }
            else -> {
                true
            }
        }
    }

    private fun bookingDetail() {

        val username =
            this.getSharedPreferences(Constants.MYFUTSAL_PREFERENCES, Context.MODE_PRIVATE)
                .getString(Constants.LOGGED_IN_USERNAME, "")!!
        val book = Book(
            UserProvider().getCurrentUserID(),
            username,
            binding.edtDate.text.toString().trim { it <= ' ' },
            binding.edtTime.selectedItem.toString(),
            binding.edtGround.selectedItem.toString(),
            "PENDING"
        )
        BookProvider().bookingRequest(this@BookingActivity, book)
    }

    fun bookUploadSuccess() {
        hideProgressDialog()
        Toast.makeText(
            this@BookingActivity,
            resources.getString(R.string.booking_success_message),
            Toast.LENGTH_SHORT
        ).show()
        val intent = Intent(this, BookFragment::class.java)
        startActivity(intent)
        finish()
    }

    private fun EditText.transformIntoDatePicker(context: Context, format: String, minDate: Date? = null) {
        isFocusableInTouchMode = false
        isClickable = true
        isFocusable = false

        val myCalendar = Calendar.getInstance()
        val datePickerOnDataSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val sdf = SimpleDateFormat(format, Locale.UK)
                setText(sdf.format(myCalendar.time))
            }

        setOnClickListener {
            DatePickerDialog(
                context, datePickerOnDataSetListener, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).run {
                minDate?.time?.also { datePicker.minDate = it }
                show()
            }
        }
    }
}