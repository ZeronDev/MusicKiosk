package com.example.mkiosk.widget

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.mkiosk.R
import com.example.mkiosk.data.Song
import com.example.mkiosk.ui.theme.Typography
import com.example.mkiosk.ui.theme.mainColorScheme
import com.example.mkiosk.util.Changer
import com.example.mkiosk.util.Util.accountMap
import com.example.mkiosk.util.Util.recommendationMap
import com.example.mkiosk.util.Util.songList
import com.example.mkiosk.util.Util.toast
import java.util.UUID

object InputWindow {
    @Composable
    fun SongDialog(id: String, dialogChanger: Changer<Boolean>, songChanger: Changer<List<Song>>) {
        var artist by remember { mutableStateOf("") }
        var title by remember { mutableStateOf("") }
        val context = LocalContext.current
        Dialog(onDismissRequest = { dialogChanger(false) } ) {
            Card(colors = CardDefaults.cardColors(containerColor = mainColorScheme.onPrimary), modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .wrapContentSize()) {
                Column {
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp))
                    Column {
                        OutlinedTextField(value = artist, onValueChange = {artist = it}, modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 0.dp)
                            , textStyle = Typography.bodySmall, label = { Text("아티스트", color = mainColorScheme.primary, style = Typography.bodySmall ) }, singleLine = true,
                            placeholder = { Text("(필수X)", color = mainColorScheme.tertiary, style = Typography.bodySmall) }, colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = mainColorScheme.primary, focusedBorderColor = mainColorScheme.primary, unfocusedContainerColor = mainColorScheme.onPrimary, focusedContainerColor = mainColorScheme.onPrimary))
                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp))
                        OutlinedTextField(value = title, onValueChange = {title = it}, modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 0.dp)
                            , textStyle = Typography.bodySmall, label = { Text("노래", color = mainColorScheme.primary, style = Typography.bodySmall ) }, singleLine = true,
                            placeholder = { Text("(필수)", color = mainColorScheme.tertiary, style = Typography.bodySmall) }, colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = mainColorScheme.primary, focusedBorderColor = mainColorScheme.primary, unfocusedContainerColor = mainColorScheme.onPrimary, focusedContainerColor = mainColorScheme.onPrimary))
                    }
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp))
                    Row {
                        ElevatedButton(onClick = {
                            dialogChanger(false)
                        },  modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(10.dp), shape = RectangleShape, colors = ButtonDefaults.buttonColors(containerColor = mainColorScheme.secondary, contentColor = mainColorScheme.onPrimary)) {
                            Text(stringResource(R.string.dismiss), style = Typography.bodyMedium)
                        }
                        ElevatedButton(onClick = {
                            if (title.isEmpty()) {
                                context.toast(R.string.title_empty)
                            } else {
                                if (accountMap.values.flatten().all {it.title != title || it.artist != artist}) {
                                    val uuid = UUID.randomUUID()
                                    val song = Song(artist, title, 0, uuid)
                                    accountMap[id] = accountMap[id]!!.apply {
                                        add(song)
                                    }
                                    recommendationMap[uuid] = mutableListOf()
                                    songChanger(songList)

                                    Log.d("TEST", accountMap.toString())
                                    context.toast(R.string.confirmed, accountMap[id]!!.size.toString())
                                    dialogChanger(false)
                                } else {
                                    context.toast(R.string.exist)
                                }
                            }
                        },  modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp), shape = RectangleShape, colors = ButtonDefaults.buttonColors(containerColor = mainColorScheme.primary, contentColor = mainColorScheme.onPrimary)) {
                            Text(stringResource(R.string.confirm), style = Typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
    @Composable
    fun EditDialog(id: String, dialogChanger: Changer<Boolean>, song: Song, songChanger: Changer<List<Song>>) {
        var artist by remember { mutableStateOf(song.artist) }
        var title by remember { mutableStateOf(song.title) }
        val context = LocalContext.current
        Dialog(onDismissRequest = { dialogChanger(false) } ) {
            Card(colors = CardDefaults.cardColors(containerColor = mainColorScheme.onPrimary), modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .wrapContentSize()) {
                Column {
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp))
                    Column {
                        OutlinedTextField(value = artist, onValueChange = {artist = it}, modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 0.dp)
                            , textStyle = Typography.bodySmall, label = { Text(stringResource(R.string.artist), color = mainColorScheme.primary, style = Typography.bodySmall ) }, singleLine = true,
                            placeholder = { Text(stringResource(R.string.essential), color = mainColorScheme.tertiary, style = Typography.bodySmall) }, colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = mainColorScheme.primary, focusedBorderColor = mainColorScheme.primary, unfocusedContainerColor = mainColorScheme.onPrimary, focusedContainerColor = mainColorScheme.onPrimary))
                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp))
                        OutlinedTextField(value = title, onValueChange = {title = it}, modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 0.dp)
                            , textStyle = Typography.bodySmall, label = { Text(stringResource(R.string.song), color = mainColorScheme.primary, style = Typography.bodySmall ) }, singleLine = true,
                            placeholder = { Text(stringResource(R.string.no_essential), color = mainColorScheme.tertiary, style = Typography.bodySmall) }, colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = mainColorScheme.primary, focusedBorderColor = mainColorScheme.primary, unfocusedContainerColor = mainColorScheme.onPrimary, focusedContainerColor = mainColorScheme.onPrimary))
                    }
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp))
                    Row {
                        ElevatedButton(onClick = {
                            dialogChanger(false)
                        },  modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(10.dp), shape = RectangleShape, colors = ButtonDefaults.buttonColors(containerColor = mainColorScheme.secondary, contentColor = mainColorScheme.onPrimary)) {
                            Text(stringResource(R.string.dismiss), style = Typography.bodyMedium)
                        }
                        ElevatedButton(onClick = {
                            if (title.isEmpty()) {
                                context.toast(R.string.title_empty)
                            } else {
                                if (accountMap.values.flatten().all {
                                    (it.title != title || it.artist != artist) || (song.title == title && song.artist == artist)
                                }) {
                                    accountMap[id]!!.find { it == song }?.also {
                                        it.artist = artist
                                        it.title = title
                                    }
                                    songChanger(songList)
                                    context.toast(R.string.edited)
                                    dialogChanger(false)
                                } else {
                                    context.toast(R.string.exist)
                                }
                            }
                        },  modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp), shape = RectangleShape, colors = ButtonDefaults.buttonColors(containerColor = mainColorScheme.primary, contentColor = mainColorScheme.onPrimary)) {
                            Text(stringResource(R.string.edit), style = Typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
    @Composable
    fun AdminLogin(adminChanger: Changer<Boolean>, dialogChanger: Changer<Boolean>) {
        var password by remember { mutableStateOf("") }
        val context = LocalContext.current
        Dialog(onDismissRequest = { dialogChanger(false) } ) {
            Card(colors = CardDefaults.cardColors(containerColor = mainColorScheme.onPrimary), modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .wrapContentSize()) {
                Column {
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp))
                    Column {
                        OutlinedTextField(value = password, onValueChange = {password = it}, modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 0.dp), visualTransformation = PasswordVisualTransformation(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                            , textStyle = Typography.bodySmall, label = { Text("비밀번호", color = mainColorScheme.primary, style = Typography.bodySmall ) }, singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = mainColorScheme.primary, focusedBorderColor = mainColorScheme.primary, unfocusedContainerColor = mainColorScheme.onPrimary, focusedContainerColor = mainColorScheme.onPrimary))
                    }
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp))
                    Row {
                        ElevatedButton(onClick = {
                            dialogChanger(false)
                        },  modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(10.dp), shape = RectangleShape, colors = ButtonDefaults.buttonColors(containerColor = mainColorScheme.secondary, contentColor = mainColorScheme.onPrimary)) {
                            Text(stringResource(R.string.dismiss), style = Typography.bodyMedium)
                        }
                        ElevatedButton(onClick = {
                            if (password == "0304") {
                                context.toast(R.string.admin_logined)
                                adminChanger(true)
                            } else {
                                context.toast(R.string.wrong_pw)
                            }
                            dialogChanger(false)
                        },  modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp), shape = RectangleShape, colors = ButtonDefaults.buttonColors(containerColor = mainColorScheme.primary, contentColor = mainColorScheme.onPrimary)) {
                            Text(stringResource(R.string.admin_login), style = Typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}
