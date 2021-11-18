package com.example.ma_project.repository

import com.example.ma_project.domain.Hotel
import com.example.ma_project.domain.Reservation

class DummyRepository : Repository {
    companion object {
        val hotels = DummyReservations.HOTELS
        val reservations = DummyReservations.ITEMS
    }
    override fun createReservation(reservation: Reservation) {
        reservations.add(reservation)
    }

    override fun updateReservation(reservation: Reservation) {
        val i: Int = reservations.indexOfFirst {r -> r.id == reservation.id}
        reservations[i] = reservation
    }

    override fun deleteReservation(reservationId: Int) {
        val i: Int = reservations.indexOfFirst {r -> r.id == reservationId}
        reservations.removeAt(i)
    }

    override fun getAllReservations(): MutableList<Reservation> {
        return reservations
    }

    override fun getAllHotels(): MutableList<Hotel> {
        return hotels
    }
}