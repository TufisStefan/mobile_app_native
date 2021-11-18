package com.example.ma_project.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ma_project.R
import com.example.ma_project.ReservationDetailActivity
import com.example.ma_project.domain.Reservation
import com.example.ma_project.repository.DummyRepository
import com.example.ma_project.repository.Repository

class ReservationAdapter() : RecyclerView.Adapter<ReservationAdapter.ViewHolder>() {
    private val dummyRepository: Repository = DummyRepository()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.reservation_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reservation: Reservation = dummyRepository.getAllReservations()[position]
        holder.hotelName.text = reservation.hotel.name
        holder.period.text = reservation.period

    }

    override fun getItemCount(): Int {
        return dummyRepository.getAllReservations().size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val hotelName: TextView = view.findViewById(R.id.hotelName)
        val period: TextView = view.findViewById(R.id.reservationPeriod)

        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                val context = view.context
                val intent = Intent(context, ReservationDetailActivity::class.java).apply {
                    putExtra("POSITION", position.toString())
                }
                context.startActivity(intent)
            }
        }
    }
}