package com.example.ma_project.domain

import java.io.Serializable

data class Hotel(
    val id: Int,
    val name: String,
    val city: String,
    val pricePerRoom: Double
) : Serializable
