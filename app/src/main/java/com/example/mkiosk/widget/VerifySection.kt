package com.example.mkiosk.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mkiosk.data.DataStorage.isGranted
import com.example.mkiosk.util.Changer
import com.example.mkiosk.widget.CameraUtil.CameraView

@Composable
fun VerifySection(id: String, idChanger: Changer<String>, applyChanger: Changer<Boolean>, isAdmin: Boolean) {

    val buttons = Buttons(idChanger)
    var (isGranted, grantedChanger) = rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) { grantedChanger(isGranted(context)) }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CameraView(idChanger, id.isNotEmpty(), isAdmin)

        if (isGranted) {
            if (id.isNotEmpty()) {
                Spacer(modifier = Modifier.fillMaxWidth(0.25f).height(25.dp))
                buttons.ApplyBtn(id, applyChanger)
                buttons.LogOutBtn()
            }
        } else {
            buttons.PermissionBtn(grantedChanger)
        }
    }
}