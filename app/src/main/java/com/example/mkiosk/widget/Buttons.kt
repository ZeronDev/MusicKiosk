package com.example.mkiosk.widget

import android.Manifest
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
    fun ButtonTemplate(stringId: Int, icon: Int? = null, modifier: Modifier = Modifier, function: (Context) -> Unit) {
        val context = LocalContext.current
        Button (onClick = {
            function(context)
        }, shape = RoundedCornerShape(10.dp), modifier = modifier
            .fillMaxWidth(0.4f).height(70.dp)
            .padding(horizontal = 25.dp, vertical = 7.dp), colors = ButtonDefaults.buttonColors(containerColor = Color.White), border = BorderStroke(1.dp, Color.LightGray) ) {
            if (icon != null) {
                Icon(painterResource(icon), "", Modifier.size(40.dp), tint = mainColorScheme.primary)
            }
            Text(stringResource(stringId), style = Typography.bodyMedium, color = Color.DarkGray)
        }
    }
    @Composable
    fun PermissionBtn(grantedChanger: Changer<Boolean>) {
        ButtonTemplate(R.string.grant_permission) { context ->
            TedPermission.create()
                .setPermissionListener(PermissionListen(context, grantedChanger))
                .setPermissions(Manifest.permission.CAMERA).setDeniedMessage(R.string.denied_msg).setGotoSettingButton(true).check()

            CoroutineScope(Dispatchers.IO).launch { grant(context) }
        }
    }
//    @Composable
//    fun ButtonList() {
//        ButtonTemplate(R.string.logout, R.drawable.logout) { context ->
//            idChanger("")
//            context.toast(R.string.logged_out)
//        }
//    }

    @Composable
    fun ButtonList(id: String, applyChanger: Changer<Boolean>) {
        Row {
            ButtonTemplate(R.string.logout, R.drawable.logout, Modifier.weight(1f)) { context ->
                idChanger("")
                context.toast(R.string.logged_out)
            }
            ButtonTemplate(R.string.sing_apply, R.drawable.add, Modifier.weight(1f)) { context ->
                if ((accountCounter[id] ?: 0) < 2) {
                    applyChanger(true)
                } else {
                    context.toast(R.string.login_maximum)
                }
            }
        }

    }
}