package com.example.mkiosk.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mkiosk.MainActivity
import com.example.mkiosk.R
import com.example.mkiosk.data.DataStorage.PASSWORD
import com.example.mkiosk.data.DataStorage.changePW
import com.example.mkiosk.data.DataStorage.storeSongs
import com.example.mkiosk.ui.theme.Typography
import com.example.mkiosk.ui.theme.mainColorScheme
import com.example.mkiosk.util.Changer
import com.example.mkiosk.util.Util.toast
import kotlinx.coroutines.runBlocking

object AppBar {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CustomAppBar(isLogined: Boolean, activity: MainActivity, isAdmin: Boolean, adminChanger: Changer<Boolean>, dialogChanger: Changer<Boolean>, passwordChanger: Changer<Boolean>) {
        val context = LocalContext.current
        TopAppBar(
            title = { Row {
                Image(painter = painterResource(id = R.drawable.logo), contentDescription = stringResource(R.string.logo), modifier = Modifier.size(50.dp))
                Text(stringResource(R.string.app_name), color = mainColorScheme.onPrimary, style = Typography.titleLarge, modifier = Modifier.alignByBaseline().offset(x = 13.dp))
                Text(stringResource(R.string.made_by), color = mainColorScheme.tertiary, style = Typography.bodySmall, modifier = Modifier.alignByBaseline().offset(x = 26.dp, y = 0.dp))
            } }, colors = topAppBarColors(containerColor = mainColorScheme.secondary),
            actions = {
                Button(onClick = {
                    if (isLogined) {
                        context.toast(R.string.admin_must_logout)
                    } else if (!isAdmin) {
                        dialogChanger(true)
                    } else {
                        adminChanger(false)
                    }
                }) { Text(stringResource(if (isAdmin) R.string.admin_logout else R.string.admin_button)) }
                IconButton(onClick = {
                    if (isAdmin) {
                        runBlocking {
                            storeSongs(context)
                            changePW(context, PASSWORD)
                        }
                        activity.stopLockTask()
                        activity.finishAndRemoveTask()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        tint = mainColorScheme.onPrimary,
                        contentDescription = stringResource(R.string.close_icon),
                        modifier = Modifier.size(100.dp)
                    )
                }
                if (isAdmin) {
                    IconButton(onClick = {
                        passwordChanger(true)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Lock,
                            tint = mainColorScheme.onPrimary,
                            contentDescription = stringResource(R.string.password_change),
                            modifier = Modifier.size(100.dp)
                        )
                    }
                }
            })
    }
}