package com.utxicotepec.registrodemascota

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.utxicotepec.registrodemascota.ui.componentes.AntDialogInfo
import com.utxicotepec.registrodemascota.ui.theme.RegistroDeMascotaTheme

class MainActivity : ComponentActivity() {
    private var dialogInfo by mutableStateOf("")
    private var mascotaFilled: Mascota? by mutableStateOf(null)
    private var isClean by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroDeMascotaTheme {
                MainView(
                    modifier = Modifier,
                    isClean = isClean,
                    onCleaned = { isClean = false },
                    onError = { errorMsg ->
                        mascotaFilled = null
                        dialogInfo = errorMsg
                    },
                    onSave = { mascota ->
                        mascotaFilled = mascota
                        dialogInfo = mascotaFormatter(mascota)
                        Log.i("JGL", mascotaFormatter(mascota))
                    }
                )

                if (dialogInfo.isNotEmpty()) {
                    val titleRes = if (mascotaFilled != null) {
                        R.string.dialog_title
                    } else {
                        R.string.form_title
                    }
                    AntDialogInfo(
                        info = dialogInfo,
                        titleRes = titleRes,
                        onDismissRequest = { confirmed ->
                            dialogInfo = ""
                            if (confirmed && mascotaFilled != null) {
                                mascotaFilled = null
                                isClean = true
                            }
                        }
                    )
                }
            }
        }
    }
}
@Preview(showSystemUi = true)
@Composable
private fun LocalPreview() {
    MainPreview()
}
