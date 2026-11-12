package com.utxicotepec.registrodemascota.ui.componentes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.utxicotepec.registrodemascota.R
import com.utxicotepec.registrodemascota.ui.theme.RegistroDeMascotaTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Preview(showBackground = true)
@Composable
private fun AntTextFieldPreview() {
    RegistroDeMascotaTheme {
        AntTextField(Modifier,
            labelRes = R.string.hint_name,
            iconRes = R.drawable.ic_person,
            maxLengthRes = R.integer.name_max_length) {}
    }
}

@Preview(showBackground = true)
@Composable
private fun AntTfDatePreview() {
    RegistroDeMascotaTheme {
        AntTfDate(Modifier, R.string.hint_birth_date) { }
    }
}

@Composable
fun AntTextField(modifier: Modifier = Modifier,
                 labelRes: Int,
                 iconRes: Int,
                 maxLengthRes: Int? = null,
                 minValue: Int = 0,
                 errorRes: Int = R.string.supporting_required,
                 keyboardOptions: KeyboardOptions? = null,
                 singleLine: Boolean = true,
                 isRequired: Boolean = true,
                 isClean: Boolean = false,
                 paddingTop: Dp = dimensionResource(R.dimen.common_padding_min),
                 onValueChange: (String) -> Unit) {
    var textValue by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    val maxLength = if (maxLengthRes == null) null else integerResource(maxLengthRes)

    LaunchedEffect(isClean) {
        if (isClean) {
            textValue = ""
            isError = false
        }
    }

    OutlinedTextField(value = textValue,
        onValueChange = {
            if (maxLength == null) {
                textValue = it
            } else {
                if (it.length <= maxLength) {
                    textValue = it
                }
            }

            isError = it.trim().isEmpty()

            if (minValue > 0) {
                isError = (textValue.toIntOrNull() ?: 0) < minValue
            }

            onValueChange(textValue)
        },
        isError = isError,
        singleLine = singleLine,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = paddingTop),
        label = {
            Text(stringResource(labelRes),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis)
        },
        leadingIcon = {
            Icon(painter = painterResource(iconRes), contentDescription = null)
        },
        keyboardOptions = KeyboardOptions(
            capitalization = keyboardOptions?.capitalization ?: KeyboardCapitalization.Sentences,
            keyboardType = keyboardOptions?.keyboardType ?: KeyboardType.Text,
            imeAction = if (keyboardOptions == null ||
                keyboardOptions.imeAction == ImeAction.Default) ImeAction.Next
            else keyboardOptions.imeAction
        ),
        supportingText = {
            Row {
                if (isRequired) {
                    Text(if (isError) stringResource(errorRes)
                    else stringResource(R.string.supporting_required))
                }

                Spacer(Modifier.weight(1f))

                if (maxLength != null && minValue == 0) {
                    Text("${textValue.length}/$maxLength")
                }
            }
        })
}

@Composable
fun AntTfDate(modifier: Modifier,
              labelRes: Int,
              selectedDate: Long? = null,
              onShowModal: () -> Unit) {
    Box(modifier = modifier) {
        OutlinedTextField(
            value = selectedDate?.let { convertMillisToDate(it) } ?: "",
            onValueChange = {},
            readOnly = true,
            enabled = true,
            label = {
                Text(stringResource(labelRes),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis)
            },
            trailingIcon = {
                Icon(painterResource(R.drawable.ic_calendar_today), contentDescription = null)
            },
            modifier = Modifier.fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable { onShowModal() }
        )
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}