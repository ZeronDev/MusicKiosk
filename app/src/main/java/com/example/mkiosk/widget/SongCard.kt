package com.example.mkiosk.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mkiosk.R
import com.example.mkiosk.data.Song
import com.example.mkiosk.ui.theme.Typography
import com.example.mkiosk.ui.theme.mainColorScheme
import com.example.mkiosk.util.Changer
import com.example.mkiosk.util.Util.accountMap
import com.example.mkiosk.util.Util.editingSong
import com.example.mkiosk.util.Util.findOwner
import com.example.mkiosk.util.Util.recommendationMap
import com.example.mkiosk.util.Util.songList
import com.example.mkiosk.util.Util.toast
import com.example.mkiosk.util.Util.voteCount

@Composable
fun SongCard(index: Int, id: String, song: Song, songChanger: Changer<List<Song>>, editChanger: Changer<Boolean>, modifier: Modifier) {
    val isOwned = accountMap[id]?.contains(song) ?: false
    val color = if (isOwned) mainColorScheme.primary else Color.Black
    var isRecommended by remember(key1 = id) { mutableStateOf(recommendationMap[song.id]?.contains(id) ?: false) }

    val recommendColor = if (isRecommended) mainColorScheme.primary else Color.Black
    val context = LocalContext.current
    Card(modifier = modifier
        .fillMaxWidth()
        .height(80.dp)
        .padding(6.dp), shape = RoundedCornerShape(70.dp), colors = CardDefaults.cardColors(containerColor = mainColorScheme.tertiary)) {
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier= Modifier
                .fillMaxHeight()
                .wrapContentWidth()) {
                Spacer(
                    Modifier
                        .fillMaxHeight()
                        .width(4.dp))
                Text(index.toString(), style = Typography.bodyMedium, color = color,  fontWeight = FontWeight.Bold)
                Spacer(
                    Modifier
                        .fillMaxHeight()
                        .width(12.dp))
                Text(song.artist + (if (song.artist.isNotEmpty()) " - " else "") + song.title, style = Typography.bodySmall, color = color)
            }

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxHeight()) {
                if (isOwned) {
                    IconButton(onClick = {
                        editingSong = song
                        editChanger(true)
                    }) {
                        Icon(Icons.Filled.Edit, stringResource(R.string.edit), tint = Color.Black, modifier = Modifier.size(60.dp))
                    }
                    IconButton(onClick = {
                        accountMap[id]?.remove(song)
                        recommendationMap.remove(song.id)
                        context.toast(R.string.deleted)
                        songChanger(songList)
                    }) {
                        Icon(Icons.Filled.Delete, stringResource(R.string.delete), tint = Color.Red, modifier = Modifier.size(60.dp))
                    }

                }
                IconButton(onClick = {
                    if (id.isEmpty()) {
                        context.toast(R.string.need_login)
                        return@IconButton
                    }
                    if (voteCount(id)) {
                        context.toast(R.string.maximum_recommendation)
                    }

                    var increase = 1
                    if (recommendationMap[song.id]?.contains(id) == true) {
                        recommendationMap[song.id]?.remove(id)
                        increase = -1
                    } else {
                        recommendationMap[song.id]?.add(id)
                    }
                    isRecommended = !isRecommended
                    accountMap.values.flatten().find { it == song }?.let {
                        it.recommendation += increase
                    }
                    songChanger(songList)

                }) {
                    Icon(if (isRecommended) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp, stringResource(R.string.recommendation),
                        tint = recommendColor,
                        modifier = Modifier.size(60.dp))
                }
                Text(song.recommendation.toString(), modifier = Modifier.padding(horizontal = 5.dp), style = Typography.bodySmall, color = recommendColor)
            }
        }
    }
}
@Composable
fun AdminCard(index: Int, song: Song, songChanger: Changer<List<Song>>, modifier: Modifier) {
    val context = LocalContext.current
    Card(modifier = modifier
        .fillMaxWidth()
        .height(80.dp)
        .padding(6.dp), shape = RoundedCornerShape(70.dp), colors = CardDefaults.cardColors(containerColor = mainColorScheme.tertiary)) {
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier= Modifier
                .fillMaxHeight()
                .wrapContentWidth()) {
                Spacer(Modifier.fillMaxHeight().width(4.dp))
                Text(index.toString(), style = Typography.bodyMedium, color = Color.Black,  fontWeight = FontWeight.Bold)
                Spacer(Modifier.fillMaxHeight().width(12.dp))
                Text(song.artist + (if (song.artist.isNotEmpty()) " - " else "") + song.title, style = Typography.bodySmall, color = Color.Black)
            }

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxHeight()) {
                Icon(
                    Icons.Filled.AccountBox,
                    stringResource(R.string.ID),
                    tint = mainColorScheme.primary,
                    modifier = Modifier.size(60.dp)
                )
                Text("ID : ${findOwner(song.id)}", style = Typography.bodySmall, color = Color.Black)

                IconButton(onClick = {
                    accountMap[findOwner(song.id)]?.remove(song)
                    recommendationMap.remove(song.id)
                    context.toast(R.string.deleted)
                    songChanger(songList)
                }) {
                    Icon(
                        Icons.Filled.Delete,
                        stringResource(R.string.delete),
                        tint = Color.Red,
                        modifier = Modifier.size(60.dp)
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        Icons.Outlined.ThumbUp,
                        stringResource(R.string.recommendation),
                        tint = Color.Black,
                        modifier = Modifier.size(60.dp)
                    )
                }

                Text(
                    song.recommendation.toString(),
                    modifier = Modifier.padding(horizontal = 5.dp),
                    style = Typography.bodySmall,
                    color = Color.Black
                )
            }
        }
    }
}
