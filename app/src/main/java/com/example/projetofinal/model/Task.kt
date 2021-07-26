package com.example.projetofinal.model

data class Task(
    val jogo: String,
    val description: String,
    val hours: String,
    val date: String,
    val id: Int = 0
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Task

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}