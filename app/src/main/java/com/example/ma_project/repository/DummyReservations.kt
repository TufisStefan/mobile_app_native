package com.example.ma_project.repository

import com.example.ma_project.domain.*
import java.util.*
import kotlin.collections.ArrayList

object DummyReservations {

    val ITEMS: MutableList<Reservation> = ArrayList()

    val HOTELS: MutableList<Hotel> = ArrayList()

    val ITEM_MAP: MutableMap<String, Reservation> = HashMap()

    private const val COUNT = 10

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createDummyItem(i))
            addHotel(createDummyHotel(i))
        }
    }

    private fun createDummyHotel(i: Int): Hotel {
        return Hotel(i,"Hotel $i", "City $i", 10.0 * i)
    }

    private fun addHotel(hotel: Hotel) {
        HOTELS.add(hotel)
    }

    private fun addItem(item: Reservation) {
        ITEMS.add(item)
        ITEM_MAP[item.id.toString()] = item
    }

    private fun createDummyItem(position: Int): Reservation {
        val hotel = Hotel(position, "Hotel $position","City $position", 10.0 * position)
        val client = Client(position, "Client $position", "Password$position")
        val values = MealPlan.values()
        val mealPlan = values[(0..3).shuffled().first()]
        return Reservation(
            position,
            "$position.12.2021-${position + 5}.12.2021",
            5 * hotel.pricePerRoom,
            position,
            hotel,
            client,
            mealPlan,
            "All good",
            ReservationStatus.PENDING
        )
    }
}
