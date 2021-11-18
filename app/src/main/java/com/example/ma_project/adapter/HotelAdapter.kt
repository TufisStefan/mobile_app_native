package com.example.ma_project.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ma_project.HotelDetailActivity
import com.example.ma_project.R
import com.example.ma_project.domain.Hotel
import com.example.ma_project.repository.DummyRepository
import com.example.ma_project.repository.Repository

class HotelAdapter() : RecyclerView.Adapter<HotelAdapter.ViewHolder>() {
    private val dummyRepository: Repository = DummyRepository()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.hotel_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hotel: Hotel = dummyRepository.getAllHotels()[position]
        holder.hotelName.text = hotel.name
        val price = "${hotel.pricePerRoom.toString()}/Night"
        holder.pricePerNight.text = price

    }

    override fun getItemCount(): Int {
        return dummyRepository.getAllHotels().size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val hotelName: TextView = view.findViewById(R.id.hotelName)
        val pricePerNight: TextView = view.findViewById(R.id.pricePerNight)

        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                val context = view.context
                val intent = Intent(context, HotelDetailActivity::class.java).apply {
                    putExtra("POSITION", position.toString())
                }
                context.startActivity(intent)
            }
        }
    }
}