package com.example.mkiosk

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mkiosk.data.DataStorage.readSongs
import com.example.mkiosk.ui.theme.MkioskTheme
import com.example.mkiosk.ui.theme.mainColorScheme
import com.example.mkiosk.util.KioskReceiver
import com.example.mkiosk.util.Util.accountMap
import com.example.mkiosk.util.Util.editingSong
import com.example.mkiosk.util.Util.songList
import com.example.mkiosk.widget.AdminCard
import com.example.mkiosk.widget.AppBar.CustomAppBar
import com.example.mkiosk.widget.InputWindow.AdminLogin
import com.example.mkiosk.widget.InputWindow.EditDialog
import com.example.mkiosk.widget.InputWindow.SongDialog
import com.example.mkiosk.widget.SongCard
import com.example.mkiosk.widget.VerifySection
import kotlinx.coroutines.runBlocking
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

var cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MkioskTheme {
                App()
            }

        }
        val dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val adminName = ComponentName(this, KioskReceiver::class.java)
        dpm.setLockTaskPackages(adminName, arrayOf(packageName))

        startLockTask()

        runBlocking { readSongs(this@MainActivity) }
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Preview(showSystemUi = true, device = "id:pixel_tablet")
    @Composable
    fun App() {
        var (id, idChanger) = rememberSaveable { mutableStateOf("") }
        var (applyDialog, applyChanger) = remember { mutableStateOf(false) }
        var (songs, songChanger) = remember { mutableStateOf(songList) }
        var (editDialog, editChanger) = remember { mutableStateOf(false) }
        var (admin, adminLogin) = rememberSaveable { mutableStateOf(false)}
        var (adminDialog, adminChanger) = remember { mutableStateOf(false)}

        Surface(modifier = Modifier.fillMaxSize().background(mainColorScheme.onPrimary)) {
            Scaffold(modifier = Modifier.fillMaxSize(),
                topBar = { CustomAppBar(id.isNotEmpty(), admin, adminLogin, adminChanger, this) }
            ) { innerPadding -> // topBar와 bottomBar를 가리지 않음

                if (applyDialog) SongDialog(id, applyChanger, songChanger)
                if (editDialog) EditDialog(id, editChanger, editingSong, songChanger)
                if (adminDialog) AdminLogin(adminLogin, adminChanger)

                Row(modifier = Modifier.padding(innerPadding)) {
                    VerifySection(id, idChanger, applyChanger, admin)

                    VerticalDivider(
                        modifier = Modifier.fillMaxHeight(),
                        thickness = 5.dp,
                        color = mainColorScheme.tertiary
                    )

                    LazyColumn(modifier=Modifier.fillMaxSize()) {
                        itemsIndexed(items = songs, key = { _, song -> song.id}) { index, song ->
                            if (admin) {
                                AdminCard(index+1, song, songChanger, Modifier.animateItemPlacement())
                            } else {
                                SongCard(index+1, id, song, songChanger, editChanger, Modifier.animateItemPlacement())
                            }
                        }
                    }
                }
            }
        }
    }
}



