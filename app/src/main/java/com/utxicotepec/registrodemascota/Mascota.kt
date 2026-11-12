package com.utxicotepec.registrodemascota

data class Mascota(
    val nombreMascota: String,
    val nombreDuenio: String,
    val peso: Double,
    val fechaNacimiento: Long,
    val especie: String
)