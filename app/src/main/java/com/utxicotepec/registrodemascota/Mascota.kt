package com.utxicotepec.registrodemascota

data class Mascota(
    val nombre: String,
    val apellidos: String,
    val peso: Int,
    val fechaNacimiento: Long,
    val especie: String,
    val antecedentes: String
)