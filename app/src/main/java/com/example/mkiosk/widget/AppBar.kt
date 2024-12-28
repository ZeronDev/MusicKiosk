package com.example.mkiosk.widget

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mkiosk.R
import com.example.mkiosk.ui.theme.Typography
import com.example.mkiosk.ui.theme.mainColorScheme

object AppBar {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CustomAppBar() {
        TopAppBar(
            title = { Row {
                Text("♪ ", color = mainColorScheme.primary, style = Typography.titleLarge, modifier = Modifier.alignByBaseline()) //LOGO 대체
                Text("Music Kiosk", color = mainColorScheme.onPrimary, style = Typography.titleLarge, modifier = Modifier.alignByBaseline())
                Text("SICC X SMBS", color = mainColorScheme.tertiary, style = Typography.bodySmall, modifier = Modifier.alignByBaseline().offset(x = 13.dp, y = 0.dp))
            } }, colors = topAppBarColors(containerColor = mainColorScheme.secondary),
            actions = {
                Button(onClick = {}) { Text("관리자 모드") }
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        tint = mainColorScheme.onPrimary,
                        contentDescription = "닫기",
                        modifier = Modifier.size(100.dp)
                    )
                }
            })
    }
}