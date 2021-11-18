package com.example.ma_project

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.example.ma_project.domain.Hotel
import com.example.ma_project.domain.MealPlan
import com.example.ma_project.domain.Reservation
import com.example.ma_project.domain.ReservationStatus
import com.example.ma_project.repository.DummyRepository
import com.example.ma_project.repository.Repository
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class HotelDetailActivity : FragmentActivity() {

    private val dummyRepository: Repository = DummyRepository()
    private var currentHotel: Hotel? = null

    var hotelNameTextView: TextView? = null
    var priceTextView: TextView? = null
    var periodEditText: EditText? = null
    var personsEditText: EditText? = null
    var notesEditText: TextView? = null
    var mealPlanSpinner: Spinner? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_reservation)
        val position = intent!!.getStringExtra("POSITION")?.toIntOrNull()
        currentHotel = dummyRepository.getAllHotels()[position!!]

        hotelNameTextView = findViewById<View>(R.id.hotelTextView) as TextView
        var text = "${currentHotel?.name}  ${currentHotel?.city}"
        hotelNameTextView!!.text = text

        priceTextView = findViewById<View>(R.id.priceTextView) as TextView
        text = "${currentHotel?.pricePerRoom.toString()}/Night"
        priceTextView!!.text = text

        periodEditText = findViewById<EditText>(R.id.periodEdit)
        personsEditText = findViewById<EditText>(R.id.personsEdit)
        notesEditText = findViewById<EditText>(R.id.notesEdit)

        val mealOptions = resources.getStringArray(R.array.mealOptions)
        mealPlanSpinner = findViewById(R.id.mealSpinner)
        val adapter = ArrayAdapter(this, R.layout.spinner_item, mealOptions)
        mealPlanSpinner!!.adapter = adapter

        val confirmButton = findViewById<View>(R.id.confirmButton)
        confirmButton.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            val reservation = getCurrentReservation()
            if(reservation != null) {
                val dialog = confirmPopup(reservation)
                builder.setMessage("Confirm reservation?")
                    .setPositiveButton("Yes", dialog)
                    .setNegativeButton("No", dialog)
                    .show()
            }
            else{
                val dialog = errorPopup()
                builder.setMessage("Invalid input")
                    .setNeutralButton("OK", dialog)
                    .show()
            }
        }

        val bottomNavigation: BottomNavigationView =
            findViewById<View>(R.id.bottomNavigationView) as BottomNavigationView
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.create_reservation -> launchCreateActivity()
                R.id.my_reservations -> launchListActivity()
            }
            true
        }
    }
    private fun errorPopup(): DialogInterface.OnClickListener {
        return DialogInterface.OnClickListener{_, option ->
            run{
                when(option){
                    DialogInterface.BUTTON_NEUTRAL -> null
                }
            }
        }
    }


    private fun launchListActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun launchCreateActivity() {
        val intent = Intent(this, HotelListActivity::class.java)
        startActivity(intent)
    }


    private fun getCurrentReservation(): Reservation? {
        val period = periodEditText!!.text.toString().trim()
        val persons = personsEditText!!.text.toString().toIntOrNull()
        val regex =
            Regex("^([1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.\\d{4}-([1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.\\d{4}")

        if (!period.matches(regex) || persons!! <= 0 || persons >10) {
            return null
        }
        val dates = period.split('-')
        val formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu", Locale.ENGLISH)
        val from = LocalDate.parse(dates[0], formatter)
        val to = LocalDate.parse(dates[1], formatter)
        val noDays = ChronoUnit.DAYS.between(from, to)
        val notes = notesEditText!!.text.toString()
        val mealPlan = MealPlan.valueOf(mealPlanSpinner!!.selectedItem.toString())
        val reservation = Reservation(
            dummyRepository.getAllReservations().size + 1,
            period,
            currentHotel!!.pricePerRoom * noDays,
            persons!!,
            currentHotel!!,
            null,
            mealPlan,
            notes,
            ReservationStatus.PENDING
        )
        return reservation
    }


    private fun confirmPopup(reservation: Reservation?): DialogInterface.OnClickListener {
        return DialogInterface.OnClickListener { _, option ->
            run {
                when (option) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        dummyRepository.createReservation(reservation!!)
                        launchListActivity()
                    }
                    DialogInterface.BUTTON_NEGATIVE -> null
                }
            }

        }
    }

}
