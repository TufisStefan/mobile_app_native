package com.example.ma_project

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.ma_project.adapter.HotelAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.hotel_item_list.*

class HotelListActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hotel_list)


        setupRecyclerView(hotel_item_list)
        val bottomNavigation :BottomNavigationView = findViewById<View>(R.id.bottomNavigationView) as BottomNavigationView
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.create_reservation -> launchCreateActivity()
                R.id.my_reservations -> launchListActivity()
            }
            true
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

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = HotelAdapter()
    }

}