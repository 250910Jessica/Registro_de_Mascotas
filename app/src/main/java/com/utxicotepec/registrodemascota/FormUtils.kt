package com.utxicotepec.registrodemascota

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun mascotaFormatter(mascota: Mascota): String {
    val result = StringBuilder()
    result.append("Nombre de la mascota: ${mascota.nombre}\n")
    result.append("Nombre del dueño: ${mascota.apellidos}\n")
    result.append("Peso: ${mascota.peso} kg\n")
    result.append("Fecha de nacimiento: ${convertMillisToDate(mascota.fechaNacimiento)}\n")
    result.append("Especie: ${mascota.especie}\n")
    result.append("Antecedentes: ${mascota.antecedentes}")
    return result.toString()
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

fun foundErrors(context: Context, name: String, owner: String, weight: String): String? {
    if (name.isBlank() || owner.isBlank() || weight.isBlank()) {
        return context.getString(R.string.form_invalid)
    }
    return null
}

fun simulateDelayLong() {
    Thread.sleep(1500)
}
