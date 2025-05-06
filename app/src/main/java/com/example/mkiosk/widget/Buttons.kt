package com.example.mkiosk.widget

import android.Manifest
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mkiosk.R
import com.example.mkiosk.data.DataStorage.grant
import com.example.mkiosk.ui.theme.Typography
import com.example.mkiosk.ui.theme.mainColorScheme
import com.example.mkiosk.util.Changer
import com.example.mkiosk.util.PermissionListen
import com.example.mkiosk.util.Util.accountCounter
import com.example.mkiosk.util.Util.toast
import com.gun0912.tedpermission.normal.TedPermission
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Buttons(val idChanger: Changer<String>) {
    @Composable
    fun PermissionBtn(grantedChanger: Changer<Boolean>) {
        val context = LocalContext.current
        ElevatedButton (onClick = {
            TedPermission.create()
                .setPermissionListener(PermissionListen(context, grantedChanger))
                .setPermissions(Manifest.permission.CAMERA).setDeniedMessage(R.string.denied_msg).setGotoSettingButton(true).check()

            CoroutineScope(Dispatchers.IO).launch { grant(context) }
        }, modifier = Modifier
            .fillMaxWidth(0.25f)
            .padding(15.dp), shape = RectangleShape, colors = ButtonDefaults.buttonColors(containerColor = mainColorScheme.primary, contentColor = mainColorScheme.onPrimary) ) {
            Text(stringResource(R.string.grant_permission), style = Typography.bodyMedium)
        }

    }
    @Composable
    fun LogOutBtn() {
        val context = LocalContext.current
        ElevatedButton(onClick = {
            idChanger("")
            context.toast(R.string.logged_out)
        },  modifier = Modifier
            .fillMaxWidth(0.25f)
            .padding(horizontal = 15.dp, vertical = 7.dp), shape = RectangleShape, colors = ButtonDefaults.buttonColors(containerColor = mainColorScheme.secondary, contentColor = mainColorScheme.onPrimary)) {
            Text(stringResource(R.string.logout), style = Typography.bodyMedium)
        }
    }

    @Composable
    fun ApplyBtn(id: String, applyChanger: Changer<Boolean>) {
        val context = LocalContext.current
        ElevatedButton (onClick = {
            if ((accountCounter[id] ?: 0) < 2) {
                applyChanger(true)
            } else {
                context.toast(R.string.login_maximum)
            }
        }, modifier = Modifier
            .fillMaxWidth(0.25f)
            .padding(horizontal = 15.dp), shape = RectangleShape, colors = ButtonDefaults.buttonColors(containerColor = mainColorScheme.primary, contentColor = mainColorScheme.onPrimary) ) {
            Text(stringResource(R.string.sing_apply), style = Typography.bodyMedium)
        }
    }
}