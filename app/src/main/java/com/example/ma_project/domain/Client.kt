package com.example.ma_project.domain

import java.io.Serializable

data class Client(
    val id: Int,
    val username: String,
    val password: String
) : Serializable