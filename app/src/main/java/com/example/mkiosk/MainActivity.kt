package com.example.mkiosk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mkiosk.ui.theme.MkioskTheme
import com.example.mkiosk.ui.theme.Typography
import com.example.mkiosk.ui.theme.mainColorScheme
import com.example.mkiosk.widget.AppBar.CustomAppBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // 앱의 화면에 status bar(상태표시줄)과 navigation bar (네비게이션바) 까지 포함한다. 상태표시줄과 네비게이션바는 앱의 내용을 가리지 않기 위해 투명해짐
        setContent {
            MkioskTheme {
                App()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class) // 실험적인 기능 사용
@Composable
fun App() {

    Surface(modifier = Modifier.fillMaxSize().background(mainColorScheme.background)) {
        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = { CustomAppBar() }
        ) { innerPadding -> // topBar와 bottomBar를 가리지 않음
            Row(modifier = Modifier.padding(innerPadding)) {
                
            }
        }
    }
}


@Preview(showSystemUi = true, device = "id:pixel_tablet")
@Composable
fun MainPreview() {
    MkioskTheme {
        App()
    }
}