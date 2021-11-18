package com.example.ma_project.domain

data class Reservation(
    val id: Int,
    var period: String,
    val price: Double,
    var persons: Int,
    val hotel:Hotel,
    val client: Client?,
    var mealPlan: MealPlan,
    var observations: String,
    val status: ReservationStatus
)
