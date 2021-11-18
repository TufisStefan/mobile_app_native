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
import com.example.ma_project.domain.MealPlan
import com.example.ma_project.domain.Reservation
import com.example.ma_project.repository.DummyRepository
import com.example.ma_project.repository.Repository
import com.google.android.material.bottomnavigation.BottomNavigationView

class ReservationDetailActivity : FragmentActivity() {

    private val dummyRepository: Repository = DummyRepository()
    private var currentReservation: Reservation? = null

    var hotelNameTextView: TextView? = null
    var priceTextView: TextView? = null
    var periodEditText: EditText? = null
    var personsEditText: EditText? = null
    var notesEditText: TextView? = null
    var mealPlanSpinner: Spinner? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reservation_detail)
        val position = intent!!.getStringExtra("POSITION")?.toIntOrNull()
        currentReservation = dummyRepository.getAllReservations()[position!!]

        hotelNameTextView = findViewById<View>(R.id.hotelTextView) as TextView
        var text = "${currentReservation?.hotel?.name}  ${currentReservation?.hotel?.city}"
        hotelNameTextView!!.text = text

        priceTextView = findViewById<View>(R.id.priceTextView) as TextView
        text = "${currentReservation?.price} - ${currentReservation?.status}"
        priceTextView!!.text = text

        periodEditText = findViewById<View>(R.id.periodEdit) as EditText
        periodEditText!!.setText(currentReservation?.period)

        personsEditText = findViewById<View>(R.id.personsEdit) as EditText
        personsEditText!!.setText(currentReservation?.persons.toString())

        notesEditText = findViewById<View>(R.id.notesEdit) as EditText
        notesEditText!!.setText(currentReservation?.observations)

        val mealOptions = resources.getStringArray(R.array.mealOptions)
        mealPlanSpinner = findViewById(R.id.mealSpinner)
        val adapter = ArrayAdapter(this, R.layout.spinner_item, mealOptions)
        mealPlanSpinner!!.adapter = adapter
        mealPlanSpinner!!.setSelection(adapter.getPosition(currentReservation!!.mealPlan.toString()))

        val deleteButton = findViewById<View>(R.id.deleteButton)
        deleteButton.setOnClickListener {
            val dialog = deletePopup()
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setMessage("Confirm delete?")
                .setPositiveButton("Yes", dialog)
                .setNegativeButton("No", dialog)
                .show()
        }

        val editButton = findViewById<View>(R.id.editButton)
        editButton.setOnClickListener {
            val reservation = getCurrentReservation()
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            if(reservation != null) {
                val dialog = updatePopup(reservation)
                builder.setMessage("Confirm update?")
                    .setPositiveButton("Yes", dialog)
                    .setNegativeButton("No", dialog)
                    .show()
            }
            else{
                val dialog = errorPopup()
                builder.setMessage("Invalid changes")
                    .setNeutralButton("OK", dialog)
                    .show()
            }
        }
        val bottomNavigation : BottomNavigationView = findViewById<View>(R.id.bottomNavigationView) as BottomNavigationView
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
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

    private fun launchCreateActivity(){
        val intent = Intent(this, HotelListActivity::class.java)
        startActivity(intent)
    }


    private fun getCurrentReservation(): Reservation? {
        val reservation: Reservation? = currentReservation
        reservation!!.observations = notesEditText!!.text.toString()
        reservation.period = periodEditText!!.text.toString()
        val personsString = personsEditText!!.text.toString()
        reservation.mealPlan = MealPlan.valueOf(mealPlanSpinner!!.selectedItem.toString())
        if (personsString != "") {
            reservation.persons = Integer.parseInt(personsEditText!!.text.toString())
        }
        val regex = Regex("^([1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.\\d{4}-([1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.\\d{4}$")
        if(reservation.period.matches(regex) && reservation.persons > 0){
            return reservation
        }
        return null
    }

    private fun deletePopup(): DialogInterface.OnClickListener {
        return DialogInterface.OnClickListener { _, option ->
            run {
                when (option) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        dummyRepository.deleteReservation(currentReservation!!.id)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    DialogInterface.BUTTON_NEGATIVE -> null
                }
            }
        }
    }

    private fun updatePopup(reservation: Reservation?): DialogInterface.OnClickListener {
        return DialogInterface.OnClickListener { _, option ->
            run {
                when(option) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        dummyRepository.updateReservation(reservation!!)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    DialogInterface.BUTTON_NEGATIVE -> null
                }
            }

        }
    }

}
