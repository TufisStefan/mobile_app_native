package com.example.ma_project.repository

import com.example.ma_project.domain.Hotel
import com.example.ma_project.domain.Reservation

interface Repository {
    fun createReservation(reservation: Reservation)

    fun updateReservation(reservation: Reservation)

    fun deleteReservation(reservationId: Int)

    fun getAllReservations():MutableList<Reservation>

    fun getAllHotels():MutableList<Hotel>
}