package com.example.mkiosk.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
    fun CustomAppBar(
        isLogined: Boolean,
        activity: MainActivity,
        isAdmin: Boolean,
        adminChanger: Changer<Boolean>,
        dialogChanger: Changer<Boolean>,
        passwordChanger: Changer<Boolean>
    ) {
        val context = LocalContext.current
        Surface(shadowElevation = 8.dp) {
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
//                Image(painter = painterResource(id = R.drawable.logo), contentDescription = stringResource(R.string.logo), modifier = Modifier.size(50.dp))
                        Icon(
                            painterResource(R.drawable.play_circle),
                            stringResource(R.string.logo),
                            modifier = Modifier.size(45.dp),
                            tint = mainColorScheme.primary
                        )
                        Text(
                            "뮤직박스",
                            color = mainColorScheme.primary,
                            style = Typography.bodyMedium,
                            modifier = Modifier
                                .padding(10.dp)
                        )
//                        Text(
//                            stringResource(R.string.made_by),
//                            color = mainColorScheme.tertiary,
//                            style = Typography.bodySmall,
//                            modifier = Modifier
//                                .alignByBaseline()
//                                .offset(x = 26.dp, y = 0.dp)
//                        )
                    }
                }, colors = topAppBarColors(containerColor = Color.White),
                actions = {
                    IconButton(onClick = {
                        if (isLogined) {
                            context.toast(R.string.admin_must_logout)
                        } else if (!isAdmin) {
                            dialogChanger(true)
                        } else {
                            adminChanger(false)
                        }
                    }) {
                        Icon(
                            painterResource(R.drawable.admin),
                            "admin",
                            modifier = Modifier.size(80.dp)
                        )
                    }
                    if (isAdmin) {
                        IconButton(onClick = {
                            runBlocking {
                                storeSongs(context)
                                changePW(context, PASSWORD)
                            }
                            activity.stopLockTask()
                            activity.finishAndRemoveTask()

                        }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = stringResource(R.string.close_icon),
                                modifier = Modifier.size(80.dp)
                            )
                        }
                    }

                    if (isAdmin) {
                        IconButton(onClick = {
                            passwordChanger(true)
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Lock,
                                contentDescription = stringResource(R.string.password_change),
                                modifier = Modifier.size(80.dp)
                            )
                        }
                    }
                })
        }
    }
}